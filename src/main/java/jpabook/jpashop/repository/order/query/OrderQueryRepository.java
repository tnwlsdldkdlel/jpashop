package jpabook.jpashop.repository.order.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

	private final EntityManager em;
	
	public List<OrderQueryDto> findOrderQueryDtos() {
		List<OrderQueryDto> result = findOrders();
		
		result.forEach( o -> {
			List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
			o.setOrderItems(orderItems);
		});
		
		return result;
	}
	
	public List<OrderQueryDto> findAllByDto_optimization() {
		List<OrderQueryDto> result = findOrders();
		
		List<Long> orderIds = result.stream().map( o -> o.getOrderId()).collect(Collectors.toList());
		
		List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
				+ " from OrderItem oi"
				+ " join oi.item i"
				+ " where oi.order.id in :orderIds", OrderItemQueryDto.class)
				.setParameter("orderIds", orderIds)
				.getResultList();
		
		Map<Long, >
	}
	
	//ToMany : row수가 증가하므로 forEach로 조회 -> n+1 문제 발생.
	private List<OrderItemQueryDto> findOrderItems(Long orderId) {
		return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
				+ " from OrderItem oi"
				+ " join oi.item i"
				+ " where oi.order.id = : orderId", OrderItemQueryDto.class)
				.setParameter("orderId", orderId)
				.getResultList();
	}

	//ToOne : row수가 증가하지 않으므로 한번에 조회.
	public List<OrderQueryDto> findOrders() {
		return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o"
				+ " join o.member m"
				+ " join o.delivery d", OrderQueryDto.class)
				.getResultList();
				
	}

}
