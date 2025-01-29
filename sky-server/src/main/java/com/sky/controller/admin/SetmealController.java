package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/setmeal")
@RestController
@Slf4j
@Api(tags = "Setmeal Api")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("add setmeal")
    public Result<String> addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        setmealService.add(setmealDTO);
        return Result.success();
    }
}
