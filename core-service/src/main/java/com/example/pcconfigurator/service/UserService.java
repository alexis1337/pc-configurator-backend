package com.example.pcconfigurator.service;

import com.example.pcconfigurator.domain.User;

public interface UserService {

    User getCurrentUser();

    User registerUser(String email, String rawPassword);
}

