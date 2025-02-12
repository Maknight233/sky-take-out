package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper {
    ShoppingCart getById(ShoppingCart shoppingCart);

    void addOne(ShoppingCart shoppingCart);

    void add(ShoppingCart shoppingCart);
}
