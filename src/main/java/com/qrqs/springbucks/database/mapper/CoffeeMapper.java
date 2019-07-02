package com.qrqs.springbucks.database.mapper;

import com.qrqs.springbucks.database.model.Coffee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CoffeeMapper {
    @Insert("insert into coffee(name, price, create_time, update_time)"
            + "values(#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

    @Select("select * from coffee where id = #{id}")
    @Results(
            @Result(id = true, column = "id")
    )
    Coffee findById(@Param("id") long id);
}
