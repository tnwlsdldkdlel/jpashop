package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired
	MemberRepository memberRepository;

	@Transactional
	@Test
	@Rollback(false)
	public void testMember() throws Exception {
		Member member = new Member();
		member.setUsername("memberA");
		
		Long savedId = memberRepository.save(member);
		Member findMember = memberRepository.find(savedId);
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		Assertions.assertThat(findMember).isEqualTo(member);
		
		System.out.println(findMember == member);
		
	}

}