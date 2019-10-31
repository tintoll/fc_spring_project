package com.tintoll.admin.repository;

import com.tintoll.admin.AdminApplicationTests;
import com.tintoll.admin.model.entity.Item;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

public class ItemRepositoryTest extends AdminApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        Item item = new Item();
        item.setName("컴퓨터");
        item.setTitle("삼성 노트북");
        item.setPrice(new BigDecimal(100000));
        item.setContent("삼성 노트북 설명");

        item.setPartnerId(1L);

        Item newItem = itemRepository.save(item);

        assertNotNull(newItem);

    }

    @Test
    public void read() {
        Long id = 1L;

        Optional<Item> item = itemRepository.findById(id);

        assertTrue(item.isPresent());
    }

}