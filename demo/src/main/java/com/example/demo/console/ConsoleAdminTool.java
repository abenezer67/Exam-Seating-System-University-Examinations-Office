package com.example.demo.console;

import com.example.demo.model.Room;
import com.example.demo.model.Student;
import com.example.demo.service.SeatingManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleAdminTool implements CommandLineRunner {

    @Autowired
    private SeatingManagerService service;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("========================================");
        System.out.println(" Exam Seating System - Console Admin Tool");
        System.out.println("========================================");

        while (running) {
            System.out.println("\n1. Add Student");
            System.out.println("2. Add Room");
            System.out.println("3. Continue to Web Server");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addStudentFromConsole(scanner);
                    break;
                case "2":
                    addRoomFromConsole(scanner);
                    break;
                case "3":
                    running = false;
                    System.out.println("Starting web server...");
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    private void addStudentFromConsole(Scanner scanner) {
        try {
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine().trim();
            if (studentId.isEmpty()) {
                System.out.println("Error: Student ID cannot be empty.");
                return;
            }

            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Error: Name cannot be empty.");
                return;
            }

            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();
            if (department.isEmpty()) {
                System.out.println("Error: Department cannot be empty.");
                return;
            }

            service.addStudent(new Student(studentId, name, department));
            System.out.println("Student '" + name + "' added successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error while adding student: " + e.getMessage());
        }
    }

    private void addRoomFromConsole(Scanner scanner) {
        try {
            System.out.print("Enter Room ID: ");
            String roomId = scanner.nextLine().trim();
            if (roomId.isEmpty()) {
                System.out.println("Error: Room ID cannot be empty.");
                return;
            }

            System.out.print("Enter Seat Capacity: ");
            String capacityInput = scanner.nextLine().trim();

            int capacity;
            try {
                capacity = Integer.parseInt(capacityInput);
            } catch (NumberFormatException e) {
                System.out.println("Error: Capacity must be a whole number.");
                return;
            }

            service.addRoom(new Room(roomId, capacity));
            System.out.println("Room '" + roomId + "' added successfully with capacity " + capacity + ".");

        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error while adding room: " + e.getMessage());
        }
    }
}