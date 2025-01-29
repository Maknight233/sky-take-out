package com.sky.service.impl;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void add(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.add(setmeal);
        Long id = setmeal.getId();
        setmealDTO.getSetmealDishes().forEach(setmealDish -> {setmealDish.setSetmealId(id);});
        log.info("The setmeal is: {}", setmealDTO.getSetmealDishes());
        setmealDishMapper.addAll(setmealDTO.getSetmealDishes());
    }
}
