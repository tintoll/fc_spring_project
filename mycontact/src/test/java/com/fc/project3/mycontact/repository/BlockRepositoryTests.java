package com.fc.project3.mycontact.repository;

import com.fc.project3.mycontact.domain.Block;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlockRepositoryTests {

    @Autowired
    private BlockRepository blockRepository;


    @Test
    void crud() {
        Block block = new Block().builder()
                .name("martin")
                .reason("그냥")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .build();

        blockRepository.save(block);


        List<Block> blocks = blockRepository.findAll();

        assertThat(blocks.size()).isEqualTo(1);

    }


}