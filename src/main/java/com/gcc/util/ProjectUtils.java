package com.gcc.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcc.common.ExceptionEnum;
import com.gcc.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author: lzhen
 * @since: 2022/11/1 10:03
 * @description:
 */
@Slf4j
public class ProjectUtils {

    /**
     * json序列化
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 获取日期，从1900年1月1日开始计算
     *
     * @param days
     * @return
     */
    public static Date getDate(int days) {
        Calendar calendar = new GregorianCalendar(1899, Calendar.DECEMBER, 30);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //设置传来的时间
        calendar.setTime(date);
        //在这个时间基础上加上n天
        calendar.add(Calendar.DATE, days);
        //把时间转换成相应的时间字符串
        String formatDate = sdf.format(calendar.getTime());
        log.info("日期：" + formatDate);
        return calendar.getTime();
    }

    /**
     * 转换为JSONString
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ProjectException(ExceptionEnum.WRITE_AS_JSON_STRING_FAILED);
        }
    }

    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ProjectException(ExceptionEnum.STRING_CONVERTER_FOR_OBJECT_FAILED);
        }
    }

    /**
     * 读取excel
     *
     * @param file
     * @param tClass
     * @param readListener
     * @param converter
     * @param headRowIndex
     */
    public static void readExcel(MultipartFile file, Class<?> tClass, ReadListener<?> readListener, Converter<?> converter, int headRowIndex) {
        try {
            ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), tClass, readListener);
            if (converter != null) {
                excelReaderBuilder.registerConverter(converter);
            }
            excelReaderBuilder.sheet().headRowNumber(headRowIndex).doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
