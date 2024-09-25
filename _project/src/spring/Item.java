package spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Item {

	private Long item_id;
	private String name;
	private int price;
	private int stock_quantity;
	
	public Item (String name, int price, int stock_quantity) {
		this.name = name;
		this.price = price;
		this.stock_quantity = stock_quantity;
	}
}
