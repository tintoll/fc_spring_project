package com.tintoll.admin.controller.api;

import com.tintoll.admin.controller.CrudController;
import com.tintoll.admin.ifs.CrudInterface;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.ItemApiRequest;
import com.tintoll.admin.model.network.response.ItemApiResponse;
import com.tintoll.admin.service.ItemApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/item")
public class ItemApiController extends CrudController<ItemApiRequest, ItemApiResponse> {


    @Autowired
    private ItemApiLogicService itemApiLogicService;

    @PostConstruct
    public void init() {
        baseService = itemApiLogicService;
    }
//
//    @Override
//    @PostMapping("")
//    public Header<ItemApiResponse> create(@RequestBody Header<ItemApiRequest> request) {
//        return itemApiLogicService.create(request);
//    }
//
//    @Override
//    @GetMapping("{id}")
//    public Header<ItemApiResponse> read(@PathVariable Long id) {
//        return itemApiLogicService.read(id);
//    }
//
//    @Override
//    @PutMapping("")
//    public Header<ItemApiResponse> update(@RequestBody Header<ItemApiRequest> request) {
//        return itemApiLogicService.update(request);
//    }
//
//    @Override
//    @DeleteMapping("{id}")
//    public Header delete(@PathVariable Long id) {
//        return itemApiLogicService.delete(id);
//    }
}
