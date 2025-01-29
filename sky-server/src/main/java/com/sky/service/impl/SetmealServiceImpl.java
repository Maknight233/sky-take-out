package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        log.info("setMealId is: {}", id);
        setmealDTO.getSetmealDishes().forEach(setmealDish -> setmealDish.setSetmealId(id));
        log.info("The setmeal is: {}", setmealDTO.getSetmealDishes());
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
}
