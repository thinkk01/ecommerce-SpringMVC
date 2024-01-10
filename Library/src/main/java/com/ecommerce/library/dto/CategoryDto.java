package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
public class CategoryDto {
        private Long id;
        private String name;
        private Long productSize;

        public CategoryDto(Long id, String name, Long productSize) {
                this.id = id;
                this.name = name;
                this.productSize = productSize;
        }

}
