package com.study.springboot.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MyUserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int countDept() {
		String query = "select count(*) from dept";
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	
	public void insert(MyUserDTO dto) {
		int deptno = dto.getDeptno();
		String dname = dto.getDname();
		String loc = dto.getLoc();
		
		String query="insert into dept values(?,?,?)";
		jdbcTemplate.update(query,deptno,dname,loc);
	}

	public List<MyUserDTO> list() {
		String query = "select * from dept";
		List<MyUserDTO> list = jdbcTemplate.query(query, (rs, rowNum) -> {
			MyUserDTO dto = new MyUserDTO(rs.getInt("deptno"), rs.getString("dname"), rs.getString("loc"));
			return dto;
		});

		for (MyUserDTO my : list) {
			System.out.println(my);
		}

		return list;
	}

}
