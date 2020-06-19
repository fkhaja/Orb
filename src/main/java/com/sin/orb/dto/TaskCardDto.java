package com.sin.orb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskCardDto {
    @Null
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long cardId;

    @NotBlank
    @Size(min = 1, max = 56)
    @ApiModelProperty(required = true)
    private String title;

    @Size(max = 2048)
    private String imageUrl;

    @Size(max = 100)
    private String description;

    @Null
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime term;

    @Null
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private List<TaskDto> tasks;

    private boolean done = false;

    private boolean completedAtTerm = false;
}