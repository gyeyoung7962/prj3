package com.backend.mapper.theater;

import com.backend.domain.theater.Theater;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TheaterMapper {

    @Select("""
            SELECT *
            FROM theater
            WHERE city = #{city}
            """)
    List<Theater> selectAllByCity(String city);

    @Select("""
            SELECT *
            FROM theater
            WHERE city = #{city}
            AND location = #{location}
            """)
    Theater selectTheaterByCityAndLocation(String city, String location);

    @Insert("""
            INSERT INTO theater
            (city, location)
            VALUES (#{city}, #{location})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "number")
    int insert(Theater theater);
}
