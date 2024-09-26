package dbquery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import domain.entity.Item;

public class DbQuery {
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	public DbQuery(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Item> findAllItem() {
		String sql = "select * from item";
		return jdbcTemplate.query(sql, (rs, rn)->{
			Item item = new Item(
					rs.getLong("id"),
					rs.getString("name"),
					rs.getInt("price"),
					rs.getInt("stockquantity")
			);
			return item;
		});
	}
	
	public int count() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select count(*) from MEMBER")) {
				rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
	}

}
