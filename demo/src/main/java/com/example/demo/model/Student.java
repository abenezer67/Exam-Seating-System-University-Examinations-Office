package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student extends Person {
    @Id
    private String studentId;
    private String department;

    public Student() {}
    public Student(String studentId, String name, String department) {
        super(name);
        this.studentId = studentId;
        this.department = department;
    }

    public String getStudentId() { 
        return studentId; 
    }
    public void setStudentId(String studentId) { 
        this.studentId = studentId; 
    }
    
    public String getDepartment() {
        return department; 
    }
    public void setDepartment(String department) { 
        this.department = department; 
    }
}