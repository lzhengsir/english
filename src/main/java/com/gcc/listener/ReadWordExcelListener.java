package com.gcc.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.gcc.common.Constants;
import com.gcc.common.ExceptionEnum;
import com.gcc.entity.Word;
import com.gcc.entity.vo.WordAdd;
import com.gcc.exception.ProjectException;
import com.gcc.mapper.WordMapper;
import com.gcc.util.ExcelHeadUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: lzhen
 * @since: 2022/11/20 10:09
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ReadWordExcelListener extends AnalysisEventListener<WordAdd> {

    private final WordMapper wordMapper;

    private final List<Word> dbWordList;

    private List<Word> updateWordList = ListUtils.newArrayListWithExpectedSize(Constants.BATCH_COUNT);

    private List<Word> insertWordList = ListUtils.newArrayListWithExpectedSize(Constants.BATCH_COUNT);

    @Override
    public void invoke(WordAdd data, AnalysisContext context) {
        Word word = new Word();
        BeanUtils.copyProperties(data, word);
        if (!dbWordList.contains(word)) {
            //如果已经存在了，则删除，保证数据唯一
            insertWordList.remove(word);
            insertWordList.add(word);
        } else {
            updateWordList.add(word);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (insertWordList.size() > 0) {
            log.info("新增了" + updateWordList.size() + "个单词");
            wordMapper.insertBatch(insertWordList);
        }
        if (updateWordList.size() > 0) {
            log.info("更新了" + updateWordList.size() + "个单词");
            wordMapper.updateBatch(updateWordList);
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (!ExcelHeadUtils.COLUMN_MAP.equals(headMap)) {
            throw new ProjectException(ExceptionEnum.PLEASE_USE_TEMPLATE);
        }
    }
}
