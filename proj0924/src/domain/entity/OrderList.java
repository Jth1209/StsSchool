package domain.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString

public class OrderList {
	private String m_name;
	private String i_name;
	private int i_price;
	private int oi_count;
	private int orderprice;
	private Date orderdate;
}
