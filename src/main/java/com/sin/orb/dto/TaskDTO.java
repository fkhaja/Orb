package com.sin.orb.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String value;
    private Boolean completed = false;
    @JsonBackReference
    private TaskCardDTO taskCard;
}
