package com.ssms.dto.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
    private Long brandId;
    private String brandName;
    private boolean status;
}
