package com.heartsuit.db2word.domain;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @Author wzc18
 * @Description MySqlInfo
 */
public class MySqlInfo {
    @ExcelProperty(value = "字段名", index = 0)
    private String field;
    @ExcelProperty(value = "数据类型", index = 1)
    private String type;
    @ExcelProperty(value = "字符集", index = 2)
    private String collation;
    @ExcelProperty(value = "是否允许为空", index = 3)
    private Boolean allowNull;
    @ExcelProperty(value = "字段是否为主键或索引", index = 4)
    private String key;
    @ExcelProperty(value = "默认值", index = 5)
    private String defaultValue;
    @ExcelProperty(value = "额外信息", index = 6)
    private String extra;
    @ExcelProperty(value = "权限", index = 7)
    private String privileges;
    @ExcelProperty(value = "备注", index = 8)
    private String comment;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public Boolean getAllowNull() {
        return allowNull;
    }

    public void setAllowNull(Boolean allowNull) {
        this.allowNull = allowNull;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "MySqlInfo{" +
                "field='" + field + '\'' +
                ", type='" + type + '\'' +
                ", collation='" + collation + '\'' +
                ", allowNull=" + allowNull +
                ", key='" + key + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", extra='" + extra + '\'' +
                ", privileges='" + privileges + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MySqlInfo)) return false;

        MySqlInfo mySqlInfo = (MySqlInfo) o;

        if (field != null ? !field.equals(mySqlInfo.field) : mySqlInfo.field != null) return false;
        if (type != null ? !type.equals(mySqlInfo.type) : mySqlInfo.type != null) return false;
        if (collation != null ? !collation.equals(mySqlInfo.collation) : mySqlInfo.collation != null) return false;
        if (allowNull != null ? !allowNull.equals(mySqlInfo.allowNull) : mySqlInfo.allowNull != null) return false;
        if (key != null ? !key.equals(mySqlInfo.key) : mySqlInfo.key != null) return false;
        if (defaultValue != null ? !defaultValue.equals(mySqlInfo.defaultValue) : mySqlInfo.defaultValue != null)
            return false;
        if (extra != null ? !extra.equals(mySqlInfo.extra) : mySqlInfo.extra != null) return false;
        if (privileges != null ? !privileges.equals(mySqlInfo.privileges) : mySqlInfo.privileges != null) return false;
        return comment != null ? comment.equals(mySqlInfo.comment) : mySqlInfo.comment == null;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (collation != null ? collation.hashCode() : 0);
        result = 31 * result + (allowNull != null ? allowNull.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        result = 31 * result + (privileges != null ? privileges.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
