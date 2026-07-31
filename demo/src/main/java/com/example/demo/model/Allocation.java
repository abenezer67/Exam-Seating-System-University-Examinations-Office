package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Allocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Student student;

    @ManyToOne
    private Room room;

    private int seatNumber;

    public Allocation() {}
    public Allocation(Student student, Room room, int seatNumber) {
        this.student = student;
        this.room = room;
        this.seatNumber = seatNumber;
    }

    public Long getId() { 
        return id; 
    }
    public Student getStudent() { 
        return student; 
    }
    public Room getRoom() { 
        return room; 
    }
    public int getSeatNumber() { 
        return seatNumber; 
    }
}