package tian.life.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import tian.life.community.model.Quesstion;

@Mapper
public interface QuesstionMapper {

    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Quesstion quesstion);
}
