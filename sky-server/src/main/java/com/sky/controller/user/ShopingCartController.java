package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/shoppingCart")
@Api("Shoping Cart")
public class ShopingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<ShoppingCart>> getAll() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAll();
        return Result.success(shoppingCarts);
    }

    @PostMapping("/sub")
    public Result<String> delete(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.delete(shoppingCartDTO);
        return Result.success();
    }

    @DeleteMapping("/clean")
    public Result<String> deleteAll() {
        shoppingCartService.deleteAll();
        return Result.success();
    }
}
