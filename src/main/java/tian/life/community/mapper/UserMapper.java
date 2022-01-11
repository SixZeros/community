package tian.life.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tian.life.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified,avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("Select * from user where token = #{token}")
        //如果token一个是类对象则可以直接将值传入数据库操作语句，否则需要加注释@Param
    User findByToken(@Param("token") String token);

    @Select("Select * from user where id = #{id}")
    User findByID(@Param("id") Integer id);
}
