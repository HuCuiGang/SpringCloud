package org.yufan.item.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api
@RequestMapping("/api/item")
public interface ItemApi {



    /**
     * 减库存   TODO  分布式事物暂未解决
     * @param skuId
     * @param quantity
     * @return
     */
    @PutMapping(value = "/decreaseStock")
    public void decreaseStock(@RequestParam("skuId") Long skuId, @RequestParam("quantity") Integer quantity);

    /**
     * 加库存  TODO  分布式事物暂未解决
     * @param skuId
     * @param quantity
     * @return
     */
    @PutMapping(value = "/increaseStock")
    public void increaseStock(@RequestParam("skuId") Long skuId,@RequestParam("quantity") Integer quantity);


}
