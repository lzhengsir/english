package com.gcc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gcc.entity.Word;
import com.gcc.entity.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 单词 服务类
 * </p>
 *
 * @author lzheng
 * @since 2022-11-19
 */
public interface WordService extends IService<Word> {

    /**
     * 读取excel
     *
     * @param file
     */
    void readExcel(MultipartFile file);

    /**
     * 获取列表
     *
     * @param content
     * @return
     */
    List<ClientWordVo> getList(String content);

    /**
     * 新增或修改一个单词
     *
     * @param wordAdd
     * @return
     */
    String addWord(WordAdd wordAdd);

    /**
     * 查询一个单词
     *
     * @param wordId
     * @return
     */
    ClientWordVo getWordVo(String wordId);

    /**
     * 修改单词
     *
     * @param wordModify
     * @return
     */
    void modifyWord(WordModify wordModify);

    /**
     * 后台分页查询
     *
     * @param pageNum
     * @param limit
     * @param adminQueryWord
     * @return
     */
    IPage<Word> getAdminWordPage(Integer pageNum, Integer limit, AdminQueryWord adminQueryWord);

    /**
     * 查询一个单词
     *
     * @param id
     * @return
     */
    Word getWord(String id);

    /**
     * 前台查询单词列表
     *
     * @param pageNum
     * @param limit
     * @param content
     * @return
     */
    IPage<ClientWordVo> getClientWordPage(Integer pageNum, Integer limit, String content);

    /**
     * 查询通过单词标记查询单词
     *
     * @param sign
     * @return
     */
    List<ClientWordVo> getWordsBySign(Integer sign);

    /**
     * 分页查询通过单词标记查询单词
     *
     * @param pageNum
     * @param limit
     * @param sign
     * @return
     */
    IPage<ClientWordVo> getWordsBySign(Integer pageNum, Integer limit, Integer sign);

    /**
     * 查询做了联想的单词
     *
     * @return
     */
    List<ClientWordVo> getWordHasAssociation();

    /**
     * 分页查询做了联想的单词
     *
     * @param pageNum
     * @param limit
     * @return
     */
    IPage<ClientWordVo> getWordHasAssociationPage(Integer pageNum, Integer limit);
}
