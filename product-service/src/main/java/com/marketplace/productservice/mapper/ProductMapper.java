package com.marketplace.productservice.mapper;

import com.marketplace.productservice.dto.ProductDto;
import com.marketplace.productservice.dto.ProductResponseDto;
import com.marketplace.productservice.dto.ProductUpdateDto;
import com.marketplace.productservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductResponseDto toProductResponseDto(Product product);

    Product toProduct(ProductDto productDto);

    Product updateProductFromDto(@MappingTarget Product product, ProductUpdateDto productUpdateDto);
}
