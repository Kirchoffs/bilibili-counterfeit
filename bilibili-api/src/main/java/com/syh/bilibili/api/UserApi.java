package com.syh.bilibili.api;

import com.syh.bilibili.api.support.UserSupport;
import com.syh.bilibili.domain.JsonResponse;
import com.syh.bilibili.domain.User;
import com.syh.bilibili.service.UserService;
import com.syh.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {
    private final UserService userService;

    private final UserSupport userSupport;

    public UserApi(UserService userService, UserSupport userSupport) {
        this.userService = userService;
        this.userSupport = userSupport;
    }

    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception {
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }

    @GetMapping("/users")
    public JsonResponse<User> getUserInfo() {
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        return new JsonResponse<>(user);
    }

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRSAPublicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return JsonResponse.success();
    }
}
