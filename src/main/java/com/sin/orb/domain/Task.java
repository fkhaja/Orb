package com.sin.orb.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tasks")
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"taskCard"})
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "completed", columnDefinition = "boolean default false", nullable = false)
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_card_id", updatable = false, nullable = false)
    private TaskCard taskCard;
}
