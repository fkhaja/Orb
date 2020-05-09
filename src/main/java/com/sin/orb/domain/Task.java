package com.sin.orb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "tasks")
@ToString(exclude = {"taskCard"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    @NotBlank
    private String value;

    @Column(name = "completed", nullable = false, columnDefinition = "boolean default false")
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "task_card_id")
    private TaskCard taskCard;
}
