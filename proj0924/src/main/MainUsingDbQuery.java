package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import config.DbConfig;
import config.DbQueryConfig;
import dbquery.DbQuery;
import domain.dao.Dao;
import domain.dao.ItemDao;
import domain.entity.Item;
import domain.entity.Member;
import domain.entity.Order;
import domain.entity.OrderItem;
import domain.entity.OrderList;
import domain.value.Address;
import error.NoValueException;
import error.OverValueException;

public class MainUsingDbQuery {

	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {

		ctx = new AnnotationConfigApplicationContext(AppCtx.class);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		help();
		processShowMember();
		processShowItem();
		
		while (true) {
			System.out.println("명령어를 입력하세요:");
			String command = reader.readLine();
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			if (command.startsWith("orderItem ")) {
				processListCommand(command.split(" "));
				continue;
			} else if (command.startsWith("orderList ")) {
				processUserOrderList(command.split(" "));
				continue;
			}else if (command.startsWith("member")) {
				processShowMember();
				continue;
			}else if (command.startsWith("item")) {
				processShowItem();
				continue;
			}else if (command.startsWith("help")) {
				help();
				continue;
			} else if (command.startsWith("newMember ")) {
				processNewMember(command.split(" "));
				continue;
			} else if (command.startsWith("newItem ")) {
				processNewItem(command.split(" "));
				continue;
			} else if (command.startsWith("newOrder ")) {
				processNewOrder(command.split(" "));
				continue;
			} else if (command.startsWith("checkOrder ")) {
				processCheckOrder(command.split(" "));
				continue;
			} else if (command.startsWith("checkOrderItem ")) {
				processCheckOrderItem(command.split(" "));
				continue;
			} else if (command.startsWith("deleteOrder ")) {
				processDeleteOrder(command.split(" "));
				continue;
			} else if (command.startsWith("deleteOrderItem ")) {
				processDeleteOrderItem(command.split(" "));
				continue;
			}
			printHelp();
		}
	}

	private static void processShowMember() {
		Dao dbQuery = ctx.getBean(Dao.class);
		List<Member> members = dbQuery.selectAllMember();
		System.out.println();
		System.out.println("사용자 상세 정보 == >> ");
		System.out.println();
		if (members == null) {
			System.out.println("사용자가 존재하지 않습니다.");
			return;
		}

		for (Member member : members) {
			System.out.println("사용자 일련 번호 : " + member.getId() + ", 이름 : " + member.getName()
					+ ", 사용자 세부 주소 ==>> 주소명 : " + member.getAddress().getCity() + ", 도로명 : "
					+ member.getAddress().getStreet() + ", 동호수 : " + member.getAddress().getZipcode());
		}
		System.out.println();
	}

	private static void processShowItem() {
		Dao dbQuery = ctx.getBean(Dao.class);
		List<Item> items = dbQuery.selectAllItem();
		System.out.println();
		System.out.println("상품 리스트 ==>> ");
		System.out.println();
		if (items == null) {
			System.out.println("존재하는 상품이 없습니다.");
			return;
		}
		for (Item item : items) {
			System.out.println("상품 번호 : " + item.getId() + ", 상품 명 : " + item.getName() + ", 상품 가격 : " + item.getPrice()
					+ ", 상품 재고 : " + item.getStockQuantity());
		}

		System.out.println();
	}

	private static void processNewOrder(String[] arg) {
		if (arg.length != 2) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		dbQuery.insertOrder(Long.parseLong(arg[1]));
		System.out.println();
		System.out.println("주문 생성 완료");
		System.out.println();
	}

	private static void processCheckOrder(String[] arg) {
		if (arg.length != 2) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		List<Order> orders = dbQuery.checkOrder(Long.parseLong(arg[1]));
		System.out.println();
		System.out.println("입력한 사용자의 주문 ==>> ");
		System.out.println();
		if (orders == null) {
			System.out.println("주문이 존재하지 않습니다.");
			return;
		} else {
			for (Order o : orders) {
				System.out.println("주문 정보 ==>> 주문 번호 : " + o.getId() + ", 주문자 번호 : " + o.getMember().getId()
						+ ", 주문 생성 시간 : " + o.getOrderDate());
				System.out.println();
			}
		}
		System.out.println();
	}

	private static void processCheckOrderItem(String[] arg) {
		if (arg.length != 2) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		List<OrderItem> orders = dbQuery.checkOrderItem(Long.parseLong(arg[1]));
		System.out.println();
		System.out.println("저장된 주문상품 목록 ==>> ");
		System.out.println();
		if (orders == null) {
			System.out.println("주문상품이 존재하지 않습니다.");
			return;
		}
		for (OrderItem oi : orders) {
			System.out.println("주문 정보 ==>> 주문상품 번호 : " + oi.getId() + ", 주문 번호 : " + oi.getOrder().getId()
					+ ", 상품 개수 : " + oi.getCount());
			System.out.println();
		}
		System.out.println();
	}

	private static void processListCommand(String[] arg) {
		if (arg.length != 4) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		try {
			dbQuery.insertOrderItem(Long.parseLong(arg[1]), Long.parseLong(arg[2]), Integer.parseInt(arg[3]));
		} catch (OverValueException e) {
			e.error();
			System.out.println();
			System.out.println("주문하신 수량이 상품의 재고량보다 많습니다. 수량을 조정해주세요.");
			System.out.println();
			return;
		}catch (NoValueException e) {
			e.error();
			System.out.println();
			System.out.println("주문하고자 하는 상품이 존재하지 않습니다. 상품을 다시 확인해주세요.");
			System.out.println();
			return;
		}
		System.out.println("상품이 주문리스트에 저장되었습니다!");
		System.out.println();
	}

	private static void processUserOrderList(String[] arg) {
		if (arg.length != 2) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		List<OrderList> orderLists = dbQuery.orders(Integer.parseInt(arg[1]));
		System.out.println();
		System.out.println("주문 리스트 ==>> ");
		System.out.println();
		if (orderLists == null) {
			System.out.println("주문정보가 존재하지 않습니다. 주문을 진행해주세요!");
			return;
		}
		for (OrderList ol : orderLists) {
			System.out.println("주문자 : " + ol.getM_name() + ", 상품명 : " + ol.getI_name() + ", 상품 가격 : " + ol.getI_price()
					+ ", 주문 수량 : " + ol.getOi_count() + ", 총 가격 : " + ol.getOrderprice() + ", 주문 날짜 : "
					+ ol.getOrderdate());
		}
		System.out.println();
	}

	private static void processNewMember(String[] arg) {
		if (arg.length != 5) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		Member member = new Member(arg[1], new Address(arg[2], arg[3], arg[4]));
		dbQuery.insertMember(member);
		System.out.println("사용자 생성 완료!");
		System.out.println();
	}

	private static void processNewItem(String[] arg) {
		if (arg.length != 4) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		Item item = new Item(arg[1], Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
		dbQuery.insertItem(item);
		System.out.println("새로운 상품 생성 완료!");
		System.out.println();
	}

	private static void processDeleteOrder(String[] arg) {
		if (arg.length != 2) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		dbQuery.deleteOrder(Long.parseLong(arg[1]));
		System.out.println();
		System.out.println("주문 삭제 완료");
		System.out.println();
	}

	private static void processDeleteOrderItem(String[] arg) {
		if (arg.length != 2) {
			printHelp();
			return;
		}
		Dao dbQuery = ctx.getBean(Dao.class);
		dbQuery.deleteOrderItem(Long.parseLong(arg[1]));
		System.out.println();
		System.out.println("주문상품 삭제 완료");
		System.out.println();
	}

	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법:");
		System.out.println("newMember 이름 도시명 도로명 동호수 => 현재 존재하는 모든 사용자의 상세정보를 출력합니다.");
		System.out.println("newItem 상품명 상품가격 상품재고 => 현재 존재하는 모든 상품의 상제정보를 출력합니다.");
		System.out.println("newOrder 사용자번호 => 입력한 사용자 번호에 주문을 한개 추가합니다.");
		System.out.println("checkOrder 사용자번호 => 입력한 사용자 번호가 가지고있는 주문을 모두 나타냅니다.");
		System.out.println("checkOrderItem 사용자번호 => 입력한 사용자 번호가 가지고있는 주문을 모두 나타냅니다.");
		System.out.println("orderItem 주문번호 상품번호 상품수량 => 번호에 해당하는 사용자가 번호에 해당하는 아이템을 상품 수량에 맞게 주문리스트에 저장합니다.");
		System.out.println("orderList 사용자번호 => 해당하는 번호의 사용자가 주문하고자 하는 물품의 리스트를 확인할 수 있습니다. ");
		System.out.println("deleteOrder 주문번호 => 입력한 번호의 주문이 삭제됩니다. ** 주문에 담겨있던 주문상품 또한 함께 삭제됩니다. ** ");
		System.out.println("deleteOrderItem 주문상품번호 => 입력한 번호의 주문상품이 주문목록에서 삭제됩니다. ");
		System.out.println();
	}
	
	private static void help() {
		System.out.println();
		System.out.println("명령어 사용법:");
		System.out.println("newMember 이름 도시명 도로명 동호수 => 현재 존재하는 모든 사용자의 상세정보를 출력합니다.");
		System.out.println("newItem 상품명 상품가격 상품재고 => 현재 존재하는 모든 상품의 상제정보를 출력합니다.");
		System.out.println("newOrder 사용자번호 => 입력한 사용자 번호에 주문을 한개 추가합니다.");
		System.out.println("checkOrder 사용자번호 => 입력한 사용자 번호가 가지고있는 주문을 모두 나타냅니다.");
		System.out.println("checkOrderItem 사용자번호 => 입력한 사용자 번호가 가지고있는 주문을 모두 나타냅니다.");
		System.out.println("orderItem 주문번호 상품번호 상품수량 => 번호에 해당하는 사용자가 번호에 해당하는 아이템을 상품 수량에 맞게 주문리스트에 저장합니다.");
		System.out.println("orderList 사용자번호 => 해당하는 번호의 사용자가 주문하고자 하는 물품의 리스트를 확인할 수 있습니다. ");
		System.out.println("deleteOrder 주문번호 => 입력한 번호의 주문이 삭제됩니다. ** 주문에 담겨있던 주문상품 또한 함께 삭제됩니다. ** ");
		System.out.println("deleteOrderItem 주문상품번호 => 입력한 번호의 주문상품이 주문목록에서 삭제됩니다. ");
		System.out.println();
	}
}
