package test;

import java.util.Optional;

public class OptionalTest {

	public static void main(String[] args) {
		String str = null;
		String oStr = Optional.ofNullable(str).orElse("not null");//ofNullable의 매개변수가 널이면, ofElse의 매개변수 값으로 대체한다는 코드
		System.out.println(oStr);
		
		String value = "Hello, Optional";
		Optional<String> optionalValue = Optional.ofNullable(value);
		
		if(optionalValue.isPresent()) {
			System.out.println("Value is present, vlaue : " + optionalValue.get());
		}else {
			System.out.println("Value is Empty : " + optionalValue.isEmpty());
		}
	}
}
