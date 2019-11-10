package com.tintoll.admin.controller.api;


import com.tintoll.admin.controller.CrudController;
import com.tintoll.admin.model.entity.Partner;
import com.tintoll.admin.model.network.request.PartnerApiRequest;
import com.tintoll.admin.model.network.response.PartnerApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/partner")
public class PartnerApiController extends CrudController<PartnerApiRequest, PartnerApiResponse, Partner> {

}
