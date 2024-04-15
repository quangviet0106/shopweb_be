package com.example.shopapp.controller;

import com.example.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")

public class CategoryController {

    @GetMapping("")
    public ResponseEntity<String> getAllCategories(@RequestParam("page") int page,
                                                   @RequestParam("limit") int limit){
        return ResponseEntity.ok(String.format("get all categories with page = %d, limit = %d",page,limit));
    }
    @PostMapping("")
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 BindingResult result){
        if (result.hasErrors()){
           List<String> errorMessages =  result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
           return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("This is insertCategory" + categoryDTO);
    }
}
