package com.heartsuit.db2word.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author wzc18
 * @Description excelUtil
 */
public class ExcelUtil {

    public static HorizontalCellStyleStrategy getHorizontalCellStyle(){
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色
//        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)15);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定

        // 背景绿色
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)15);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
    /**
     * @description 单sheet页 web写入
     * @param response 写入流
     * @param data 数据列表
     * @param excelClass 试用类
     * @param fileName 文件名
     */
    public static <T> void export(HttpServletResponse response, List<T> data, Class<?> excelClass, String fileName) throws IOException {
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系

        String exportFileName = "";
        if (StringUtils.isBlank(fileName)) {
            SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
            exportFileName = URLEncoder.encode(formatDay.format(new Date()), "UTF-8").replaceAll("\\+", "%20");
        }else {
            exportFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        }
        response.setHeader("Content-disposition", "attachment;filename='" + exportFileName + ".xlsx");

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        //这里 需要指定写用哪个class去写
        WriteSheet writeSheet = EasyExcel.writerSheet(0, fileName).head(excelClass)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();

        excelWriter.write(data, writeSheet);
        excelWriter.finish();
        response.flushBuffer();
        }

}
