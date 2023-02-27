package com.gcc.controller;

import com.gcc.service.DownloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: lzhen
 * @since: 2022/12/1 16:35
 * @description:
 */
@Controller
@RequestMapping("/administer")
@Api(tags = "文件下载")
public class AdminDownloadController {

    @Resource
    DownloadService downloadService;

    @ApiOperation("单词导入模板下载")
    @GetMapping("/exceltemplate")
    public void downloadWordExcelTemplate(HttpServletResponse response) {
        downloadService.downloadExcelTemplate(response);
    }

    @ApiOperation("导出单词库")
    @GetMapping("/wordsexcel")
    public void importWordExcel(HttpServletResponse response) {
        downloadService.importWordExcel(response);
    }
}
