package com.heartsuit.db2word.domain;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @Author wzc18
 * @Description PgTableInfo
 */
public class PgTableInfo {
    @ExcelProperty(value = "字段名", index = 0)
    private String field;
    @ExcelProperty(value = "产品id", index = 1)
    private String type;
    @ExcelProperty(value = "是否为空", index = 2)
    private String nullable;
    @ExcelProperty(value = "备注", index = 3)
    private String comment;
    @ExcelProperty(value = "键值属性", index = 4)
    private String key;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PgTableInfo{" +
                "field='" + field + '\'' +
                ", nullable='" + nullable + '\'' +
                ", comment='" + comment + '\'' +
                ", type='" + type + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
