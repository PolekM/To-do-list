package com.to_do_list.entity;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
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
    @ManyToOne
    @JoinColumn(name = "user_id")
    AppUser appUser;
    @OneToMany(mappedBy = "list",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> tasks;

    public ToDoList(CreateListCommand command,AppUser appUser){
        this.appUser = appUser;
        this.name = command.getCreateListDto().getName();
    }

}
