package com.gcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gcc.common.ExceptionEnum;
import com.gcc.common.ThreadLocalHolder;
import com.gcc.entity.Word;
import com.gcc.entity.WordTag;
import com.gcc.entity.vo.WordTagVo;
import com.gcc.exception.ProjectException;
import com.gcc.mapper.WordMapper;
import com.gcc.mapper.WordTagMapper;
import com.gcc.service.WordTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 单词标记 服务实现类
 * </p>
 *
 * @author lzheng
 * @since 2022-11-21
 */
@Service
public class WordTagServiceImpl extends ServiceImpl<WordTagMapper, WordTag> implements WordTagService {

    @Resource
    private WordMapper wordMapper;

    @Override
    public void handleWordTag(WordTagVo wordTagVo) {
        //先查询单词是否存在
        QueryWrapper<Word> wordQueryWrapper = new QueryWrapper<>();
        wordQueryWrapper.eq("id", wordTagVo.getWordId());
        if (wordMapper.selectOne(wordQueryWrapper) == null) {
            throw new ProjectException(ExceptionEnum.THIS_WORD_NOT_EXIST);
        }
        String userid = ThreadLocalHolder.USERID.get();
        QueryWrapper<WordTag> wrapper = new QueryWrapper<>();
        wrapper.eq("word_id", wordTagVo.getWordId()).eq("app_userid", userid);
        WordTag wordTag = this.getOne(wrapper);
        if (wordTag == null) {
            //新增
            wordTag = new WordTag();
            BeanUtils.copyProperties(wordTagVo, wordTag);
            wordTag.setAppUserid(userid);
            this.save(wordTag);
        } else {
            //更新
            if (!StringUtils.isEmpty(wordTagVo.getSign())) {
                wordTag.setSign(wordTagVo.getSign());
            }
            if (!StringUtils.isEmpty(wordTagVo.getAssociation())) {
                wordTag.setAssociation(wordTagVo.getAssociation());
            }
            this.updateById(wordTag);
        }
    }

    @Override
    public void removeAssociation(String wordId) {
        QueryWrapper<WordTag> wrapper = new QueryWrapper<>();
        wrapper.eq("word_id", wordId).eq("app_userid", ThreadLocalHolder.USERID.get());
        WordTag wordTag = this.getOne(wrapper);
        if (wordTag == null) {
            throw new ProjectException(ExceptionEnum.WORD_TAG_NOT_EXIST);
        }
        UpdateWrapper<WordTag> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("association", null).eq("id", wordTag.getId());
        this.update(updateWrapper);
    }
}
