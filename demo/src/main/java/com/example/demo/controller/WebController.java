package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.SeatingManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebController {
    @Autowired private SeatingManagerService service;

    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student) { 
        return service.addStudent(student); 
    }

    @PostMapping("/room")
    public Room addRoom(@RequestBody Room room) { 
        return service.addRoom(room); 
    }

    @PostMapping("/allocate")
    public String allocate() { 
        return service.generateArrangement(); 
    }

    @GetMapping("/allocations")
    public List<Allocation> getAllocations() { 
        return service.getAllAllocations(); 
    }

    @DeleteMapping("/room/{id}")
    public String removeRoom(@PathVariable String id) {
        return service.removeRoom(id);
    }

    @DeleteMapping("/student/{id}")
    public String removeStudent(@PathVariable String id) {
        return service.removeStudent(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleValidationError(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
    }
}