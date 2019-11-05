package com.tintoll.admin.service;

import com.tintoll.admin.ifs.CrudInterface;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.OrderGroupRequest;
import com.tintoll.admin.model.network.response.OrderGroupResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderGroupApiService implements CrudInterface<OrderGroupRequest, OrderGroupResponse> {
    @Override
    public Header<OrderGroupResponse> create(Header<OrderGroupRequest> request) {
        return null;
    }

    @Override
    public Header<OrderGroupResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<OrderGroupResponse> update(Header<OrderGroupRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }
}
