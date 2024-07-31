package com.akichou.service;

import com.akichou.domain.dto.CategoryAddDto;
import com.akichou.domain.dto.CategorySelectConditionsDto;
import com.akichou.domain.dto.CategoryEditDto;
import com.akichou.domain.entity.Category;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.vo.CategoryInfoVo;
import com.akichou.domain.vo.CategoryPageSelectVo;
import com.akichou.domain.vo.CategoryVo;
import com.akichou.domain.vo.PageVo;

import java.util.List;
import java.util.Set;

/**
 * Category Service Interface
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public interface CategoryService {

    ResponseResult<List<CategoryVo>> listAllCategories();

    List<Category> getAllById(Set<Long> categoryIds) ;

    List<Category> listExistCategories() ;

    ResponseResult<PageVo<CategoryPageSelectVo>> selectCategories(Integer pageNum, Integer pageSize, CategorySelectConditionsDto categorySelectConditionsDto);

    ResponseResult<Object> addCategory(CategoryAddDto categoryAddDto);

    ResponseResult<CategoryInfoVo> getCategory(Long categoryId);

    ResponseResult<Object> editCategory(CategoryEditDto categoryEditDto);

    ResponseResult<Object> deleteCategory(Long categoryId);
}
