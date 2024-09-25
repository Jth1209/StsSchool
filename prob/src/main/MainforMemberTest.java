package main;

import domain.entity.Member;
import domain.repository.MemberRepository;

public class MainforMemberTest {

	public static void main(String[] args) {
		MemberRepository memberRepository = new MemberRepository();
		Member member = new Member(0L, "홍길동", null, null);
		memberRepository.save(member);
		System.out.println(memberRepository.findAll());

	}

}
