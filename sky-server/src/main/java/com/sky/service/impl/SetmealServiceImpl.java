package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.DishNotExistExcption;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    @Override
    @Transactional
    public void add(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.add(setmeal);
        Long id = setmeal.getId();
        log.info("setMealId is: {}", id);
        setmealDTO.getSetmealDishes().forEach(setmealDish -> setmealDish.setSetmealId(id));
        log.info("The setmeal is: {}", setmealDTO.getSetmealDishes());
        for (SetmealDish setmealDish : setmealDTO.getSetmealDishes()) {
            Dish dish = dishMapper.getById(setmealDish.getDishId());
            if (dish == null) {
                throw new DishNotExistExcption(MessageConstant.DISH_NOT_EXIST);
            }
        }
        setmealDishMapper.addAll(setmealDTO.getSetmealDishes());
    }

    @Override
    public PageResult pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = new PageResult();
        Integer id = setmealPageQueryDTO.getCategoryId();
        Setmeal setmeal = Setmeal.builder()
                .name(setmealPageQueryDTO.getName())
                .categoryId(((id == null)? null : Long.valueOf(id)))
                .status(setmealPageQueryDTO.getStatus())
                .build();
        try(Page<Object> objects = PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
            Page<Setmeal> page = setmealMapper.pageSetmeal(setmeal)){
            log.info("start page: {}; page size: {}", objects.getPages(), objects.getPageSize());
            pageResult.setTotal(objects.getTotal());
            pageResult.setRecords(page.getResult());
        }
        return pageResult;
    }

    @Override
    public SetmealVO getSetmealById(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        Setmeal setmeal = setmealMapper.getSetmealById(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.getSetmealDishById(id);
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    @Transactional
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getSetmealById(id);
            if (setmeal.getStatus() == 1){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
            setmealMapper.deleteById(id);
            setmealDishMapper.deleteById(id);
        }
    }

    @Override
    public void startOrStopSetmeal(Integer status, Long id) {
        //status changes to 1
        if (status == 1){
            List<SetmealDish> setmealDishes = setmealDishMapper.getSetmealDishById(id);
            for (SetmealDish setmealDish : setmealDishes) {
                Integer dishStatus = dishMapper.getById(setmealDish.getDishId()).getStatus();
                if (dishStatus == 0){
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        setmealDishMapper.deleteById(setmealDTO.getId());
        log.info("The setmealId is: {}", setmealDTO.getId());
        setmealDTO.getSetmealDishes().forEach(setmealDish -> setmealDish.setSetmealId(setmealDTO.getId()));
        setmealDishMapper.addAll(setmealDTO.getSetmealDishes());
    }

    @Override
    public List<Setmeal> getSetmealByCategoryId(Long categoryId) {
        Setmeal setmeal = Setmeal.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();

        return setmealMapper.getSetmealByCategoryIdAndStatus(setmeal);
    }

    @Override
    public List<DishItemVO> getSetmealDishByCategoryId(Long id) {
        List<SetmealDish> dishes = setmealDishMapper.getSetmealDishById(id);
        List<DishItemVO> list = new ArrayList<>();
        for (SetmealDish dish : dishes) {
            Dish d = dishMapper.getById(dish.getDishId());
            if (Objects.equals(d.getStatus(), StatusConstant.ENABLE)) {
                DishItemVO dishItemVO = DishItemVO.builder()
                        .image(d.getImage())
                        .name(d.getName())
                        .copies(dish.getCopies())
                        .description(d.getDescription())
                        .build();
                list.add(dishItemVO);
            }
        }
        return list;
    }
}
