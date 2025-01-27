package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("UserShopController")
@Slf4j
@Api(tags = "shop part")
@RequestMapping("/user/shop")
public class ShopController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/status")
    @ApiOperation("get status")
    public Result<Integer> getStatus(){
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("shop_status");
        return Result.success(shopStatus);
    }
}
