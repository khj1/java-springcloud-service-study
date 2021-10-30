package com.example.catalogservice.utils;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogMapper {

    private final ModelMapper mapper;

    public ResponseCatalog mapEntityToResponse(CatalogEntity entity) {
        return mapper.map(entity, ResponseCatalog.class);
    }
}
