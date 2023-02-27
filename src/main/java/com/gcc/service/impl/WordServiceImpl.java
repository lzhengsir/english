package com.gcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcc.common.ExceptionEnum;
import com.gcc.common.ThreadLocalHolder;
import com.gcc.entity.Word;
import com.gcc.entity.vo.AdminQueryWord;
import com.gcc.entity.vo.ClientWordVo;
import com.gcc.entity.vo.WordAdd;
import com.gcc.entity.vo.WordModify;
import com.gcc.exception.ProjectException;
import com.gcc.listener.ReadWordExcelListener;
import com.gcc.mapper.WordMapper;
import com.gcc.service.WordService;
import com.gcc.util.ProjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 单词 服务实现类
 * </p>
 *
 * @author lzheng
 * @since 2022-11-19
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    @Override
    public void readExcel(MultipartFile file) {
        List<Word> list = this.list();
        ReadWordExcelListener listener = new ReadWordExcelListener(baseMapper, list);
        ProjectUtils.readExcel(file, WordAdd.class, listener, null, 1);
    }

    @Override
    public List<ClientWordVo> getList(String content) {
        return baseMapper.selectViewWordVoList(ThreadLocalHolder.USERID.get(), content);
    }

    @Override
    public String addWord(WordAdd wordAdd) {
        //查询该单词是否存在
        QueryWrapper<Word> wrapper = new QueryWrapper<>();
        wrapper.eq("spell", wordAdd.getSpell());
        Word word = this.getOne(wrapper);
        if (word != null) {
            throw new ProjectException(ExceptionEnum.THIS_WORD_ALREADY_EXIST);
        }
        word = new Word();
        BeanUtils.copyProperties(wordAdd, word);
        this.save(word);
        return word.getId();
    }

    @Override
    public ClientWordVo getWordVo(String wordId) {
        return baseMapper.selectWordVo(ThreadLocalHolder.USERID.get(), wordId);
    }

    @Override
    public void modifyWord(WordModify wordModify) {
        //查询单词是否存在
        Word word = this.getById(wordModify.getId());
        if (word == null) {
            throw new ProjectException(ExceptionEnum.WORD_NOT_EXIST);
        }
        if (!StringUtils.isEmpty(wordModify.getSpell())) {
            word.setSpell(word.getSpell());
        }
        if (!StringUtils.isEmpty(wordModify.getEmphasisMean())) {
            word.setEmphasisMean(wordModify.getEmphasisMean());
        }
        if (!StringUtils.isEmpty(wordModify.getPhonetic())) {
            word.setPhonetic(wordModify.getPhonetic());
        }
        this.updateById(word);
    }

    @Override
    public IPage<Word> getAdminWordPage(Integer pageNum, Integer size, AdminQueryWord adminQueryWord) {
        IPage<Word> page = new Page<>(pageNum, size);
        QueryWrapper<Word> wrapper = new QueryWrapper<>();
        if (adminQueryWord.getContent() != null) {
            wrapper.like("emphasis_mean", adminQueryWord.getContent()).or().like("spell", adminQueryWord.getContent());
        }
        if (adminQueryWord.getCreateTime() != null) {
            wrapper.eq("create_time", adminQueryWord.getCreateTime());
        }
        if (adminQueryWord.getUpdateTime() != null) {
            wrapper.eq("update_time", adminQueryWord.getUpdateTime());
        }
        baseMapper.selectPage(page, wrapper);
        return page;
    }

    @Override
    public Word getWord(String id) {
        return this.getById(id);
    }

    @Override
    public IPage<ClientWordVo> getClientWordPage(Integer pageNum, Integer limit, String content) {
        IPage<ClientWordVo> page = new Page<>(pageNum, limit);
        page = baseMapper.selectClientWordVoPage(page, ThreadLocalHolder.USERID.get(), content);
        return page;
    }

    @Override
    public List<ClientWordVo> getWordsBySign(Integer sign) {
        return baseMapper.selectWordsByTag(sign, ThreadLocalHolder.USERID.get());
    }

    @Override
    public IPage<ClientWordVo> getWordsBySign(Integer pageNum, Integer limit, Integer sign) {
        IPage<ClientWordVo> page = new Page<>(pageNum, limit);
        return baseMapper.selectWordPageByTag(page, sign, ThreadLocalHolder.USERID.get());
    }

    @Override
    public List<ClientWordVo> getWordHasAssociation() {
        return baseMapper.selectWordHasAssociation(ThreadLocalHolder.USERID.get());
    }

    @Override
    public IPage<ClientWordVo> getWordHasAssociationPage(Integer pageNum, Integer limit) {
        IPage<ClientWordVo> page = new Page<>(pageNum, limit);
        return baseMapper.selectWordHasAssociationPage(page, ThreadLocalHolder.USERID.get());
    }
}
