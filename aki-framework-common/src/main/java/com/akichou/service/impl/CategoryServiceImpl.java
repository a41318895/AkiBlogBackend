package com.akichou.service.impl;

import com.akichou.customEnum.AppHttpCodeEnum;
import com.akichou.domain.ResponseResult;
import com.akichou.domain.dto.CategoryAddDto;
import com.akichou.domain.dto.CategorySelectConditionsDto;
import com.akichou.domain.dto.CategoryEditDto;
import com.akichou.domain.entity.Category;
import com.akichou.domain.vo.CategoryInfoVo;
import com.akichou.domain.vo.CategoryPageSelectVo;
import com.akichou.domain.vo.CategoryVo;
import com.akichou.domain.vo.PageVo;
import com.akichou.exception.SystemException;
import com.akichou.repository.ArticleRepository;
import com.akichou.repository.CategoryRepository;
import com.akichou.service.CategoryService;
import com.akichou.util.BeanCopyUtil;
import com.akichou.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.akichou.constant.SystemConstants.*;

/**
 * Category Service Implementation
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository ;
    private final ArticleRepository articleRepository ;

    @Override
    public ResponseResult<List<CategoryVo>> listAllCategories() {

        // Database Process
        List<Long> distinctCategoryIds =
                articleRepository.findDistinctCategoryIdsByStatusAndDelFlag(ARTICLE_STATUS_PUBLISHED, DEL_FLAG_EXIST) ;
        List<Category> categoryList = categoryRepository.findByIdIn(distinctCategoryIds) ;

        // Vo Encapsulation
        List<CategoryVo> categoryVos = BeanCopyUtil.copyBeanList(categoryList, CategoryVo.class) ;

        return ResponseResult.okResult(categoryVos) ;
    }

    @Override
    public List<Category> listExistCategories() {

        return categoryRepository.findAllByDelFlag(DEL_FLAG_EXIST) ;
    }

    @Override
    public ResponseResult<PageVo<CategoryPageSelectVo>> selectCategories(Integer pageNum, Integer pageSize, CategorySelectConditionsDto categorySelectConditionsDto) {

        // Database Process
        String name = Optional.ofNullable(categorySelectConditionsDto.getName()).filter(StringUtils::hasText).orElse(null) ;
        String status = Optional.ofNullable(categorySelectConditionsDto.getStatus()).filter(StringUtils::hasText).orElse(null);

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<Category> page = categoryRepository.findByNameAndStatusExisting(
                name,
                status,
                pageRequest) ;

        // Vo Encapsulation
        List<CategoryPageSelectVo> categoryPageSelectVos = BeanCopyUtil.copyBeanList(page.getContent(), CategoryPageSelectVo.class);

        return ResponseResult.okResult(new PageVo<>(categoryPageSelectVos, page.getTotalElements())) ;
    }

    @Override
    public ResponseResult<Object> addCategory(CategoryAddDto categoryAddDto) {

        Category addedCategory = mapCategoryAddDtoToCategory(categoryAddDto) ;

        // Database Process
        categoryRepository.save(addedCategory) ;

        return ResponseResult.okResult() ;
    }

    private Category mapCategoryAddDtoToCategory(CategoryAddDto categoryAddDto) {

        return Category.builder()
                .name(categoryAddDto.getName())
                .pid(NO_ROOT_CATEGORY_PID)
                .description(categoryAddDto.getDescription())
                .status(categoryAddDto.getStatus())
                .createBy(SecurityUtil.getUserId())
                .updateBy(SecurityUtil.getUserId())
                .createTime(new Date())
                .updateTime(new Date())
                .delFlag(DEL_FLAG_EXIST)
                .build();
    }

    @Override
    public ResponseResult<CategoryInfoVo> getCategory(Long categoryId) {

        // Database Process
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.CATEGORY_NOT_FOUND));

        // Vo Encapsulation
        CategoryInfoVo categoryInfoVo = BeanCopyUtil.copyBean(category, CategoryInfoVo.class);

        return ResponseResult.okResult(categoryInfoVo) ;
    }

    @Override
    public ResponseResult<Object> editCategory(CategoryEditDto categoryEditDto) {

        // Database Process
        Category category = categoryRepository.findById(categoryEditDto.getId())
                        .orElseThrow(() -> new SystemException(AppHttpCodeEnum.CATEGORY_NOT_FOUND));
        categoryRepository.save(setAttributesForCategoryEditing(category, categoryEditDto)) ;

        return ResponseResult.okResult();
    }

    private Category setAttributesForCategoryEditing(Category category, CategoryEditDto categoryEditDto) {

        category.setName(categoryEditDto.getName());
        category.setStatus(categoryEditDto.getStatus());
        category.setDescription(categoryEditDto.getDescription());
        category.setUpdateBy(SecurityUtil.getUserId());
        category.setUpdateTime(new Date());

        return category ;
    }

    @Override
    public ResponseResult<Object> deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new SystemException(AppHttpCodeEnum.CATEGORY_NOT_FOUND));

        category.setDelFlag(DEL_FLAG_DELETED);
        categoryRepository.save(category) ;

        return ResponseResult.okResult() ;
    }

    public List<Category> getAllById(Set<Long> categoryIds) {

        // JpaRepository's defined findAllById method's return type is : Iterable<> ids, so used ArrayList here.
        return categoryRepository.findAllById(new ArrayList<>(categoryIds)) ;
    }
}
