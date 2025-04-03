package com.example.demo.restaurant.dto.response;

import io.micrometer.common.lang.Nullable;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import com.example.demo.restaurant.support.PageUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public record CollectionResponse<T>(Collection<T> content, PagedModel.PageMetadata page) implements Iterable<T> {

    /**
     * Creates an empty {@link CollectionResponse} with the given fallback type.
     */
    public static <T> CollectionResponse<T> empty() {
        return new CollectionResponse<>(Collections.emptyList(), PageUtils.asPageMetadata(Page.empty()));
    }

    /**
     * Creates a new {@link CollectionResponse} from the given content, {@link PagedModel.PageMetadata}.
     *
     * @param content  must not be {@literal null}.
     * @param metadata can be {@literal null}.
     * @return will never be {@literal null}.
     */
    public static <T> CollectionResponse<T> of(Collection<T> content, @Nullable PagedModel.PageMetadata metadata) {
        return new CollectionResponse<>(content, metadata);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

}
