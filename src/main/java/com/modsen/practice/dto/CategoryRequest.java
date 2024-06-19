package com.modsen.practice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @Min(1)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
}
