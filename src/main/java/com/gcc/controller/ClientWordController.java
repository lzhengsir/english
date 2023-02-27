package com.gcc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gcc.common.Result;
import com.gcc.entity.vo.ClientWordVo;
import com.gcc.entity.vo.WordTagVo;
import com.gcc.service.WordService;
import com.gcc.service.WordTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 单词 前端控制器
 * </p>
 *
 * @author lzheng
 * @since 2022-11-19
 */
@RestController
@RequestMapping("/client")
@Api(tags = "客户端-单词管理")
public class ClientWordController {

    @Resource
    private WordService wordService;
    @Resource
    private WordTagService wordTagService;

    @ApiOperation("可带查询条件的不分页获取单词列表")
    @ApiImplicitParam(name = "content", paramType = "query", value = "单词或意思")
    @GetMapping("/words")
    public Result getWordList(String content) {
        List<ClientWordVo> list = wordService.getList(content);
        return Result.ok().data("items", list);
    }

    @ApiOperation("查询一个单词（带个性化标记）")
    @GetMapping("/word/{wordId}")
    public Result getWord(@PathVariable String wordId) {
        ClientWordVo clientWordVo = wordService.getWordVo(wordId);
        return Result.ok().data("item", clientWordVo);
    }

    @ApiOperation("可带查询条件的分页获取单词列表")
    @GetMapping("/words/{page}/{size}")
    @ApiImplicitParam(name = "content", paramType = "query", value = "单词或意思")
    public Result getWoldListPage(@ApiParam(value = "当前页数", name = "page", required = true) @PathVariable("page") Integer pageNum,
                                  @ApiParam(value = "每页数据大小", name = "size", required = true) @PathVariable("size") Integer limit,
                                  String content) {
        IPage<ClientWordVo> iPage = wordService.getClientWordPage(pageNum, limit, content);
        return Result.ok().data("item", iPage);
    }

    @ApiOperation("新增|更新单词标记")
    @PostMapping("/wordtag")
    public Result handlerWordTag(@RequestBody @Valid WordTagVo wordTagVo) {
        wordTagService.handleWordTag(wordTagVo);
        return Result.ok();
    }

    @ApiOperation("查询是否掌握的单词（不分页）")
    @GetMapping("/sign")
    public Result getWordsBySign(@ApiParam(value = "掌握程度（1-未掌握- 2-已掌握）", required = true) @RequestParam Integer sign) {
        List<ClientWordVo> wordVoList = wordService.getWordsBySign(sign);
        return Result.ok().data("item", wordVoList);
    }

    @ApiOperation("查询是否掌握的单词（分页）")
    @GetMapping("/sign/{page}/{size}")
    public Result getWordsBySign(@ApiParam(value = "当前页数", required = true) @PathVariable("page") Integer pageNum,
                                 @ApiParam(value = "每页数据大小", required = true) @PathVariable("size") Integer limit,
                                 @ApiParam(value = "掌握程度（1-未掌握- 2-已掌握）", required = true) @RequestParam Integer sign) {
        IPage<ClientWordVo> iPage = wordService.getWordsBySign(pageNum, limit, sign);
        return Result.ok().data("item", iPage);
    }

    @ApiOperation("清空联想")
    @DeleteMapping("/wordtag/{wordId}")
    public Result removeAssociation(@ApiParam(value = "单词id", required = true) @PathVariable String wordId) {
        wordTagService.removeAssociation(wordId);
        return Result.ok();
    }

    @GetMapping("/associationwords")
    @ApiOperation("查询做了单词联想的单词(不分页)")
    public Result getWordHasAssociation() {
        List<ClientWordVo> wordVoList = wordService.getWordHasAssociation();
        return Result.ok().data("item", wordVoList);
    }

    @GetMapping("/associationwords/{page}/{size}")
    @ApiOperation("查询做了单词联想的单词(分页)")
    public Result getWordHasAssociation(@ApiParam(value = "当前页数", required = true) @PathVariable("page") Integer pageNum,
                                        @ApiParam(value = "每页数据大小", required = true) @PathVariable("size") Integer limit) {
        IPage<ClientWordVo> page = wordService.getWordHasAssociationPage(pageNum, limit);
        return Result.ok().data("item", page);
    }
}
