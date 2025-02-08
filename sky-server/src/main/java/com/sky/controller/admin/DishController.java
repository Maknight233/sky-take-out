package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "Dish Api")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("add dish")
    @CacheEvict(cacheNames = "dishController", allEntries = true)
    public Result<String> addDish(@RequestBody DishDTO dishDTO) {
        dishService.addDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> pageDish(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.pageDish(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @CacheEvict(cacheNames = "dishController", allEntries = true)
    public Result<String> deleteDishs(Long[] ids) {
        dishService.deleteDishs(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @CacheEvict(cacheNames = "dishController", allEntries = true)
    public Result<String> updateDish(@RequestBody DishDTO dishDTO) {
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    @PostMapping("status/{status}")
    @CacheEvict(cacheNames = "dishController", allEntries = true)
    public Result<String> startStopDish(@PathVariable Integer status, Long id) {
        dishService.startStopDish(status, id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Dish>> getDishByCategoryId(Long categoryId) {
        List<Dish> list = dishService.getDishByCategoryId(categoryId);
        return Result.success(list);
    }
}
