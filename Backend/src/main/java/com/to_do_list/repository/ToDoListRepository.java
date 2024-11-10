package com.to_do_list.repository;

import com.to_do_list.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList,Integer> {

    List<ToDoList> findAllByAppUserId(Integer id);
}
