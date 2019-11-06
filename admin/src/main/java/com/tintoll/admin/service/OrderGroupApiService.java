package com.tintoll.admin.service;

import com.tintoll.admin.ifs.CrudInterface;
import com.tintoll.admin.model.entity.OrderGroup;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.OrderGroupRequest;
import com.tintoll.admin.model.network.response.OrderGroupResponse;
import com.tintoll.admin.repository.OrderGroupRepository;
import com.tintoll.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderGroupApiService implements CrudInterface<OrderGroupRequest, OrderGroupResponse> {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<OrderGroupResponse> create(Header<OrderGroupRequest> request) {

        OrderGroupRequest body = request.getData();
        OrderGroup orderGroup = OrderGroup.builder()
                                .status(body.getStatus())
                                .orderType(body.getOrderType())
                                .revAddress(body.getRevAddress())
                                .revName(body.getRevName())
                                .paymentType(body.getPaymentType())
                                .totalPrice(body.getTotalPrice())
                                .totalQuantity(body.getTotalQuantity())
                                .orderAt(LocalDateTime.now())
                                .user(userRepository.getOne(body.getUserId()))
                                .build();

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);

        return response(newOrderGroup);
    }



    @Override
    public Header<OrderGroupResponse> read(Long id) {
        return orderGroupRepository.findById(id)
                .map(orderGroup -> response(orderGroup))
                .orElseGet(() -> Header.ERROR("주문내역 없음"));
    }

    @Override
    public Header<OrderGroupResponse> update(Header<OrderGroupRequest> request) {
        OrderGroupRequest body = request.getData();
        return orderGroupRepository.findById(body.getId())
                .map(orderGroup -> {
                    orderGroup
                            .setStatus(body.getStatus())
                            .setOrderType(body.getOrderType())
                            .setRevAddress(body.getRevAddress())
                            .setRevName(body.getRevName())
                            .setPaymentType(body.getPaymentType())
                            .setTotalPrice(body.getTotalPrice())
                            .setTotalQuantity(body.getTotalQuantity())
                            .setOrderAt(body.getOrderAt())
                            .setArrivalDate(body.getArrivalDate())
                            .setUser(userRepository.getOne(body.getUserId()));
                    return orderGroupRepository.save(orderGroup);
                }).map(newOrderGroup -> response(newOrderGroup))
                .orElseGet(() -> Header.ERROR("주문내역 없음"));
    }

    @Override
    public Header delete(Long id) {
        return orderGroupRepository.findById(id)
                .map(orderGroup -> {
                    orderGroupRepository.delete(orderGroup);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("주문내역 없음"));
    }

    private Header<OrderGroupResponse> response(OrderGroup orderGroup) {

        OrderGroupResponse orderGroupResponse
                = OrderGroupResponse.builder()
                    .id(orderGroup.getId())
                    .status(orderGroup.getStatus())
                    .orderType(orderGroup.getOrderType())
                    .revAddress(orderGroup.getRevAddress())
                    .revName(orderGroup.getRevName())
                    .paymentType(orderGroup.getPaymentType())
                    .totalPrice(orderGroup.getTotalPrice())
                    .totalQuantity(orderGroup.getTotalQuantity())
                    .orderAt(orderGroup.getOrderAt())
                    .arrivalDate(orderGroup.getArrivalDate())
                    .userId(orderGroup.getUser().getId())
                    .build();

        return Header.OK(orderGroupResponse);
    }
}
