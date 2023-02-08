package db;

import java.util.List;
import java.util.Scanner;

public class DbTestMain {
	
	public static void main(String[] args) {
        //DbTest dbTest = new DbTest();
        //dbTest.dbSelect();
        //dbTest.dbInsert();
        //dbTest.dbUpdate();
        //dbTest.dbDelete();

        MemberService memberService = new MemberService();
        List<Member> memberList = memberService.list();
	}
	
    public static void _main(String[] args) {
        //DbTest dbTest = new DbTest();
        //dbTest.dbSelect();
        //dbTest.dbInsert();
        //dbTest.dbUpdate();
        //dbTest.dbDelete();

        MemberService memberService = new MemberService();

        Scanner scanner = new Scanner(System.in);

        String memberType = "email";

        System.out.println("탈퇴할 회원 아이디를 입력해주세요.");
        System.out.println("아이디 입력:>>>");
        String userId = scanner.next();
        /*
        System.out.println("비밀번호 입력:>>>");
        String password = scanner.next();
        System.out.println("이름 입력:>>>");
        String name = scanner.next();
         */

        //dbTest.dbInsert(memberType, userId, password, name); params가 많으면 복잡해질 수 있다

        //Member 데이터로 추상화
        Member member = new Member();
        member.setMemberType(memberType);
        member.setUserId(userId);
        //member.setPassword(password);
        //member.setName(name);

        //memberService.register(member);
        memberService.withdraw(member);

        //이미 DB에 있는 값 입력시 [ WARN] (main) Error: 1062-23000: Duplicate entry 'email-zerobase@naver.com' for key 'PRIMARY'
    }
}
