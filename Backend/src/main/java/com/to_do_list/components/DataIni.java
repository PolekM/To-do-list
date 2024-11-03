package com.to_do_list.components;

import com.to_do_list.entity.AppRole;
import com.to_do_list.repository.AppRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataIni implements CommandLineRunner {

    private final AppRoleRepository appRoleRepository;

    public DataIni(AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
    appRoleRepository.saveAll(initAppRoleTable());
    }

    public List<AppRole> initAppRoleTable() {
        List<AppRole> appRoleList = new ArrayList<>();
        appRoleList.add(new AppRole("ROLE_ADMIN"));
        appRoleList.add(new AppRole("ROLE_USER"));

        return appRoleList;
    }
}
