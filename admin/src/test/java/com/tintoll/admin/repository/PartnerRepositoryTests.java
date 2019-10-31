package com.tintoll.admin.repository;

import com.tintoll.admin.model.entity.Partner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class PartnerRepositoryTests extends AdminUserRepositoryTests{

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    public void create() {
        Partner partner = new Partner();

        partner.setCreatedAt(LocalDateTime.now());
        partner.setCreatedBy("AdminServer");

        // partner.setCategoryId(1L);

        Partner newPartner = partnerRepository.save(partner);

        assertNotNull(newPartner);
    }

}