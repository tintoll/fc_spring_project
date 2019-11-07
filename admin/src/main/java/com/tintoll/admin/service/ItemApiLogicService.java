package com.tintoll.admin.service;

import com.tintoll.admin.model.entity.Item;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.ItemApiRequest;
import com.tintoll.admin.model.network.response.ItemApiResponse;
import com.tintoll.admin.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest,ItemApiResponse, Item> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest body = request.getData();

        Item item = Item.builder()
                .status(body.getStatus())
                .name(body.getName())
                .title(body.getTitle())
                .content(body.getContent())
                .price(body.getPrice())
                .brandName(body.getBrandName())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);
        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(item -> response(item))
                .orElseGet(() -> Header.ERROR("아이템 정보 없음 "));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest body = request.getData();

        return baseRepository.findById(body.getId())
                .map(item -> {
                    item.setStatus(body.getStatus())
                            .setTitle(body.getTitle())
                            .setContent(body.getContent())
                            .setName(body.getName())
                            .setPrice(body.getPrice())
                            .setBrandName(body.getBrandName())
                            .setPartner(partnerRepository.getOne(body.getPartnerId()))
                            .setStatus(body.getStatus())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setUnregisteredAt(body.getUnregisteredAt());

                    return baseRepository.save(item);

                }).map(modifyItem -> response(modifyItem))
                .orElseGet(() -> Header.ERROR("아이템 정보 없음"));

    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(item -> {
                    baseRepository.delete(item);
                    return Header.OK();
                }).orElseGet(() -> Header.ERROR("아이템 정보 없음 "));
    }

    private Header<ItemApiResponse> response(Item item) {
        ItemApiResponse itemApiResponse = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return Header.OK(itemApiResponse);
    }
}
