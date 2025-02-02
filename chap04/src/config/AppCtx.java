package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration//개인적으로 Configuartion 보다 ComponentScan이 낫다고 생각 => 나중에 Security같은 보안에만 Bean등록을 한다(특수적인 경우만 Bean 등록)
@ComponentScan(basePackages = {"spring"})//기본 패키지 이름 설정 => 해당 패키지에 @Component를 지정한 모든 클래스를 가져다가 쓸 수 있다.
public class AppCtx {
//	@Bean
//	public MemberDao memberDao() {
//		return new MemberDao();
//	}
	
	@Bean
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}

	@Bean
	@Qualifier("printer")//MemberInfoPrinter와 연결
	public MemberPrinter memberPrinter1() {
		return new MemberPrinter();
	}
	
	@Bean
	@Qualifier("summaryPrinter")//MemberListPrinter와 연결 (매개변수가 MemberSummaryPrinter)
	public MemberSummaryPrinter memberprinter2() {
		return new MemberSummaryPrinter();
	}
	
//	@Bean
//	public MemberListPrinter memberListPrinter() {
//		return new MemberListPrinter();
//	}
//	
//	@Bean
//	public MemberInfoPrinter memberInfoPrinter() {//setter메소드 없이도 값이 알아서 초기화 됨.
//		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
//		return infoPrinter;
//	}

	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter vp = new VersionPrinter();
		vp.setMajorVersion(5);
		vp.setMinorVersion(7);
		return vp;
	}
	
//	@Bean
//	public MemberRegisterService memberRegSvc() {
//		return new MemberRegisterService();
//	}

	@Bean
	public ChangePasswordService changePwdSvc() {
		ChangePasswordService pwdSvc = new ChangePasswordService();//기본 생성자를 선언하는 것만으로 MemberDao 또한 초기화가 된다.
		return pwdSvc;
	}
}
