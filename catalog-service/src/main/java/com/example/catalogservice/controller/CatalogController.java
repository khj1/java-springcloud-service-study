package com.example.catalogservice.controller;

import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.utils.CatalogMapper;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;
    private final CatalogMapper mapper;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It`s working in User Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> selectUsers() {
        List<ResponseCatalog> response = catalogService.getAllCatalogs().stream()
                .map(mapper::mapEntityToResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
