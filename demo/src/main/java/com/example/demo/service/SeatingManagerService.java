package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;

@Service
public class SeatingManagerService {
    @Autowired private StudentRepository studentRepo;
    @Autowired private RoomRepository roomRepo;
    @Autowired private AllocationRepository allocationRepo;

    public Student addStudent(Student student) {
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty.");
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        try {
            return studentRepo.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save student: " + e.getMessage());
        }
    }

    public Room addRoom(Room room) {
        if (room.getRoomId() == null || room.getRoomId().trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be empty.");
        }
        if (room.getCapacity() <= 0) {
            throw new IllegalArgumentException("Room capacity must be greater than zero.");
        }
        try {
            return roomRepo.save(room);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save room: " + e.getMessage());
        }
    }

    public List<Allocation> getAllAllocations() { return allocationRepo.findAll(); }

    public String generateArrangement() {
        allocationRepo.deleteAll();

        List<Student> students = studentRepo.findAll();
        List<Room> rooms = roomRepo.findAll();

        Collections.shuffle(students);

        int studentIndex = 0;

        for (Room room : rooms) {
            for (int seat = 1; seat <= room.getCapacity(); seat++) {
                if (studentIndex < students.size()) {
                    Student currentStudent = students.get(studentIndex);
                    Allocation allocation = new Allocation(currentStudent, room, seat);
                    allocationRepo.save(allocation);
                    studentIndex++;
                } else {
                    return "Arrangement Complete! All students assigned.";
                }
            }
        }
        return "Warning: Not enough seats for all students!";
    }

    public String removeRoom(String roomId) {
        if (roomRepo.existsById(roomId)) {
            allocationRepo.deleteAll();
            roomRepo.deleteById(roomId);
            return "Room '" + roomId + "' deleted successfully! Seating arrangement was reset.";
        } else {
            return "Error: Room ID not found in database!";
        }
    }

    public String removeStudent(String studentId) {
        if (studentRepo.existsById(studentId)) {
            allocationRepo.deleteAll();
            studentRepo.deleteById(studentId);
            return "Student '" + studentId + "' deleted successfully! Seating arrangement was reset.";
        } else {
            return "Error: Student ID not found in database!";
        }
    }
}