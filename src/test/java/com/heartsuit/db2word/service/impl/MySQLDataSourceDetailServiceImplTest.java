package com.heartsuit.db2word.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.heartsuit.db2word.domain.MySqlInfo;
import com.heartsuit.db2word.domain.PgTableInfo;
import com.heartsuit.db2word.service.MySQLDataSourceDetailService;
import com.heartsuit.db2word.utils.ExcelUtil;
import com.lowagie.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 18:38
 */
@SpringBootTest
class MySQLDataSourceDetailServiceImplTest {
    @Autowired
    private MySQLDataSourceDetailService mySQLDataSourceDetailService;

    @Test
    void getAllDataSourceName() {
        List<Map<String, String>> tableNames = mySQLDataSourceDetailService.getAllDataSourceName("biyi_security");
        System.out.println(tableNames);
        assertTrue(tableNames.size() > 0);
    }

    @Test
    void getDataSourceDetail() {
        List<MySqlInfo> details = mySQLDataSourceDetailService.getDataSourceDetail("security_task");
        details.forEach(System.out::println);
        assertTrue(details.size() > 0);
    }

    @Test
    void toWord() throws FileNotFoundException, DocumentException {
        mySQLDataSourceDetailService.toWord(mySQLDataSourceDetailService.getAllDataSourceName("zaservice"));
    }

    @Test
    void toExcel(){
        String fileName = System.getProperty("user.dir") + "MysqlExportExcel" + System.currentTimeMillis() + ".xlsx";

        List<Map<String, String>> tableNames = mySQLDataSourceDetailService.getAllDataSourceName("biyi_security");


        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, MySqlInfo.class).build()) {
            for (int i =0;i<tableNames.size();i++) {
                String table_name = tableNames.get(i).get("table_name");
                WriteSheet writeSheet = EasyExcel.writerSheet(i, table_name)
                        //内容策略
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
                        .registerWriteHandler(ExcelUtil.getHorizontalCellStyle())
                        .build();

                List<MySqlInfo> details = mySQLDataSourceDetailService.getDataSourceDetail("security_task");
                excelWriter.write(details, writeSheet);

            }

        }
    }
}