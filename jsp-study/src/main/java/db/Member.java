package db;

//데이터 관점에서 회원에 대한 데이터를 저장하는 클래스
//회원의 데이터 추상화
public class Member {
    private String memberType;
    private String userId;
    private String password;
    private String name;

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
