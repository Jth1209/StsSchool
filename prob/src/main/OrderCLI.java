package main;

import java.util.Date;
import java.util.Scanner;

import domain.entity.Delivery;
import domain.entity.Member;
import domain.entity.Order;
import domain.entity.OrderItem;

public class OrderCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a new Order instance
        Order order = new Order();

        // Set order details
        System.out.print("Enter Order ID: ");
        order.setId(scanner.nextLong());
        scanner.nextLine(); // consume newline

        // Assuming Member and Delivery classes have appropriate constructors or setters
        System.out.print("Enter Member ID: ");
        Long memberId = scanner.nextLong();
        scanner.nextLine(); // consume newline
        Member member = new Member(memberId); // Adjust according to your Member class
        order.setMember(member);

        System.out.print("Enter Delivery ID: ");
        Long deliveryId = scanner.nextLong();
        scanner.nextLine(); // consume newline
        Delivery delivery = new Delivery(deliveryId); // Adjust according to your Delivery class
        order.setDelivery(delivery);

        order.setOrderDate(new Date());

        // Add OrderItems
        while (true) {
            System.out.print("Enter OrderItem ID (or 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            Long orderItemId = Long.parseLong(input);
            OrderItem orderItem = new OrderItem(orderItemId); // Adjust according to your OrderItem class
            order.addOrderItem(orderItem);
        }

        // Print the order details
        System.out.println("Order created successfully!");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Member ID: " + order.getMember().getId());
        System.out.println("Delivery ID: " + order.getDelivery().getId());
        System.out.println("Order Date: " + order.getOrderDate());
        System.out.println("Order Items: ");
        for (OrderItem item : order.getOrderItems()) {
            System.out.println(" - OrderItem ID: " + item.getId());
        }

        scanner.close();
    }
}
