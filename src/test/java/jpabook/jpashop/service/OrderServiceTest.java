package jpabook.jpashop.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;

@RunWith(SpringRunner.class)
@Transactional
class OrderServiceTest {

	@Autowired
	OrderService orderService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	ItemService itemService;
	
	@PersistenceContext
	EntityManager em;
	
	@Test
	void 상품주문() throws Exception {
		
		Member member = new Member();
		member.setName("sujin");
		member.setAddress( new Address("도시1","거리1","zipcode1"));
		em.persist(member);
		
		Book book = new Book();
		book.setAuthor("작가1");
		book.setIsbn("isbn1");
		book.setName("책이름!");
		book.setPrice(1000);
		book.setStockQuantity(100);
		
		memberService.join(member);
		orderService.order(member.getId(), book.getId(), 22);
	}

}
