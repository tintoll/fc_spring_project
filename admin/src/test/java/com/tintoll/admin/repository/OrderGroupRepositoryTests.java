package com.tintoll.admin.repository;

import com.tintoll.admin.model.entity.OrderGroup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class OrderGroupRepositoryTests extends AdminUserRepositoryTests{

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Test
    public void create() {
        OrderGroup orderGroup = new OrderGroup();
        orderGroup.setStatus("STATUS");
        orderGroup.setOrderType("WAITING");

        // orderGroup.setUserId(1L);

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);

        assertNotNull(newOrderGroup);
    }
}