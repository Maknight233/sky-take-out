package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetmealService {
    void add(SetmealDTO setmealDTO);

    PageResult pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    SetmealVO getSetmealById(Long id);

    void deleteAll(Long[] ids);

    void startOrStopSetmeal(Integer status, Long id);

    void update(SetmealDTO setmealDTO);
}
