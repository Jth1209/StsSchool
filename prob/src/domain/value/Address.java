package domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Address {
	private String city;
	private String street;
	private String zipcode;
}
