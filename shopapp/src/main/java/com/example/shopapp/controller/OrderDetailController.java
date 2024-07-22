package com.example.shopapp.controller;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.responses.OrderDetailResponse;
import com.example.shopapp.services.OrderDetailSerivce;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${spring.sendgrid.api-key}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailSerivce orderDetailSerivce;
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO){
        try {
           return ResponseEntity.ok(OrderDetailResponse.convertFromOrderDetail(orderDetailSerivce.createOrderDetail(orderDetailDTO)));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws DataNotFoundException {
        //return ResponseEntity.ok(orderDetailSerivce.getOrderDetail(id));
        try{
            return ResponseEntity.ok(OrderDetailResponse.convertFromOrderDetail(orderDetailSerivce.getOrderDetail(id)));
        }
        catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body("Cannot find order detail with id: "+id);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId){
        List<OrderDetail> orderDetails = orderDetailSerivce.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream().map(OrderDetailResponse::convertFromOrderDetail).toList();
        return ResponseEntity.ok(orderDetailResponses);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
                                               @RequestBody OrderDetailDTO newOrderDetailDTO) throws DataNotFoundException {
        try {
            return ResponseEntity.ok(orderDetailSerivce.updateOrderDetail(id, newOrderDetailDTO));
        }catch (DataNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        orderDetailSerivce.deleteOrderDetail(id);
        return ResponseEntity.ok().body("Delete orderdetail with id: "+id+"successfully!");
    }
}
