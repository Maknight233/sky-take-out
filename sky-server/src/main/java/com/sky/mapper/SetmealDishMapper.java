package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    void addAll(List<SetmealDish> setmealDishes);

    @Select("select * from sky_take_out.setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getSetmealDishById(Long id);

    @Delete("delete from sky_take_out.setmeal_dish where setmeal_id = #{id}")
    void deleteById(Long id);
}
