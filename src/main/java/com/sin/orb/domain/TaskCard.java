package com.sin.orb.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@Table(name = "task_cards")
@ToString(exclude = {"user"})
public class TaskCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "creation_date")
    @Basic
    private LocalDate creationDate;

    @OneToMany(mappedBy = "taskCard", fetch = FetchType.EAGER)
    private List<Task> tasks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
