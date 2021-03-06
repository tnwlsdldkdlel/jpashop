package jpabook.jpashop.service.query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

	private final OrderRepository orderRepository;

	public List<OrderDto> orderV3() {
		List<Order> orders = orderRepository.findAllWithItem();

		return orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
	}

	@Data
	static class OrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;
		private List<OrderItemDto> orderItems;

		public OrderDto(Order order) {
			this.orderId = order.getId();
			this.name = order.getMember().getName();
			this.orderDate = order.getOrderDate();
			this.orderStatus = order.getStatus();
			this.address = order.getDelivery().getAddress();
			order.getOrderItems().forEach(o -> o.getItem().getName());
			this.orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem))
					.collect(Collectors.toList());
		}

	}

	@Data
	static class OrderItemDto {
		private String itemName;
		private int orderPrice;
		private int count;

		public OrderItemDto(OrderItem orderItem) {
			this.itemName = orderItem.getItem().getName();
			this.orderPrice = orderItem.getOrderPrice();
			this.count = orderItem.getCount();
		}

	}

}
