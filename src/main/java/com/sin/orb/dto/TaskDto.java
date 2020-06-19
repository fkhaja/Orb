package com.sin.orb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class TaskDto {
    @Null
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long taskId;

    @Size(min = 1, max = 255)
    @ApiModelProperty(required = true)
    private String value;

    private boolean completed = false;
}