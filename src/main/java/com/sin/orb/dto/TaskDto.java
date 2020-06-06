package com.sin.orb.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class TaskDto {
    @Null
    private Long taskId;

    @NotBlank
    @Size(min = 1, max = 255)
    private String value;

    private Boolean completed = false;
}
