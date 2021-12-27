package com.realestate.servicedeal.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DealDto {
    private long estateId;
    private long ownerId;
    private long realtorId;
}
