package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Test
	void 회원가입() throws Exception {
		// given
		Member member = new Member();
		member.setName("kim");

		// when
		Long saveId = memberService.join(member);

		// then
		assertEquals(member, memberRepository.findOne(saveId));

	}

	
	 @Test void 중복_회원_예외() throws Exception { 
		// given
		 Member member1 = new Member();
		 member1.setName("kim");
		 
		 Member member2 = new Member();
		 member2.setName("kim");
		 
		// when
		 memberService.join(member1);
		 memberService.join(member2); // 예외가 발생해야한다!
		 
		// then
	 }
	

}
