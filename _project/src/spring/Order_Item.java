package spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Order_Item {
	private int order_item_id;
	private int order_id;
	private int item_id;
	private int order_price;
	private int count;
}
