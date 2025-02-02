package spring;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

public class MemberPrinter {

	private DateTimeFormatter dateTimeFormatter;
	
	public MemberPrinter() {
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
	}

	public void print(Member member) {
		if (dateTimeFormatter == null) {
			System.out.printf("회원정보 : 아이디 = %d, 이메일 = %s, 이름 = %s, 등록일 = %tF\n", member.getId(), member.getEmail(),
					member.getName(), member.getRegisterDateTime());
		} else {
			System.out.printf("회원정보 : 아이디 = %d, 이메일 = %s, 이름 = %s, 등록일 = %s\n", member.getId(), member.getEmail(),
					member.getName(), dateTimeFormatter.format(member.getRegisterDateTime()));
		}
	}

//	@Autowired(required = false) // 매칭되는 빈이 없어도 오류를 발생시키지 않고 자동주입을 실행하지 않는다.(이거 좀 괜찮은 듯)/ 아예 실행시키지 않음
//	public void setDateFormatter(DateTimeFormatter dateTimeFormatter) {
//		this.dateTimeFormatter = dateTimeFormatter;
//	}

	@Autowired//매칭되는 빈이 없어도 오류를 발생시키지 않고 자동주입을 실행하지 않는다.(이거 좀 괜찮은 듯)
	public void setDateFormatter(@Nullable DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	// Optional은 무조건 해당 메소드를 실행한 후, 매개변수의 널 여부에 따라 다른 결과를 도출
//	@Autowired
//	public void setDateFormatter(Optional<DateTimeFormatter> formatterOpt) {
//		if(formatterOpt.isPresent()) {
//			this.dateTimeFormatter = formatterOpt.get();
//		}else {
//			this.dateTimeFormatter = null;
//		}
//	}
}
