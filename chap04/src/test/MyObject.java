package test;

public class MyObject {
	@Myanno(number=4)
	public void test1() {
		System.out.println("this is testMethod");
	}
	
	@Myanno(value = "my new annotation")
	public void test2(){
		System.out.println("this is test2");
	}
}
