package com.sin.orb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "tasks")
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"taskCard"})
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "completed", columnDefinition = "boolean default false", nullable = false)
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_card_id", updatable = false, nullable = false)
    private TaskCard taskCard;
}
