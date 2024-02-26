package com.lut.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.pojo.entity.Category;
import com.lut.pojo.vo.CategoryVO;
import com.lut.pojo.vo.ExcelCategoryVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.CategoryService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.WebUtils;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author 浅夜
 * @Description 分类
 * @DateTime 2023/10/25 15:53
 **/
@RestController
@Api(tags = "文章分类")
@RequestMapping
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表 (前台只展示有文章的分类)
     *
     * @return
     */
    @GetMapping("/category/getCategoryList")
    public Result getCategoryList() {
        return categoryService.getCategoryList();
    }

    /**
     * 获取文章列表(后台需要列出所有分类)
     * @return
     */
    @GetMapping("/content/category/listAllCategory")
    public Result getAllCategory() {
        return categoryService.getAllCategory();
    }

    /**
     * 导出所有分类
     * @param response
     */
    @PreAuthorize("@ps.hasPermission('content:category:export')") //判断当前用户权限列表中是否有导出权限
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVO> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVO.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVO.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            Result result = Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("content/category/list")
    public Result<PageVO> pageCategoryList(Integer pageNum, Integer pageSize, String name, String status) {
        return categoryService.pageCategoryList(pageNum, pageSize, name, status);
    }

    @PostMapping("/content/category")
    public Result save(@RequestBody Category category) {
        boolean save = categoryService.save(category);
        return save ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @GetMapping("content/category/{id}")
    public Result getInfo(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return Result.okResult(category);
    }

    @PutMapping("/content/category")
    public Result update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.okResult();
    }

    @DeleteMapping("/content/category/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        return categoryService.delete(ids);
    }


}
