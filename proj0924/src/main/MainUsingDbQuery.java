package main;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import config.DbConfig;
import config.DbQueryConfig;
import dbquery.DbQuery;
import domain.dao.Dao;
import domain.dao.ItemDao;
import domain.entity.Item;

public class MainUsingDbQuery {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

		Dao dbQuery = ctx.getBean(Dao.class);
//		dbQuery.insertOrder(1L, 3L, 5);
		System.out.println(dbQuery.orders(1).toString());
	}
}
