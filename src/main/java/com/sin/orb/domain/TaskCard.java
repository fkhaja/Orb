package com.sin.orb.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Column(name = "name")
    @Length(max = 64)
    private String name;

    @Basic
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "taskCard", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Task> tasks;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Column(name = "description")
    @Length(max = 500)
    private String description;

    @Basic
    @Column(name = "term")
    private LocalDateTime term;

    @NotNull
    @Column(name = "done", columnDefinition = "boolean default false")
    private Boolean done;

    @Column(name = "image_url")
    private String imageUrl;
}
