package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@Slf4j
@Api(tags = "shop part")
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("set status")
    public Result<String> setStatus(@PathVariable Integer status){
        redisTemplate.opsForValue().set("shop_status", status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("get status")
    public Result<Integer> getStatus(){
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("shop_status");
        return Result.success(shopStatus);
    }
}
