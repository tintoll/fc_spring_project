package com.tintoll.admin.service;


import com.tintoll.admin.model.entity.Category;
import com.tintoll.admin.model.entity.Partner;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.PartnerApiRequest;
import com.tintoll.admin.model.network.response.PartnerApiResponse;
import com.tintoll.admin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerApiLogicService extends BaseService<PartnerApiRequest, PartnerApiResponse, Partner> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {
        PartnerApiRequest dto = request.getData();

        Category category = categoryRepository.findById(dto.getCategoryId()).orElse(null);

        Partner partner = Partner.builder()
                .name(dto.getName())
                .status(dto.getStatus())
                .address(dto.getAddress())
                .callCenter(dto.getCallCenter())
                .partnerNumber(dto.getPartnerNumber())
                .businessNumber(dto.getBusinessNumber())
                .ceoName(dto.getCeoName())
                .category(category)
                .build();

        Partner newPartner = baseRepository.save(partner);
        return Header.OK(response(newPartner));
    }



    @Override
    public Header<PartnerApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(partner -> response(partner))
                .map(Header::OK)
                .orElseGet( () -> Header.ERROR("파트너 정보 없음 "));
    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {

        PartnerApiRequest dto = request.getData();

        return baseRepository.findById(dto.getId())
                .map(partner -> {
                    partner.setAddress(dto.getAddress())
                           .setBusinessNumber(dto.getBusinessNumber())
                           .setCallCenter(dto.getCallCenter())
                           .setCeoName(dto.getCeoName());
                    
                    baseRepository.save(partner);
                    return Header.OK(response(partner));
                }).orElseGet( () -> Header.ERROR("파트너 정보 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(partner -> {
                    baseRepository.delete(partner);
                    return Header.OK();
                }).orElseGet( () -> Header.ERROR("파트너 정보 없음"));
    }

    private PartnerApiResponse response(Partner partner) {

        PartnerApiResponse body = PartnerApiResponse.builder()
                .id(partner.getId())
                .name(partner.getName())
                .status(partner.getStatus())
                .address(partner.getAddress())
                .callCenter(partner.getCallCenter())
                .partnerNumber(partner.getPartnerNumber())
                .businessNumber(partner.getBusinessNumber())
                .ceoName(partner.getCeoName())
                .registeredAt(partner.getRegisteredAt())
                .unregisteredAt(partner.getUnregisteredAt())
                .categoryId(partner.getCategory().getId())
                .build();

        return body;
    }
}
