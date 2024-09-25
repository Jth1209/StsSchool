package main;

import java.util.ArrayList;
import java.util.Scanner;

import domain.entity.Member;
import domain.value.Address;

public class MemberCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a new Member instance
        System.out.print("Enter Member ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Member Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Member Address (Street, City, Zip): ");
        String street = scanner.nextLine();
        String city = scanner.nextLine();
        String zip = scanner.nextLine();
        Address address = new Address(street, city, zip); // Adjust according to your Address class

        Member member = new Member(id, name, address, new ArrayList<>());

        // Print the member details
        System.out.println("Member created successfully!");
        System.out.println("Member ID: " + member.getId());
        System.out.println("Member Name: " + member.getName());
        System.out.println("Member Address: " + member.getAddress());

        scanner.close();
    }
}
