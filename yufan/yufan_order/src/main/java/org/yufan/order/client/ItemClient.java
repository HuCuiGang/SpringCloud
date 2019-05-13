package org.yufan.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.yufan.item.api.ItemApi;


@FeignClient("yufan-item-service")
public interface ItemClient extends ItemApi {
}
