package Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.JpashopApplication;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpashopApplication.class)
@Transactional
public class MemberServiceTest {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;

	@Test
	@Rollback(false)
	public void 회원가입() throws Exception {
		Member member = new Member();
		member.setName("sujin");
		
		Long saveId = memberService.join(member);
		
		assertEquals(member, memberService.findOne(saveId));
	}
	
	@Test
	public void 중복_회원_예외() throws Exception {
		Member member1 = new Member();
		member1.setName("sujin1");
		
		Member member2 = new Member();
		member2.setName("sujin2");
		
		memberService.join(member1);
		memberService.join(member2);
		
	}
	

}
