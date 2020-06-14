package com.sin.orb.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "task_cards")
@ToString(exclude = {"user"})
@NoArgsConstructor
@AllArgsConstructor
public class TaskCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

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
    private Boolean completedAtTerm;

    @Column(name = "done", columnDefinition = "boolean default false", nullable = false)
    private Boolean done;

    @Column(name = "image_url")
    private String imageUrl;
}
