package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/setmeal")
@RestController("adminSetmealController")
@Slf4j
@Api(tags = "Setmeal Api")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("add setmeal")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result<String> addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.add(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "page search")
    public Result<PageResult> pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.pageSetmeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get seatmeal by id")
    public Result<SetmealVO> getSetmealById(@PathVariable String id) {
        SetmealVO setmealVO = setmealService.getSetmealById(Long.valueOf(id));
        return Result.success(setmealVO);
    }

    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result<String> deleteSetmeal(Long[] ids) {
        setmealService.deleteAll(ids);
        return Result.success();
    }

    @PostMapping("status/{status}")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result<String> startOrStopSetmeal(@PathVariable Integer status, Long id) {
        setmealService.startOrStopSetmeal(status, id);
        return Result.success();
    }

    @PutMapping
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result<String> updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }
}
