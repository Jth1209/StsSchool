package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.VersionPrinter;

@Configuration
public class AppConf2 {
	@Autowired
	private MemberDao memberDao;//자동 주입 방식을 사용
	@Autowired
	private MemberPrinter memberPrinter;
	
	@Bean
	public MemberListPrinter memberListPrinter() {
		return new MemberListPrinter(memberDao, memberPrinter);
	}
	
	@Bean
	public MemberInfoPrinter memberInfoPrinter() {
		MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
//		infoPrinter.setMemberDao(memberDao);
//		infoPrinter.setMemberPrinter(memberPrinter);
		return infoPrinter;
	}

	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter vp = new VersionPrinter();
		vp.setMajorVersion(5);
		vp.setMinorVersion(7);
		return vp;
	}
	
	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao);
	}

	@Bean
	public ChangePasswordService changePwdSvc() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao);
		return pwdSvc;
	}
}
