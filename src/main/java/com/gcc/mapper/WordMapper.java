package com.gcc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gcc.entity.Word;
import com.gcc.entity.vo.ClientWordVo;
import com.gcc.entity.vo.WordAdd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 单词 Mapper 接口
 * </p>
 *
 * @author lzheng
 * @since 2022-11-19
 */
public interface WordMapper extends BaseMapper<Word> {

    /**
     * 批量新增
     *
     * @param list
     */
    void insertBatch(List<Word> list);

    /**
     * 批量新增
     *
     * @param list
     */
    void updateBatch(List<Word> list);

    /**
     * 查询单词
     *
     * @param userid
     * @param content
     * @return
     */
    List<ClientWordVo> selectViewWordVoList(@Param("userid") String userid, @Param("content") String content);

    /**
     * 查询单词
     *
     * @param userid
     * @param wordId
     * @return
     */
    ClientWordVo selectWordVo(@Param("userid") String userid, @Param("wordId") String wordId);

    /**
     * 分页查询
     *
     * @param page
     * @param userid
     * @param content
     * @return
     */
    IPage<ClientWordVo> selectClientWordVoPage(IPage<ClientWordVo> page,
                                               @Param("userid") String userid,
                                               @Param("content") String content);

    /**
     * 通过单词标记查询单词
     *
     * @param sign
     * @param userid
     * @return
     */
    List<ClientWordVo> selectWordsByTag(@Param("sign") Integer sign, @Param("userid") String userid);

    /**
     * 分页-通过单词标记查询单词
     *
     * @param page
     * @param sign
     * @param userid
     * @return
     */
    IPage<ClientWordVo> selectWordPageByTag(IPage<ClientWordVo> page, @Param("sign") Integer sign, @Param("userid") String userid);

    /**
     * 查询做了联想标记的单词
     *
     * @param userid
     * @return
     */
    List<ClientWordVo> selectWordHasAssociation(String userid);

    /**
     * 分页查询做了联想标记的单词
     *
     * @param page
     * @param userid
     * @return
     */
    IPage<ClientWordVo> selectWordHasAssociationPage(IPage<ClientWordVo> page, @Param("userid") String userid);

    /**
     * 查询
     *
     * @return
     */
    List<WordAdd> selectWordAddList();
}
