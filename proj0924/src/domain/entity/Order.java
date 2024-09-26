package domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Order {
	private Long id;
	private Member member;
	private List<OrderItem> orderItems = new ArrayList<>();
	private Date orderDate;
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}
}
