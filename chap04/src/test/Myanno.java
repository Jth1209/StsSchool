package test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})//요소 타입
@Retention(RetentionPolicy.RUNTIME)//보유 정책
public @interface Myanno {
	String value() default "나의 애노테이션";//데이터 타입 , 매개변수 이름 , 기본값 설정 , 기본값
	int number() default 10;
	boolean required() default true;
}
