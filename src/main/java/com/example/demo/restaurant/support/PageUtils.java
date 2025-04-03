package com.example.demo.restaurant.support;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.util.Assert;

/**
 * @author bachhs
 */
public class PageUtils {

    public static PagedModel.PageMetadata asPageMetadata(Page<?> page) {

        Assert.notNull(page, "Page must not be null");

        int number = page.getNumber();

        return new PagedModel.PageMetadata(page.getSize(), number, page.getTotalElements(), page.getTotalPages());
    }

}
