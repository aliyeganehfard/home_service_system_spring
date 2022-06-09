package org.maktab.home_service_system.controller;

import org.maktab.home_service_system.controller.dto.OrderDto;
import org.maktab.home_service_system.controller.util.OrderGridSearchHolder;
import org.maktab.home_service_system.model.service.impl.OrderServiceImpl;
import org.maktab.home_service_system.model.util.OrderState;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("api/order")
public class OrderController {
    private final OrderServiceImpl orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderServiceImpl orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.save(orderDto));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<OrderDto>> findAll() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/findByOrderState/{orderState}")
    public ResponseEntity<List<OrderDto>> findByUserState(@PathVariable String orderState) {
        return ResponseEntity.ok(orderService.findByOrderState(OrderState.valueOf(orderState)));
    }

    @GetMapping("/selectAnExpertToOrder/{orderId}/{offerId}")
    public ResponseEntity<OrderDto> selectAnExpertToOrder(@PathVariable String orderId, @PathVariable String offerId) {
        return ResponseEntity.ok(orderService.selectAnExpertToOrder(
                Integer.valueOf(orderId),
                Integer.valueOf(offerId)
        ));
    }

    @GetMapping("/changeOrderState/{orderId}/{orderState}")
    public ResponseEntity<OrderDto> changeOrderState(@PathVariable String orderId, @PathVariable String orderState) {
        return ResponseEntity.ok(orderService.changeOrderState(
                Integer.valueOf(orderId),
                OrderState.valueOf(orderState)
        ));
    }

    @GetMapping("/findCustomerOrders/{customerId}")
    public ResponseEntity<List<OrderDto>> findCustomerOrders(@PathVariable String customerId) {
        return ResponseEntity.ok(orderService.findAllOrderByCustomerId(Integer.valueOf(customerId)));
    }

    @GetMapping("/findSuggestedOrderForExpert/{expertId}")
    public ResponseEntity<List<OrderDto>> findSuggestedOrderForExpert(@PathVariable String expertId) {
        return ResponseEntity.ok(orderService.findSuggestedOrderForExpert(Integer.valueOf(expertId)));
    }

    @GetMapping("/findCustomerOrders/{customerId}/{orderState}")
    public ResponseEntity<List<OrderDto>> findCustomerOrders(@PathVariable String customerId, @PathVariable String orderState) {
        return ResponseEntity.ok(orderService.findCustomerOrders(Integer.valueOf(customerId), orderState));
    }

    @GetMapping("/findExpertOrders/{expertId}")
    public ResponseEntity<List<OrderDto>> findExpertOrders(@PathVariable String expertId) {
        return ResponseEntity.ok(orderService.findExpertOrders(Integer.valueOf(expertId)));
    }

    @GetMapping("/findFinishedOrderWithCustomerId/{customerId}")
    public ResponseEntity<List<OrderDto>> findFinishedOrderWithCustomerId(@PathVariable String customerId) {
        var orderListDto =
                orderService.findFinishedOrderWithCustomerId(Integer.valueOf(customerId)).stream()
                        .map(order -> modelMapper.map(order, OrderDto.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(orderListDto);
    }

    @GetMapping("/finishOfOrder/{orderId}")
    public ResponseEntity<OrderDto> finishOfOrder(@PathVariable Integer orderId){
        var orderDto =
                modelMapper.map(orderService.finishOfOrder(orderId),OrderDto.class);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/searchGrid")
    public ResponseEntity<List<OrderDto>> searchGrid(@RequestBody OrderGridSearchHolder holder){
        var orderListDto = orderService.gridSearch(holder).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderListDto);
    }

    @GetMapping("/numberOfCustomerOrders/{customerId}")
    public ResponseEntity<Integer> numberOfCustomerOrders(@PathVariable Integer customerId){
        return ResponseEntity.ok(orderService.numberOfCustomerOrders(customerId));
    }
}
