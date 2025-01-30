package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId category id
     * @return number of dish that id = categoryId
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    void addDish(Dish dish);

    Page<Dish> pageDish(Dish dish);

    @Select("select * from sky_take_out.dish d where d.id = #{id}")
    Dish getById(Long id);

    @Delete("delete from sky_take_out.dish d where d.id = #{id}")
    void deleteById(Long id);

    @AutoFill(OperationType.UPDATE)
    void updateDish(Dish dish);

    @Select("select * from sky_take_out.dish d where d.category_id = #{categoryId}")
    List<Dish> getByCategoryId(Long categoryId);
}
