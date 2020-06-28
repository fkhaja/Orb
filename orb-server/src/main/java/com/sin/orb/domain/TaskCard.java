package com.sin.orb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "task_cards")
@ToString(exclude = {"user"})
@NoArgsConstructor
public class TaskCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToMany(mappedBy = "taskCard", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Task> tasks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "term")
    private LocalDateTime term;

    @Column(name = "completed_at_term", columnDefinition = "boolean default false", nullable = false)
    private boolean completedAtTerm;

    @Column(name = "done", columnDefinition = "boolean default false", nullable = false)
    private boolean done;

    @Column(name = "image_url")
    private String imageUrl;
}
