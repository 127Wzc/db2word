package com.heartsuit.db2word.dao.mysql;


import com.heartsuit.db2word.domain.MySqlInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MySQLDataSourceMapper {
    /**
     *  描述：根据表名称获取表的详细信息
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:55
     */
    @Select("SHOW FULL FIELDS FROM ${tableName}")
    @Results({
            @Result(column = "field", property = "field", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "collation", property = "collation", jdbcType = JdbcType.VARCHAR),
            @Result(column = "allowNull", property = "allowNull", jdbcType = JdbcType.BOOLEAN),
            @Result(column = "key", property = "key", jdbcType = JdbcType.VARCHAR),
            @Result(column = "defaultValue", property = "defaultValue", jdbcType = JdbcType.VARCHAR),
            @Result(column = "extra", property = "extra", jdbcType = JdbcType.VARCHAR),
            @Result(column = "privileges", property = "privileges", jdbcType = JdbcType.VARCHAR),
            @Result(column = "comment", property = "comment", jdbcType = JdbcType.VARCHAR)
    })
    List<MySqlInfo> getAllTableNames(@Param("tableName") String tableName);

    /**
     *  描述：根据数据库名称获取数据库中表的名称和注释
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:55
     */
    @Select("select table_name AS table_name, table_comment AS table_comment from information_schema.tables where table_schema = #{dbName}")
    List<Map<String, String>> getTableColumnDetail(@Param("dbName") String dbName);
}
