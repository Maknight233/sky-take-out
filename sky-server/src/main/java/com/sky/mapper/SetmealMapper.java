package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    @Select("select count(Id) from sky_take_out.setmeal_dish sd where sd.dish_id = #{dishId};")
    Integer countByDishId(Long dishId);

    @AutoFill(value = OperationType.INSERT)
    void add(Setmeal setmeal);

    Page<Setmeal> pageSetmeal(Setmeal setmeal);

    @Select("select * from setmeal where id = #{id}")
    Setmeal getSetmealById(Long id);

    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    @Select("select * from sky_take_out.setmeal s where s.category_id = #{categoryId} and s.status = #{status}")
    List<Setmeal> getSetmealByCategoryIdAndStatus(Setmeal setmeal);
}
