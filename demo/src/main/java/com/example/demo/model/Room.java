package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Room {
    @Id
    private String roomId;
    private int capacity;

    public Room() {}
    public Room(String roomId, int capacity) {
        this.roomId = roomId;
        this.capacity = capacity;
    }

    public String getRoomId() { 
        return roomId; 
    }
    public void setRoomId(String roomId) { 
        this.roomId = roomId; 
    }
    
    public int getCapacity() { 
        return capacity; 
    }
    public void setCapacity(int capacity) { 
        this.capacity = capacity; 
    }
}