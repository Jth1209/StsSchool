package main;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.format.datetime.joda.LocalDateTimeParser;

import config.AppCtx;
import dbquery.DbQuery;
import spring.Member;
import spring.MemberDao;

public class MainUsingDbQuery {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppCtx.class);
		LocalDateTime time = LocalDateTime.now();
		Long num = 3L;
		
		MemberDao memberDao = ctx.getBean(MemberDao.class);
		System.out.println(memberDao.selectAll().get(0).getName() + " : " + memberDao.selectAll().get(1).getName());
//		memberDao.inserItem(new Member("test1@naver.com","8697","Jung",time));
		memberDao.updateItem(new Member(num,"xkgs@daum.com","2468","Kim",time));
		System.out.println(memberDao.selectById("xkgs@daum.com").getEmail() + " , " + memberDao.selectById("xkgs@daum.com").getName());
		ctx.close();
	}
}
