package com.personal.blog_app.util;

import com.personal.blog_app.config.ModelMapperConfig;

public class DtoEntityMapper {

    private DtoEntityMapper() {
    }

    public static <D, E> E dtoToEntity(D dto, Class<E> entityClass) {
        return ModelMapperConfig.getMapper().map(dto, entityClass);
    }

    public static <E, D> D entityToDto(E entity, Class<D> dtoClass) {
        return ModelMapperConfig.getMapper().map(entity, dtoClass);
    }
}
