package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@Slf4j
@RequestMapping("/user/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/list")
    @ApiOperation("get setmeal by category Id")
    public Result<List<Setmeal>> getSetmealByCategoryID(Long categoryId) {

        List<Setmeal> list = setmealService.getSetmealByCategoryId(categoryId);

        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("get enable setmeal dish by id")
    public Result<List<DishItemVO>> getSetmealDishById(@PathVariable Long id) {
        List<DishItemVO> dishes = setmealService.getSetmealDishByCategoryId(id);
        return Result.success(dishes);
    }
}
