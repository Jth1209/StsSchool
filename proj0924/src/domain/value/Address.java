package domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Address {
	private String city;
	private String street;
	private String zipcode;
}
