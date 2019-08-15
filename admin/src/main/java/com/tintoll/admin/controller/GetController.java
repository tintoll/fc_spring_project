package com.tintoll.admin.controller;

import com.tintoll.admin.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GetController {
    // http://localhost:8080/api/getMethod
    @RequestMapping(method = RequestMethod.GET, value = "/getMethod")
    public String getMethod() {
        return "Hi getMethod";
    }

    // http://localhost:8080/api/getParameter?id=111&password=pass
    @GetMapping("/getParameter")
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd) {
        String password = "pass";

        System.out.println("id : "+ id);
        System.out.println("password : "+ password);

        return id + password;
    }

    // http://localhost:8080/api/getMultiParameter?account=tintoll&email=b@b.com&page=1
    @GetMapping("/getMultiParameter")
    // 리턴으로 객체를 주면 자동으로 json 형태로 spring이 변경하여 보내준다.
    public SearchParam getMultiParameter(SearchParam searchParam) {
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());
        return searchParam;
    }
}
