package error;

public class OverValueException extends RuntimeException{
	public String error() {
		return "재고수량보다 많은 양을 선택할 수 없습니다.";
	}
}
