package com.to_do_list.repository;

import com.to_do_list.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole,Integer> {

    AppRole findByRoleName(String role);

}
