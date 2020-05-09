package com.sin.orb.dto;

import lombok.Data;

@Data
public class TaskDto {
    private Long taskId;
    private String value;
    private Boolean completed = false;
}
