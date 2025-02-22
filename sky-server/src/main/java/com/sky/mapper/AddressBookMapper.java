package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    void add(AddressBook addressBook);

    @Select("select * from sky_take_out.address_book ab where ab.user_id = #{userId}")
    List<AddressBook> getByUserId(Long userId);

    @Select("select * from sky_take_out.address_book ab where ab.user_id = #{userId} and ab.is_default = 1")
    AddressBook getDefaultByUserId(Long userId);

    void update(AddressBook addressBook);

    @Update("update sky_take_out.address_book set address_book.is_default = #{isDefault} where address_book.id = #{id}")
    void setDefaultById(Long id, Integer isDefault);

    @Select("select * from sky_take_out.address_book ab where ab.id = #{id}")
    AddressBook getById(Long id);

    @Delete("delete from sky_take_out.address_book ab where ab.id = #{id}")
    void deleteById(Long id);
}
