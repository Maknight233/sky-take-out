package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void add(List<DishFlavor> flavors);

    @Delete("delete from sky_take_out.dish_flavor df where df.dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    @Select("select * from sky_take_out.dish_flavor df where df.dish_id = #{id}")
    List<DishFlavor> getByDishId(Long id);
}
