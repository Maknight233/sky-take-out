package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    ShoppingCart getById(ShoppingCart shoppingCart);

    void addOne(ShoppingCart shoppingCart);

    void add(ShoppingCart shoppingCart);

    @Select("select * from sky_take_out.shopping_cart sc where sc.user_id = #{userId}")
    List<ShoppingCart> getAllById(Long userId);

    void delete(ShoppingCart shoppingCart);

    @Delete("delete from sky_take_out.shopping_cart sc where sc.user_id = #{userId}")
    void deleteAllById(Long userId);
}
