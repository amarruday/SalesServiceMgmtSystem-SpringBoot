package com.ssms.dto.actiontype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionTypeBean {
    private Long actionTypeId;
    private String actionType;
    private String status;
}
