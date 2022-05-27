package jpabook.jpashop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	private final ItemService itemService;
	private final MemberService memberService;
	
	@GetMapping("/order")
	public String createForm(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		
		return "order/orderForm";
	}
	
	@PostMapping("/order")
	public String order(@RequestParam("memberId") Long memberId,
			@RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
		orderService.order(memberId, itemId, count);
		
		return "redirect:/orders"; 
	}
	
	@GetMapping("/orders")
	public String orderList(OrderSearch orderSearch, Model model) {
		List<Order> orders = orderService.findOrders(orderSearch);
		model.addAttribute("orders", orders);
		
		return "order/orderList";
	}
	
	 @PostMapping(value = "/orders/{orderId}/cancel")
	 public String cancleOrder(@PathVariable("orderId") Long orderId) {
		 orderService.cancelOrder(orderId);
		 
		 return "redirect:/orders";
	 }
}
