package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private EntityManager em;
	
	public void save(Item item) {
		if(item.getId() == null) {
			em.persist(item);
		} else {
			em.merge(item); // update
		}
	}
	
	public List<Item> findAll() {
		return em.createQuery("select i from Item i", Item.class)
				.getResultList();
	}
	
	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}
	
	
	
}
