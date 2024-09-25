package domain.entity;

import domain.value.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Delivery {
	public Delivery(Long deliveryId) {
		this.id = deliveryId;
	}
	private Long id;
	private Order order;
	private Address address;

}
