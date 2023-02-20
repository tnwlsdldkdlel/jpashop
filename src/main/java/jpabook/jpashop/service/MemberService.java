package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 가지고있는 객체만 생성자를 만들어줌.
public class MemberService {
	
	private final MemberRepository memberRespository;

	
	// 회원가입
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRespository.save(member);

		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		Member findMembers = memberRespository.findByName(member.getName());

		if (findMembers == null) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	// 회원 전체 조회
	public List<Member> findMembers() {
		return memberRespository.findAll();
	}

	public Member findOne(Long memberId) {
		return memberRespository.findOne(memberId);
	}
}
