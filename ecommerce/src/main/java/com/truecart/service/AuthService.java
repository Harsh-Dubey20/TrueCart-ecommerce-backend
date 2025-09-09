package com.truecart.service;

import com.truecart.domain.USER_ROLE;
import com.truecart.request.LoginRequest;
import com.truecart.response.AuthResponse;
import com.truecart.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req);
}
