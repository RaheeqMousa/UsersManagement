package com.example.UsersManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String,String>> healthCheck(){
        Map<String,String> res=new HashMap<>();
        res.put("status","up");
        res.put("message","The app is running");
        return ResponseEntity.ok(res);
    }
}
