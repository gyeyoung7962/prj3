package com.backend.mapper.promotion;

import com.backend.domain.promotion.Promo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PromoMapper {

    @Insert("""
            INSERT INTO promo
            (id, title, eventType, eventStartDate, eventEndDate, content, isRecommended, isApplyButtonVisible)
            VALUES (#{id}, #{title}, #{eventType}, #{eventStartDate}, #{eventEndDate}, #{content}, #{isRecommended}, #{isApplyButtonVisible})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPromo(Promo promo);

    @Select("""
            SELECT *
            FROM promo
            ORDER BY id DESC
            """)
    List<Promo> selectList();

    @Select("""
            SELECT *
            FROM promo
            WHERE id = #{id}
            """)
    Promo selectById(int id);

    @Delete("""
            DELETE FROM promo
            WHERE id = #{id}
            """)
    int deleteById(int id);

    @Update("""
            UPDATE promo
            SET title = #{title},
                eventType = #{eventType},
                eventStartDate = #{eventStartDate},
                eventEndDate = #{eventEndDate},
                content = #{content},
                isRecommended = #{isRecommended},
                isApplyButtonVisible = #{isApplyButtonVisible}
            WHERE id = #{id}
            """)
    int update(Promo promo);

    @Insert("""
            INSERT INTO promo_file (promo_id, name)
            VALUES (#{promoId}, #{name})
            """)
    int insertFileName(int promoId, String name);

    @Select("""
            SELECT name
            FROM promo_file
            WHERE promo_id=#{promoId}
            """)
    List<String> selectFileNameByPromoId(int promoId);

    @Delete("""
            DELETE FROM promo_file
            WHERE promo_id=#{promoId}
            """)
    int deleteFileByPromoId(int promoId);

    @Delete("""
            DELETE FROM promo_file
            WHERE promo_id=#{promoId}
              AND name=#{fileName}
            """)
    int deleteFileByPromoIdAndName(int promoId, String fileName);

    @Select("""
            SELECT COUNT(*)
            FROM promo
            WHERE eventEndDate >= CURRENT_DATE
            AND eventType = #{type}
            AND (title LIKE CONCAT('%', #{search}, '%') OR content LIKE CONCAT('%', #{search}, '%'))
            """)
    int countAllExcludingEnded(String type, String search);

    @Select("""
            SELECT *
            FROM promo
            WHERE eventEndDate >= CURRENT_DATE
            AND eventType = #{type}
            AND (title LIKE CONCAT('%', #{search}, '%') OR content LIKE CONCAT('%', #{search}, '%'))
            ORDER BY id DESC
            LIMIT #{offset}, #{pageSize}
            """)
    List<Promo> selectAllPagingExcludingEnded(int offset, int pageSize, String type, String search);

    @Select("""
            SELECT COUNT(*)
            FROM promo
            WHERE eventEndDate < CURRENT_DATE
            AND (title LIKE CONCAT('%', #{search}, '%') OR content LIKE CONCAT('%', #{search}, '%'))
            """)
    int countAllEnded(String search);

    @Select("""
            SELECT *
            FROM promo
            WHERE eventEndDate < CURRENT_DATE
            AND (title LIKE CONCAT('%', #{search}, '%') OR content LIKE CONCAT('%', #{search}, '%'))
            ORDER BY id DESC
            LIMIT #{offset}, #{pageSize}
            """)
    List<Promo> selectAllPagingEnded(int offset, int pageSize, String search);

    @Select("""
            SELECT COUNT(*)
            FROM promo
            WHERE title LIKE CONCAT('%', #{search}, '%') OR content LIKE CONCAT('%', #{search}, '%')
            """)
    int countAll(String search);

    @Select("""
            SELECT *
            FROM promo
            WHERE title LIKE CONCAT('%', #{search}, '%') OR content LIKE CONCAT('%', #{search}, '%')
            ORDER BY id DESC
            LIMIT #{offset}, #{pageSize}
            """)
    List<Promo> selectAllPaging(int offset, int pageSize, String search);

    @Select("""
            SELECT *
            FROM promo
            WHERE title LIKE CONCAT('%', #{search}, '%') OR content LIKE CONCAT('%', #{search}, '%')
            ORDER BY id DESC
            """)
    List<Promo> selectAllWithoutPaging(String search);

    @Update("""
            UPDATE promo
            SET isRecommended = #{isRecommended}
            WHERE id = #{id}
            """)
    int updateRecommendation(Integer id, boolean isRecommended);

    @Update("""
            UPDATE promo
            SET isApplyButtonVisible = #{isApplyButtonVisible}
            WHERE id = #{id}
            """)
    int updateApplyButtonVisibility(Integer id, boolean isApplyButtonVisible);

    @Select("""
            SELECT *
            FROM promo
            WHERE isRecommended = true
            """)
    List<Promo> selectRecommendedPromotions();

    @Select("""
            SELECT *
            FROM promo
            WHERE isApplyButtonVisible = true
            """)
    List<Promo> selectPromotionsWithVisibleApplyButton();
}
