package com.gcc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gcc.common.Result;
import com.gcc.entity.Word;
import com.gcc.entity.vo.AdminQueryWord;
import com.gcc.entity.vo.WordAdd;
import com.gcc.entity.vo.WordModify;
import com.gcc.service.WordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 单词 前端控制器
 * </p>
 *
 * @author lzheng
 * @since 2022-11-19
 */
@RestController
@RequestMapping("/administer")
@Api(tags = "管理员-单词管理")
public class AdminWordController {

    @Resource
    private WordService wordService;

    @PostMapping("/words")
    @ApiOperation("上传excel")
    public Result uploadExcel(@RequestParam("file") MultipartFile file) {
        wordService.readExcel(file);
        return Result.ok();
    }

    @ApiOperation("新增一个单词")
    @PostMapping("/word")
    public Result addWord(@RequestBody @Valid WordAdd wordAdd) {
        String wordId = wordService.addWord(wordAdd);
        return Result.ok().data("item", wordId);
    }

    @ApiOperation("修改一个单词")
    @PutMapping("/word")
    public Result addWord(@RequestBody @Valid WordModify wordModify) {
        wordService.modifyWord(wordModify);
        return Result.ok();
    }

    @ApiOperation("分页查询单词")
    @GetMapping("/words/{page}/{size}")
    public Result getListPage(@ApiParam(value = "当前页数", required = true) @PathVariable Integer page,
                              @ApiParam(value = "每页数据大小", name = "size", required = true) @PathVariable("size") Integer limit,
                              AdminQueryWord adminQueryWord) {
        IPage<Word> iPage = wordService.getAdminWordPage(page, limit, adminQueryWord);
        return Result.ok().data("item", iPage);
    }

    @ApiOperation("查询一个单词（不带个性化标记）")
    @GetMapping("/word/{id}")
    public Result queryOne(@ApiParam(value = "单词id", name = "id", required = true) @PathVariable String id) {
        Word word = wordService.getWord(id);
        return Result.ok().data("item", word);
    }
}
