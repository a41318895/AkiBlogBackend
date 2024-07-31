package com.akichou.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Page Vo
 *
 * <p>
 * This class represents a generic pagination wrapper for a list of items (rows) and
 * the total count of items (total). It is commonly used to encapsulate paginated
 * results returned from queries or services.
 * <p>
 *
 * @author Aki Chou
 * @date 2024/07/17 Wed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "PageVo", description = "Encapsulate page present data")
public class PageVo<T> {

    @Schema(name = "rows", description = "paginated data rows")
    private List<T> rows ;

    @Schema(name = "total", description = "selected data total number")
    private Long total;
}
