package com.heartsuit.db2word.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.heartsuit.db2word.domain.PgParams;
import com.heartsuit.db2word.domain.PgTableInfo;
import com.heartsuit.db2word.service.PostgreSQLDataSourceDetailService;
import com.lowagie.text.DocumentException;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 16:22
 */
@SpringBootTest
class PostgreSQLDataSourceDetailServiceImplTest {

    @Autowired
    private PostgreSQLDataSourceDetailService postgreSQLDataSourceDetailService;

    @Test
    public void getAllTableNames() {
        PgParams pgParams = new PgParams();
        pgParams.setSchema("public");
        List<Map<String, String>> allTableNames = postgreSQLDataSourceDetailService.getAllTableNames(pgParams);
        System.out.println(allTableNames);
        System.out.println(allTableNames.size());
        assertTrue(allTableNames.size() > 0);
    }

    @Test
    public void getTableColumnDetail() {
        List<PgTableInfo> details = postgreSQLDataSourceDetailService.getTableColumnDetail("security_quality_rule_detail");
        details.forEach(System.out::println);
        assertTrue(details.size() > 0);
    }

    @Test
    public void toWord() throws FileNotFoundException, DocumentException {
        List<Map<String, String>> tableNames = postgreSQLDataSourceDetailService.getAllTableNames(new PgParams());
        postgreSQLDataSourceDetailService.toWord(tableNames);
    }

    @Test
    public void toExcel() throws FileNotFoundException, DocumentException {

        String fileName = System.getProperty("user.dir") + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        PgParams pgParams = new PgParams();
        pgParams.setSchema("myschema");
        List<Map<String, String>> tableNames = postgreSQLDataSourceDetailService.getAllTableNames(pgParams);

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
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
//        postgreSQLDataSourceDetailService.toWord(tableNames);
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, PgTableInfo.class).build()) {
            for (int i =0;i<tableNames.size();i++) {
                String table_name = tableNames.get(i).get("table_name");
                WriteSheet writeSheet = EasyExcel.writerSheet(i, table_name)
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .registerWriteHandler(horizontalCellStyleStrategy)
                        .build();

                List<PgTableInfo> data = postgreSQLDataSourceDetailService.getTableColumnDetail(table_name);
                excelWriter.write(data, writeSheet);

            }

        }
    }
}