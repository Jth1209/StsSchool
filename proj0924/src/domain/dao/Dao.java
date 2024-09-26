package domain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import domain.entity.Item;
import domain.entity.Member;
import domain.entity.Order;
import domain.entity.OrderList;
import domain.value.Address;

public class Dao {
private JdbcTemplate jdbcTemplate;
	
	public Dao(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	public List<OrderList> orders(int m_id){//사용자 별 주문 보기에 사용
		List<OrderList> results = jdbcTemplate.query("select m.name m_name, i.name i_name, i.price, oi.count, oi.orderprice, o.order_date\r\n"
				+ "  from member m, orders o, order_item oi, item i\r\n"
				+ " where m.id = o.member_id\r\n"
				+ "   and o.id = oi.order_id\r\n"
				+ "   and oi.item_id = i.id and m.id = ?",
				(ResultSet rs, int rowNum)->{
					OrderList orderList = new OrderList(
							rs.getString("m_name"),
							rs.getString("i_name"),
							rs.getInt("price"),
							rs.getInt("count"),
							rs.getInt("orderprice"),
							rs.getDate("order_date"));
					return orderList;
				},m_id);
		return results.isEmpty() ? null : results;
	}
	
	public void insertOrder(Long m_id ,Long i_id , int count) {//매개변수로 사용자번호, 상품 번호, 주문 가격, 주문 수량을 설정
		Member member = selectByMemberId(m_id);
		Item item = selectByItemId(i_id);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update((Connection con)->{//orders 테이블에 데이터를 삽입하는 기능
			PreparedStatement pstmt = con.prepareStatement("insert into orders(member_id, order_item_id, city, street, zipcode, order_date)\r\n"
					+ "values (? , 0, ? , ? , ? , now())",new String[] {"id"});
			pstmt.setLong(1, member.getId());
			pstmt.setString(2, member.getAddress().getCity());
			pstmt.setString(3, member.getAddress().getStreet());
			pstmt.setString(4, member.getAddress().getZipcode());
			return pstmt;
		},keyHolder);
		Number orderValue = keyHolder.getKey();
		Long order_id = orderValue.longValue();
		
		
		KeyHolder keyHolder2 = new GeneratedKeyHolder();
		jdbcTemplate.update((Connection con)->{//order_item_id 테이블에 데이터 삽입 기능
			PreparedStatement pstmt = con.prepareStatement("insert into order_item(item_id, order_id, orderprice, count)\r\n"
					+ "values (?, ?, ?, ?)",new String[] {"id"});
			pstmt.setLong(1, item.getId());
			pstmt.setLong(2, order_id);
			pstmt.setInt(3, item.getPrice() * count);
			pstmt.setInt(4, count);
			return pstmt;
		},keyHolder2);
		Number orderItemValue = keyHolder2.getKey();
		Long order_item_id = orderItemValue.longValue();
		
		jdbcTemplate.update("update orders set order_item_id=? where id = ?",//orders 테이블 order_item_id 업데이트 기능
				order_item_id , order_id);
		
	}

	public Member selectByMemberId(Long id) {
		Member result = jdbcTemplate.queryForObject("select * from member where id = ?",
				(ResultSet rs , int rowNum)->{
					Member member = new Member(
							rs.getLong("id"),
							rs.getString("name"),
							new Address(rs.getString("city"),rs.getString("street"),rs.getString("zipcode")),
							new ArrayList<>());
					return member;
				},id);
		return result;
	}
	
	public Item selectByItemId(Long id) {
		Item result = jdbcTemplate.queryForObject("select * from item where id = ?",
				(ResultSet rs , int rowNum)->{
					Item item = new Item(
							rs.getLong("id"),
							rs.getString("name"),
							rs.getInt("price"),
							rs.getInt("stockquantity"));
					return item;
				},id);
		return result;
	}
}
