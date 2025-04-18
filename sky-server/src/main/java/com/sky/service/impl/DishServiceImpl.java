package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Transactional
    @Override
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.addDish(dish);
        Long id = dish.getId();
        dishDTO.getFlavors().forEach(flavor -> flavor.setDishId(id));
        dishFlavorMapper.add(dishDTO.getFlavors());
    }

    @Override
    public PageResult pageDish(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = new PageResult();
        Integer id = dishPageQueryDTO.getCategoryId();
        Dish dish = Dish.builder()
                .name(dishPageQueryDTO.getName())
                .categoryId(((id == null)? null : Long.valueOf(id)))
                .status(dishPageQueryDTO.getStatus())
                .build();
        log.info("categoryId:{}", dish.getCategoryId());
        try (Page<Object> objects = PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
             Page<Dish> page = dishMapper.pageDish(dish)) {
            log.info("start page: {}; page size: {}", objects.getPages(), objects.getPageSize());
            pageResult.setTotal(page.getTotal());
            pageResult.setRecords(page.getResult());
        }
        return pageResult;
    }

    @Override
    @Transactional
    public void deleteDishs(Long[] ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == 1) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            Integer count = setmealMapper.countByDishId(id);
            if (count > 0) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    @Transactional
    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.updateDish(dish);
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        dishFlavorMapper.add(dishDTO.getFlavors());
    }

    @Override
    public DishVO getDishById(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavorList = dishFlavorMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    @Override
    public void startStopDish(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.updateDish(dish);
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<DishVO> dishVOList = new ArrayList<>();

        try (Page<Dish> dishList = dishMapper.pageDish(dish)) {

            for (Dish d : dishList) {
                DishVO dishVO = new DishVO();
                BeanUtils.copyProperties(d, dishVO);

                //根据菜品id查询对应的口味
                List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

                dishVO.setFlavors(flavors);
                dishVOList.add(dishVO);
            }
        }

        return dishVOList;
    }

    @Override
    public List<Dish> getDishByCategoryId(Long categoryId) {
        List<Dish> dishes = dishMapper.getByCategoryId(categoryId);
        return dishes;
    }
}
