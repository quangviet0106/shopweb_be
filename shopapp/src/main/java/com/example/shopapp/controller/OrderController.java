package com.example.shopapp.controller;


import com.example.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${spring.sendgrid.api-key}/orders")
public class OrderController {

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO,
                                         BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return ResponseEntity.ok("createOrder successfully!");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId){
        try {
            return ResponseEntity.ok("Lấy ra danh sách order từ userID");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{user_id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("user_id") Long userId,
                                         @Valid @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok("Cập nhật thông tin 1 order");
    }

    @DeleteMapping("{user_id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("user_id") Long userId){
        return ResponseEntity.ok("Delete order successfully!");
    }
}
