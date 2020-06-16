package com.sin.orb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class TaskDto {
    @Null
    @ApiModelProperty(readOnly = true)
    private Long taskId;

    @Size(min = 1, max = 255)
    @ApiModelProperty(required = true)
    private String value;

    private Boolean completed = false;
}
