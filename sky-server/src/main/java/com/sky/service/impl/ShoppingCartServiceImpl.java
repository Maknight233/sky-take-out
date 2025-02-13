package com.sky.service.impl;



import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    @Transactional
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .dishId(shoppingCartDTO.getDishId())
                .dishFlavor(shoppingCartDTO.getDishFlavor())
                .setmealId(shoppingCartDTO.getSetmealId())
                .userId(BaseContext.getCurrentId())
                .build();
        if (shoppingCartMapper.getById(shoppingCart) == null) {
            if (shoppingCartDTO.getDishId() != null) {
                Dish dish = dishMapper.getById(shoppingCartDTO.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Setmeal setmeal = setmealMapper.getSetmealById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);
            shoppingCartMapper.add(shoppingCart);
        } else {
            shoppingCartMapper.addOne(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.getAllById(BaseContext.getCurrentId());
        return shoppingCarts;
    }

    @Override
    public void delete(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .dishId(shoppingCartDTO.getDishId())
                .dishFlavor(shoppingCartDTO.getDishFlavor())
                .setmealId(shoppingCartDTO.getSetmealId())
                .userId(BaseContext.getCurrentId())
                .build();
        shoppingCartMapper.delete(shoppingCart);
    }

    @Override
    public void deleteAll() {
        shoppingCartMapper.deleteAllById(BaseContext.getCurrentId());
    }
}
