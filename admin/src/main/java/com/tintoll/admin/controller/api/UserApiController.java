package com.tintoll.admin.controller.api;

import com.tintoll.admin.ifs.CrudInterface;
import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.UserApiRequest;
import com.tintoll.admin.model.network.response.UserApiResponse;
import com.tintoll.admin.service.UserApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserApiController implements CrudInterface<UserApiRequest, UserApiResponse> {


    @Autowired
    private UserApiLogicService userApiLogicService;

    @Override
    @PostMapping("")
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> userApiRequest) {
        return userApiLogicService.create(userApiRequest);
    }

    @Override
    @GetMapping("/{id}")
    public Header<UserApiResponse> read(@PathVariable Long id) {
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> userApiRequest) {
        return userApiLogicService.update(userApiRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    public Header<UserApiResponse> delete(@PathVariable Long id) {
        return userApiLogicService.delete(id);
    }
}
