package com.to_do_list.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Integer id;
    String description;
    Boolean isCompleted;
    @ManyToOne
    @JoinColumn(name = "list_id")
    ToDoList list;

}
