package com.gcc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gcc.entity.WordTag;
import com.gcc.entity.vo.WordTagVo;

/**
 * <p>
 * 单词标记 服务类
 * </p>
 *
 * @author lzheng
 * @since 2022-11-21
 */
public interface WordTagService extends IService<WordTag> {

    /**
     * 新增或修改
     *
     * @param wordTagVo
     */
    void handleWordTag(WordTagVo wordTagVo);

    /**
     * 清空联想
     *
     * @param wordId
     */
    void removeAssociation(String wordId);
}
