package org.yufan.cart.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.yufan.user.api.UserApi;

@FeignClient("yufan-user-service")
public interface UserClient extends UserApi {
}
