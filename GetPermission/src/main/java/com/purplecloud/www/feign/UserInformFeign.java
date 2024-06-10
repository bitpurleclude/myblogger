package com.purplecloud.www.feign;

import com.purplecloud.www.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userInformClient",url = "http://localhost:10088")
public interface UserInformFeign {
    @PostMapping("/user-info/insertUserInform")
    void insertUserInform(@RequestParam("id") int id, @RequestParam("email") String email);

}
