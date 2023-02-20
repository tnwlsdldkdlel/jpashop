package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {

	@Id @GeneratedValue
	@Column(name = "category_id")
	private Long id;
	
	private String name;
	
	@ManyToMany
	@JoinTable(name = "categoty_item",
		joinColumns = @JoinColumn(name = "category_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id")) // 1:n , n:1 로 풀어낼 테이블 필요.  -> 실전에선 쓰면 안됨.
	private List<Item> items = new ArrayList<>();
	
	// LAZY(지연로딩) : 해당 테이블 데이터만 가져오도록.
	// toOne 조인은 즉시로딩이 default : n+1 문제 발생. 관련된 데이터를 모두 가져오므로 조심해야함.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Category> child = new ArrayList<>();
	
	// 연관관계 메소드
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
 
}
