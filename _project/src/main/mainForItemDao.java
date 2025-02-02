package main;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import spring.Item;
import spring.ItemDao;

public class mainForItemDao {
	private static ItemDao itemDao;
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		
		itemDao = ctx.getBean(ItemDao.class);
		
		selectAll();
		insertItem();
		updateItem();
		
		ctx.close();
	}
	
	private static void selectAll() {
		List<Item> items = itemDao.selectAll();
		for(Item i : items) {
			System.out.println(i.getItem_id() + " : " + i.getName() + " : " + i.getPrice() + " : " + i.getStock_quantity());
		}
	}
	
	private static void updateItem() {
		System.out.println("---------updateItem");
		Item item = itemDao.selectById("비틀즈");
	}
	
	private static void insertItem() {
		System.out.println("------------insertItem");
		Item item = new Item("비틀즈",1000,10);
		itemDao.insert(item);
		System.out.println(item.getItem_id() + "번 데이터 추가 완료");
	}
}
