package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author chun0
* @since 2025/7/6 14:02
* @version 1.0
*/
@RestController
@RequestMapping("/admin/category")
@Api("分类管理")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类数据，参数：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQueryCategory(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.saveCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 启用禁用分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result<String> startOrStopCategory(@PathVariable String status, Long id) {
        log.info("启用禁用分类：{}", id);
        categoryService.startOrStopCategory(status, id);
        return Result.success();
    }

    /**
     * 分类查询
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分类查询")
    public Result<List<Category>> getCategoryInfoByType(Integer type) {
        log.info("查询分类数据，type={}", type);
        List<Category> list = categoryService.getCategoryInfoByType(type);
        return Result.success(list);
    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping()
    @ApiOperation("修改分类")
    public Result<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类数据，数据：{}", categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 删除分类
     * @param
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<String> deleteCategoryById(Long id) {
        log.info("删除分类，id为：{}", id);
        categoryService.deleteCategoryById(id);
        return Result.success();
    }
}
