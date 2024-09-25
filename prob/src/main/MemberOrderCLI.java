package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import domain.entity.Member;
import domain.entity.Order;
import domain.entity.OrderItem;
import domain.value.Address;

public class MemberOrderCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a new Member instance
        System.out.print("Enter Member ID: ");
        Long memberId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Member Name: ");
        String memberName = scanner.nextLine();

        System.out.print("Enter Member Address Street: ");
        String street = scanner.nextLine();

        System.out.print("Enter Member Address City: ");
        String city = scanner.nextLine();

        System.out.print("Enter Member Address Zip: ");
        String zip = scanner.nextLine();

        Address address = new Address(street, city, zip);

        Member member = new Member(memberId, memberName, address, new ArrayList<>());

        // Create a new Order instance
        System.out.print("Enter Order ID: ");
        Long orderId = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Order order = new Order();
        order.setId(orderId);
        order.setMember(member);
        order.setOrderDate(new Date());

        // Add OrderItems
        List<OrderItem> orderItems = new ArrayList<>();
        while (true) {
            System.out.print("Enter OrderItem ID (or 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            Long orderItemId = Long.parseLong(input);
            OrderItem orderItem = new OrderItem(orderItemId); // Adjust according to your OrderItem class
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        // Add order to member's orders
        member.getOrders().add(order);

        // Print the member and order details
        System.out.println("Member created successfully!");
        System.out.println("Member ID: " + member.getId());
        System.out.println("Member Name: " + member.getName());
        System.out.println("Member Address: " + member.getAddress());
        System.out.println("Orders: ");
        for (Order o : member.getOrders()) {
            System.out.println(" - Order ID: " + o.getId());
            System.out.println("   Order Date: " + o.getOrderDate());
            System.out.println("   Order Items: ");
            for (OrderItem item : o.getOrderItems()) {
                System.out.println("     - OrderItem ID: " + item.getId());
            }
        }

        scanner.close();
    }
}
