package com.to_do_list.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Integer id;
    String name;
    @OneToMany(mappedBy = "list",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> tasks;

}
