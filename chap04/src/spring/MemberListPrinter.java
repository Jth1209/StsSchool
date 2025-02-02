package spring;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("listPrinter")
public class MemberListPrinter {
	private MemberDao memberDao;
	private MemberPrinter printer;
	
	public MemberListPrinter() {
	}
	
	public void printAll() {
		Collection<Member> members = memberDao.selectAll();
		members.forEach(m->printer.print(m));
	}
	
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	@Autowired
	@Qualifier("printer")//이렇게 한정자를 선언해서 AppCtx에 MemberSummaryPrinter와 연결해주는 방법도 있고
	public void setMemberPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
	
//	public void setMemberPrinter(MemberSummaryPrinter printer) {//setter 메소드를 사용해서 매개변수로 MemberSummaryPrinter를 사용하게끔 변경
//		this.printer = printer;
//	}
}
