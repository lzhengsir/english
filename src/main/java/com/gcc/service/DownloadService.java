package com.gcc.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: lzhen
 * @since: 2022/12/1 16:36
 * @description:
 */
public interface DownloadService {
    /**
     * 文件下载
     *
     * @param response
     */
    void downloadExcelTemplate(HttpServletResponse response);

    /**
     * 导出单词
     *
     * @param response
     */
    void importWordExcel(HttpServletResponse response);
}
