package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDao {

	private JdbcTemplate jdbcTemplate;

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Member selectById(String email) {
		Member result = jdbcTemplate.queryForObject("select * from member where email = ?",
				(ResultSet rs, int rowNum) -> {
					Member member = new Member(rs.getString("email"), rs.getString("password"), rs.getString("name"),
							rs.getTimestamp("regdate").toLocalDateTime());
					member.setId(rs.getLong("id"));
					return member;
				}, email);
		return result;
	}

	public void inserItem(Member member) {// insert에서 가장 중요한 것은 KeyHolder와 new String[] {"autoIncrement 칼럼 명"} 이다.
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update((Connection con) -> {
			PreparedStatement pstmt = con.prepareStatement(
					"insert into member (email,password,name,regdate) values (?,?,?,?)", new String[] {"id"});
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
			return pstmt;
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}

	public void updateItem(Member member) {
		jdbcTemplate.update("update member set email = ?, password = ? , name = ?, regdate = ? where id = ?",
				member.getEmail(), member.getPassword(), member.getName(), member.getRegisterDateTime(), member.getId());
	}

	public List<Member> selectAll() {
		List<Member> results = jdbcTemplate.query("select * from member", (ResultSet rs , int rowNum)->{
			Member member = new Member(rs.getString("email"),rs.getString("password"),rs.getString("name"),rs.getTimestamp("regdate").toLocalDateTime());
			member.setId(rs.getLong("id"));
			return member;
		});
		return results;
	}
}
