package com.heartsuit.db2word.dao.postgresql;

import com.heartsuit.db2word.domain.PgParams;
import com.heartsuit.db2word.domain.PgTableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 8:30
 */
@Mapper
@Repository
public interface PostgreSQLDataSourceMapper {
    /**
     * 根据数据库名称获取数据库中表的名称和注释
     *
     * @return
     */
    @Select({
            "<script>",
            "SELECT" ,
            "relname AS TABLE_NAME,",
            "( SELECT description FROM pg_description WHERE objoid = oid AND objsubid = 0 ) AS table_comment ",
            "FROM  pg_class ",
            "WHERE relkind = 'r'  AND relname NOT LIKE'pg%'  AND relname NOT LIKE'sql_%' ",
            " <if test=\"pgParams.schema != null and pgParams.schema!='' \"> ",
            "AND relnamespace = (SELECT oid FROM pg_namespace WHERE nspname = #{pgParams.schema,jdbcType=VARCHAR})",
            " </if> ",
            "ORDER BY TABLE_NAME",
            "</script>"

    })
    List<Map<String, String>> getAllTableNames(@Param("pgParams") PgParams pgParams);

    /**
     * 根据表名称获取表的详细信息
     *
     * @param tableName 数据表名
     * @return
     */
    @Select("select\n" +
            "a.attname as field,\n" +
            "format_type(a.atttypid,a.atttypmod) as type,\n" +
            "(case \n" +
            "when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='p')>0 then 'PRI' \n" +
            "when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='u')>0 then 'UNI'\n" +
            "when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='f')>0 then 'FRI'\n" +
            "else '' end) as key,\n" +
            "(case when a.attnotnull=true then 'NO' else 'YES' end) as nullable,\n" +
            "col_description(a.attrelid,a.attnum) as comment\n" +
            "from pg_attribute a\n" +
            "where attstattarget=-1 and attrelid = (select oid from pg_class where relname = '${tableName}');")
    @Results({
            @Result(column = "field", property = "field", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "nullable", property = "nullable", jdbcType = JdbcType.VARCHAR),
            @Result(column = "comment", property = "comment", jdbcType = JdbcType.VARCHAR),
            @Result(column = "key", property = "key", jdbcType = JdbcType.VARCHAR),
    })
    List<PgTableInfo> getTableColumnDetail(@Param("tableName") String tableName);
}
