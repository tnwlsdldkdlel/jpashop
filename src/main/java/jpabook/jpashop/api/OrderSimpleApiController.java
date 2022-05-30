package jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	
	@GetMapping("/api/v1/simple-orders")
	public List<Order> orderV1() {
		List<Order> all = orderRepository.findAll(new OrderSearch());
		
		return all;
	}
	
	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDTO> orderV2() {
		List<Order> orders = orderRepository.findAll(new OrderSearch());
		
		return orders.stream().map( o -> new SimpleOrderDTO(o)).collect(Collectors.toList());
	}
	
	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDTO> orderV3() {
		List<Order> orders = orderRepository.findAllWithMemberDelivery();
		
		return orders.stream().map( o -> new SimpleOrderDTO(o)).collect(Collectors.toList());
	}
	
	@GetMapping("/api/v4/simple-orders")
	public List<OrderSimpleQueryDto> orderV4() {
		return orderRepository.findOrderDtos();
	}
	
	@Data
	static class SimpleOrderDTO {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;
		
		public SimpleOrderDTO(Order order) {
			this.orderId = order.getId();
			this.name = order.getMember().getName(); // lazy 
			this.orderDate = order.getOrderDate();
			this.orderStatus = order.getStatus();
			this.address = order.getDelivery().getAddress(); // lazy 
		}
	}

}
