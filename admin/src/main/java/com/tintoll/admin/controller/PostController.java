package com.tintoll.admin.controller;

import com.tintoll.admin.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    @PostMapping("/postMethod")
    public SearchParam postMethod(@RequestBody SearchParam searchParam) {
        return searchParam;
    }

    @PutMapping("/putMethod")
    public void putMethod() {

    }

    @PatchMapping("/patchMehod")
    public void patchMethod() {

    }

    @DeleteMapping("/deleteMethod")
    public void deleteMethod() {

    }
}
