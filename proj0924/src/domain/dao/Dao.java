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
import domain.entity.OrderItem;
import domain.entity.OrderList;
import domain.value.Address;
import error.OverValueException;

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
	
	public void insertOrderItem(Long order_id ,Long i_id , int count) {//매개변수로 사용자번호, 상품 번호, 주문 가격, 주문 수량을 저장
		Item item = selectByItemId(i_id);
	
		if(count > item.getStockQuantity()) {
			throw new OverValueException();
		}
		
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
	}
	
	public void insertOrder(Long m_id) {
		Member member = selectByMemberId(m_id);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update((Connection con)->{
			PreparedStatement pstmt = con.prepareStatement("insert into orders (member_id,city,street,zipcode,order_date) values (?,?,?,?,now())",new String[] {"id"});
			pstmt.setLong(1, m_id);
			pstmt.setString(2, member.getAddress().getCity());
			pstmt.setString(3, member.getAddress().getStreet());
			pstmt.setString(4, member.getAddress().getZipcode());
			return pstmt;
		},keyHolder);
	}
	
	public List<Order> checkOrder(Long m_id) {
		List<Order> order = jdbcTemplate.query("select * from orders where member_id = ?",
				(ResultSet rs , int rowNum)->{
					Order orders = new Order(rs.getLong("id"),
					new Member(rs.getLong("member_id")),
					new ArrayList<>(),
					rs.getDate("order_date"));
					return orders;
				},m_id);
		return order.isEmpty() ? null : order;
	}
	
	public List<OrderItem> checkOrderItem(Long o_id) {
		List<OrderItem> order = jdbcTemplate.query("select * from order_item where order_id = ?",
				(ResultSet rs , int rowNum)->{
					OrderItem orders = new OrderItem(rs.getLong("id"),
					new Item(rs.getLong("item_id")),
					new Order(rs.getLong("order_id")),
					rs.getInt("orderprice"),
					rs.getInt("count"));
					return orders;
				},o_id);
		return order.isEmpty() ? null : order;
	}

	public Member selectByMemberId(Long id) {//사용자 정보를 사용자 일련 번호를 통해 가져오는 기능
		List<Member> result = jdbcTemplate.query("select * from member where id = ?",
				(ResultSet rs , int rowNum)->{
					Member member = new Member(
							rs.getLong("id"),
							rs.getString("name"),
							new Address(rs.getString("city"),rs.getString("street"),rs.getString("zipcode")),
							new ArrayList<>());
					return member;
				},id);
		return result.isEmpty() ? null : result.get(0);
	}
	
	public void insertMember(Member member) {//멤버 추가 기능
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update((Connection con) -> {
			PreparedStatement pstmt = con.prepareStatement("insert into member (name,city,street,zipcode) values (?,?,?,?)",new String[] {"id"});
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getAddress().getCity());
			pstmt.setString(3, member.getAddress().getStreet());
			pstmt.setString(4, member.getAddress().getZipcode());
			return pstmt;
		},keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}
	
	public List<Member> selectAllMember(){//모든 멤버의 정보를 출력 (멤버 선택을 위해서)
		List<Member> results = jdbcTemplate.query("select * from member", 
				(ResultSet rs , int rowNum)->{
					Member member = new Member(
							rs.getLong("id"),
							rs.getString("name"),
							new Address(rs.getString("city"),rs.getString("street"),rs.getString("zipcode")),
							new ArrayList<>());
					return member;
				});
		return results.isEmpty() ? null : results;
	}
	
	public Item selectByItemId(Long id) {//아이템의 번호로 해당 아이템의 정보를 출력
		List<Item> result = jdbcTemplate.query("select * from item where id = ?",
				(ResultSet rs , int rowNum)->{
					Item item = new Item(
							rs.getLong("id"),
							rs.getString("name"),
							rs.getInt("price"),
							rs.getInt("stockquantity"));
					return item;
				},id);
		return result.isEmpty() ? null : result.get(0);
	}
	
	public void insertItem(Item item) {//멤버 추가 기능
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update((Connection con) -> {
			PreparedStatement pstmt = con.prepareStatement("insert into item (name,price,stockquantity) values (?,?,?)",new String[] {"id"});
			pstmt.setString(1, item.getName());
			pstmt.setInt(2, item.getPrice());
			pstmt.setInt(3, item.getStockQuantity());
			return pstmt;
		},keyHolder);
		Number keyValue = keyHolder.getKey();
		item.setId(keyValue.longValue());
	}
	
	public List<Item> selectAllItem(){//모든 아이템 상세 정보를 출력
		List<Item> results = jdbcTemplate.query("select * from item", 
				(ResultSet rs , int rowNum)->{
					Item item = new Item(
							rs.getLong("id"),
							rs.getString("name"),
							rs.getInt("price"),
							rs.getInt("stockquantity"));
					return item;
				});
		return results.isEmpty() ? null : results;
	}
	
	public void deleteOrder(Long o_id) {
		jdbcTemplate.update("delete from orders where id = ?",o_id);
		jdbcTemplate.update("delete from order_item where order_id = ?",o_id);
	}
	
	public void deleteOrderItem(Long oi_id) {
		jdbcTemplate.update("delete from order_item where id = ?",oi_id);
	}
}
