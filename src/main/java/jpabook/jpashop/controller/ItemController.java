package jpabook.jpashop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	@GetMapping("/items/new")
	public String createForm(Model model) {
		model.addAttribute("form", new BookForm());
		
		return "items/createItemForm";
	}
	
	@PostMapping("/items/new")
	public String create(@Valid BookForm form, BindingResult result) {
		
		Book book = new Book();
		book.setAuthor(form.getAuthor());
		book.setIsbn(form.getIsbn());
		book.setName(form.getName());
		book.setPrice(form.getPrice());
		book.setStockQuantity(form.getStockQuantity());
		
		itemService.saveItem(book);
		
		return "redirect:/items";
	}
	
	@GetMapping("/items")
	public String list(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		
		return "items/itemLlist";
	}
	
	@GetMapping("/items/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Book book = (Book)itemService.findOne(itemId);
		
		BookForm form = new BookForm();
		form.setAuthor(book.getAuthor());
		form.setIsbn(book.getIsbn());
		form.setName(book.getName());
		form.setPrice(book.getPrice());
		form.setStockQuantity(book.getStockQuantity());
		form.setId(itemId);

		model.addAttribute("form", form);
		
		return "items/updateItemForm";
	}
	
	@PostMapping(value = "/items/{itemId}/edit")
	public String updateItem(BookForm form) {
		itemService.upddate(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity());
		
		return "redirect:/items";
	}
}
