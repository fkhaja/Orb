package com.sin.orb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "tasks")
@ToString(exclude = {"taskCard"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "value")
    private String value;

    @NotNull
    @Column(name = "completed", columnDefinition = "boolean default false")
    private Boolean completed;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_card_id", updatable = false)
    private TaskCard taskCard;
}
