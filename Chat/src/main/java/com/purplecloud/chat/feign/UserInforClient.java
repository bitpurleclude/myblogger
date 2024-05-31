package com.purplecloud.chat.feign;

import com.purplecloud.chat.bean.UserIfor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-inform-server")
public interface UserInforClient {
    @GetMapping("/user/getUserInform/{id}")
    UserIfor getUser(@PathVariable("id") int id);
}
