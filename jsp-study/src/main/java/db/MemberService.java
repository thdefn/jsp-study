package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//회원에 대한 기능을 구현한 클래스화
//회원의 기능 추상화
public class MemberService {

    public List<Member> list(){
    	
    	List<Member> memberList = new ArrayList<>();

        //1.ip(domain) 2.port 3.계정 4.password 5.인스턴스
        String url = "jdbc:mariadb://localhost:3305/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";

        //1.드라이버 로드
        //예외 처리 방법 1. 내가 감당되면 처리 2. 감당 안되면 펑션 밖으로 throw
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // lib/~/Driver 클래스 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null; //finally에서 close 하기 위함
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;

        //email, kakao, facebook
        //sql injection 공격 가능
        String memberTypeValue = "email";

        try {
            //2.커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3.스테이트먼트 객체 생성
            //CallableStatement callableStatement = null; Stored Procedure용
            //statement = connection.createStatement();

            //4.쿼리 실행
            String sql = " select member_type, user_id, password, name " +
                    " from member " +
                    " where member_type= ? ";
            //" and user_id = ?";

            preparedStatement = connection.prepareStatement(sql); //statement와 달리 쿼리 파라미터 있음
            preparedStatement.setString(1, memberTypeValue); //parmeter index는 1부터
            //preparedStatement.setString(2, userId);

            //rs = statement.executeQuery(sql); //결과가 컬렉션 형태로 떨어져야 하기 때문에 executeQuery
            rs = preparedStatement.executeQuery(); //쿼리는 앞에서 설정했으므로

            //5.결과 수행
            while (rs.next()){ //하나하나씩 set을 읽어오기
                String memberType = rs.getString("member_type");
                String userId = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");
               
                Member member = new Member();
                member.setMemberType(memberType);
                member.setName(name);
                member.setUserId(userId);
                member.setPassword(password);
                
                memberList.add(member);

                System.out.println(memberType+", "+userId+", "+password+", "+name);

                /*
                if(특별한조건){
                    return;
                }
                 */
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //열린 객체 닫아주기
            //실제로 서비스가 운영되는 서버에서는 웹 서버 , DB 서버가 따로 있기 때문에 RS, statement close 하다가 예외 발생 가능
            try {
                if(rs!= null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!= null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //커넥션 객체가 제대로 안닫힐 수도 있다, 안닫히면 서버가 죽기도 함
            try {
                if(connection!= null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        
        return memberList;

    }
    
public Member detail(String memberType, String userId){
  
		Member member = null;

        //1.ip(domain) 2.port 3.계정 4.password 5.인스턴스
        String url = "jdbc:mariadb://localhost:3305/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";

        //1.드라이버 로드
        //예외 처리 방법 1. 내가 감당되면 처리 2. 감당 안되면 펑션 밖으로 throw
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // lib/~/Driver 클래스 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null; //finally에서 close 하기 위함
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;

        //email, kakao, facebook
        //sql injection 공격 가능
        String memberTypeValue = "email";

        try {
            //2.커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3.스테이트먼트 객체 생성
            //CallableStatement callableStatement = null; Stored Procedure용
            //statement = connection.createStatement();

            //4.쿼리 실행
            String sql = " select m.member_type, m.user_id, m.password, m.name, md.mobile_no, md.marketing_yn, md.register_date "
            		+ " from member m "
            		+ "    left join member_detail md on md.member_type = m.member_type and m.user_id = md.user_id "
            		+ " where m.member_type = ? and m.user_id = ? ";
            //" and user_id = ?";

            preparedStatement = connection.prepareStatement(sql); //statement와 달리 쿼리 파라미터 있음
            preparedStatement.setString(1, memberType); //parmeter index는 1부터
            preparedStatement.setString(2, userId);

            //rs = statement.executeQuery(sql); //결과가 컬렉션 형태로 떨어져야 하기 때문에 executeQuery
            rs = preparedStatement.executeQuery(); //쿼리는 앞에서 설정했으므로

            //5.결과 수행
            if (rs.next()){ //하나하나씩 set을 읽어오기
            	// 여기 안들어오면 회원은 null
            	member = new Member();
                member.setMemberType(rs.getString("member_type"));
                member.setName(rs.getString("name"));
                member.setUserId(rs.getString("user_id"));
                member.setPassword(rs.getString("password"));
                member.setMobileNo(rs.getString("mobile_no"));
                member.setMarketingYn(rs.getBoolean("marketing_yn"));
                member.setRegisterDate(rs.getDate("register_date"));
                
                /*
                if(특별한조건){
                    return;
                }
                 */
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //열린 객체 닫아주기
            //실제로 서비스가 운영되는 서버에서는 웹 서버 , DB 서버가 따로 있기 때문에 RS, statement close 하다가 예외 발생 가능
            try {
                if(rs!= null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!= null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //커넥션 객체가 제대로 안닫힐 수도 있다, 안닫히면 서버가 죽기도 함
            try {
                if(connection!= null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        
        return member;

    }

    /**
     * 회원 가입 함수
     * @param member 회원정보
     */
    public void register(Member member){
        //1.ip(domain) 2.port 3.계정 4.password 5.인스턴스
        String url = "jdbc:mariadb://localhost:3305/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";

        //1.드라이버 로드
        //예외 처리 방법 1. 내가 감당되면 처리 2. 감당 안되면 펑션 밖으로 throw
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // lib/~/Driver 클래스 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null; //finally에서 close 하기 위함
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;

        /*
        String memberTypeValue = "email";
        String userIdValue = "zerobase@naver.com";
        String passwordValue = "3333";
        String nameValue = "제로베이스";
         */

        try {
            //2.커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3.쿼리 준비
            String sql = " insert into member (member_type, user_id, password, name) " +
                    " values (?,?,?,?) ";

            preparedStatement = connection.prepareStatement(sql); //statement와 달리 쿼리 파라미터 있음
            preparedStatement.setString(1, member.getMemberType()); //parmeter index는 1부터
            preparedStatement.setString(2, member.getUserId());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.setString(4, member.getName());

            //4. 쿼리 실행
            //rs = statement.executeQuery(sql); //결과가 컬렉션 형태로 떨어져야 하기 때문에 executeQuery
            int affected = preparedStatement.executeUpdate(); //결과가 int

            //5.결과 확인
            if(affected > 0){
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //열린 객체 닫아주기
            //실제로 서비스가 운영되는 서버에서는 웹 서버 , DB 서버가 따로 있기 때문에 RS, statement close 하다가 예외 발생 가능
            try {
                if(rs!= null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!= null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //커넥션 객체가 제대로 안닫힐 수도 있다, 안닫히면 서버가 죽기도 함
            try {
                if(connection!= null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void dbUpdate(){
        //1.ip(domain) 2.port 3.계정 4.password 5.인스턴스
        String url = "jdbc:mariadb://localhost:3305/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";

        //1.드라이버 로드
        //예외 처리 방법 1. 내가 감당되면 처리 2. 감당 안되면 펑션 밖으로 throw
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // lib/~/Driver 클래스 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null; //finally에서 close 하기 위함
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;

        String memberTypeValue = "email";
        String userIdValue = "zerobase@naver.com";
        String passwordValue = "9999";

        try {
            //2.커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3.쿼리 준비
            String sql = " update member set " +
                    "  password = ? " +
                    " where member_type = ? and user_id = ? ";

            preparedStatement = connection.prepareStatement(sql); //statement와 달리 쿼리 파라미터 있음
            preparedStatement.setString(1, passwordValue); //parmeter index는 1부터
            preparedStatement.setString(2, memberTypeValue);
            preparedStatement.setString(3, userIdValue);

            //4. 쿼리 실행
            //rs = statement.executeQuery(sql); //결과가 컬렉션 형태로 떨어져야 하기 때문에 executeQuery
            int affected = preparedStatement.executeUpdate(); //결과가 int

            //5.결과 확인
            if(affected > 0){
                System.out.println("수정 성공");
            } else {
                System.out.println("수정 실패");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //열린 객체 닫아주기
            //실제로 서비스가 운영되는 서버에서는 웹 서버 , DB 서버가 따로 있기 때문에 RS, statement close 하다가 예외 발생 가능
            try {
                if(rs!= null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!= null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //커넥션 객체가 제대로 안닫힐 수도 있다, 안닫히면 서버가 죽기도 함
            try {
                if(connection!= null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 회원 탈퇴 함수
     * @param member
     */
    public void withdraw(Member member){
        //1.ip(domain) 2.port 3.계정 4.password 5.인스턴스
        String url = "jdbc:mariadb://localhost:3305/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";

        //1.드라이버 로드
        //예외 처리 방법 1. 내가 감당되면 처리 2. 감당 안되면 펑션 밖으로 throw
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // lib/~/Driver 클래스 로드
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null; //finally에서 close 하기 위함
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            //2.커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3.쿼리 준비
            String sql = " delete from member " +
                    " where member_type = ? and user_id = ? ";

            preparedStatement = connection.prepareStatement(sql); //statement와 달리 쿼리 파라미터 있음
            preparedStatement.setString(1, member.getMemberType()); //parmeter index는 1부터
            preparedStatement.setString(2, member.getUserId());

            //4. 쿼리 실행
            //rs = statement.executeQuery(sql); //결과가 컬렉션 형태로 떨어져야 하기 때문에 executeQuery
            int affected = preparedStatement.executeUpdate(); //결과가 int

            //5.결과 확인
            if(affected > 0){
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //열린 객체 닫아주기
            //실제로 서비스가 운영되는 서버에서는 웹 서버 , DB 서버가 따로 있기 때문에 RS, statement close 하다가 예외 발생 가능
            try {
                if(rs!= null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(preparedStatement!= null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //커넥션 객체가 제대로 안닫힐 수도 있다, 안닫히면 서버가 죽기도 함
            try {
                if(connection!= null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 회원 목록
     * @return 회원정보목록
     */
    public List<Member> getList(){

        return null;
    }
    
    


}
