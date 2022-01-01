package com.realestate.serviceestate.api.dto;

import com.realestate.serviceestate.api.enums.EstateDealEnum;
import com.realestate.serviceestate.repo.model.Description;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class EstateDto {
    private EstateDealEnum dealType;
    private Description description;
    private String owner;
}
