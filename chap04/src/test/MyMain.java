package test;

import java.lang.reflect.Method;

public class MyMain {
	
	public static void main(String[] args) {
		Method[] methodList = MyObject.class.getMethods();
		
		for(Method m : methodList) {
			if(m.isAnnotationPresent(Myanno.class)) {//애노테이션 존재 여부 파악/ 값이 존재 하면 아래 코드 진행
				Myanno annotation = m.getDeclaredAnnotation(Myanno.class);
				String value = annotation.value();//MyObject에서 선언된 값 : my new annotation
				System.out.println(m.getName()+ ":" + value);
				System.out.println(annotation.number());//MyObject에서 선언된 값 : 4
			}
		}
	}
}
