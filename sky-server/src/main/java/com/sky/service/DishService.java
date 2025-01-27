package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

public interface DishService {
    void addDish(DishDTO dishDTO);

    PageResult pageDish(DishPageQueryDTO dishPageQueryDTO);

    void deleteDishs(Long[] ids);

    void updateDish(DishDTO dishDTO);

    DishVO getDishById(Long id);

    void startStopDish(Integer status, Long id);
}
