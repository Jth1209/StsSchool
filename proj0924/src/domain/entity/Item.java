package domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Item {
	private Long id;
	private String name;
	private int price;
	private int stockQuantity;
	
	public Item(String name,int price,int stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
	
	public Item(Long id) {
		this.id = id;
	}
	
}
