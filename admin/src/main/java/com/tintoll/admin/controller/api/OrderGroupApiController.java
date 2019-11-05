package com.tintoll.admin.controller.api;

import com.tintoll.admin.ifs.CrudInterface;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.OrderGroupRequest;
import com.tintoll.admin.model.network.response.OrderGroupResponse;
import com.tintoll.admin.service.OrderGroupApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orderGroup")
public class OrderGroupApiController implements CrudInterface<OrderGroupRequest, OrderGroupResponse> {

    @Autowired
    private OrderGroupApiService orderGroupApiService;

    @Override
    @PostMapping("")
    public Header<OrderGroupResponse> create(@RequestBody Header<OrderGroupRequest> request) {
        return orderGroupApiService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<OrderGroupResponse> read(@PathVariable Long id) {
        return orderGroupApiService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<OrderGroupResponse> update(@RequestBody Header<OrderGroupRequest> request) {
        return orderGroupApiService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return orderGroupApiService.delete(id);
    }
}
