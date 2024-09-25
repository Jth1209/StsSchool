package chap02;

public class Main {

	public static void main(String[] args) {
		Greeter greeter = new Greeter();
		greeter.setFormat("%s, 안녕!");
		String msg = greeter.greet("스프링");
		System.out.println(msg);

	}

}
