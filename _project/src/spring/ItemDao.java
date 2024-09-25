package spring;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ItemDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public ItemDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Item selectById(String item_id) {
		Item results = jdbcTemplate.queryForObject("select * from item where item_id = ?",
				(ResultSet rs , int rowNum) -> {
					Item item = new Item(rs.getLong("item_id"),
										 rs.getString("name"),
							             rs.getInt("price"),
							             rs.getInt("stock_quantity"));
					return item;
				},item_id);
		return results;
	}
	public void insert(Item item) {
		KeyHolder keyholder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("insert into item (name,price,stock_quantity) values (?,?,?)",new String[] {"item_id"});
				pstmt.setString(1, item.getName());
				pstmt.setInt(2,item.getPrice());
				pstmt.setInt(3, item.getStock_quantity());
				return pstmt;
			}
		},keyholder);
		Number keyValue = keyholder.getKey();
		item.setItem_id(keyValue.longValue());
	}
	public void update(Item item) {
		jdbcTemplate.update("update item set name = ?,price = ?,stock_quantity=? where item_id=?",
				item.getName(),item.getPrice(),item.getStock_quantity(),item.getItem_id());
	}
	public List<Item> selectAll(){
		List<Item> results = jdbcTemplate.query("select * from item",
				(ResultSet rs , int rowNum) -> {
					Item item = new Item(rs.getLong("item_id"),
										 rs.getString("name"),
							             rs.getInt("price"),
							             rs.getInt("stock_quantity"));
					return item;
				});
		return results;
	}
}
