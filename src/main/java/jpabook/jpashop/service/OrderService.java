package jpabook.jpashop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	
	// 주문 
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		
		// 엔티티조회 
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		// 배송정보조회 
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		// 주문상품생성 
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		
		// 주문생성 
		Order order = Order.createOrder(member, delivery, orderItem);
		
		// 주문 
		// order, delivery 는 cascade로 자동적으로 persist됨.
		// 라이프사이클이 동일한 경우 사용.
		orderRepository.save(order);
		
		return order.getId();
	}
	
	// 주문취소 
	@Transactional
	public void cancelOrder(Long orderId) {
		orderRepository.findOne(orderId).cancle();
	}
	
	// 검색 
//	public List<Order> findOrders(String orderSearch) {
//		
//	}

}
