package domain.entity;

import lombok.Getter;

@Getter
public class OrderItem {
	public OrderItem(Long orderItemId) {
		this.id = orderItemId;
	}
	private Long id;
	private Item item;
	private Order order;
	private int orderPrice;
	private int count;
}
