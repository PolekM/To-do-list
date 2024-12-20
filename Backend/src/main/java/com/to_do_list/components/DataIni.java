package com.to_do_list.components;

import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.repository.AppRoleRepository;
import com.to_do_list.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataIni implements CommandLineRunner {

    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataIni(AppRoleRepository appRoleRepository,AppUserRepository appUserRepository,PasswordEncoder passwordEncoder) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
    appRoleRepository.saveAll(initAppRoleTable());
    appUserRepository.save(initAppUser());
    }

    public List<AppRole> initAppRoleTable() {
        List<AppRole> appRoleList = new ArrayList<>();
        appRoleList.add(new AppRole("ROLE_ADMIN"));
        appRoleList.add(new AppRole("ROLE_USER"));

        return appRoleList;
    }
    public AppUser initAppUser(){
        AppUser appUser = new AppUser();
        appUser.setAppRole(appRoleRepository.findByRoleName("ROLE_USER"));
        appUser.setEmail("user@user.pl");
        appUser.setPassword(passwordEncoder.encode("password"));
        return appUser;
    }
}
