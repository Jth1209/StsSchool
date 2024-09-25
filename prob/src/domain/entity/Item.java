package domain.entity;

import lombok.Getter;

@Getter
public class Item {
	private Long id;
	private String name;
	private int price;
	private int stockQuantity;
}
