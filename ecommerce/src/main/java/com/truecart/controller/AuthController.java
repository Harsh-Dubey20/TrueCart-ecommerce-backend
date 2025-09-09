package com.truecart.controller;

import com.truecart.domain.USER_ROLE;
import com.truecart.modal.User;
import com.truecart.modal.VerificationCode;
import com.truecart.repository.UserRepository;
import com.truecart.request.LoginOtpRequest;
import com.truecart.request.LoginRequest;
import com.truecart.response.ApiResponse;
import com.truecart.response.AuthResponse;
import com.truecart.response.SignupRequest;
import com.truecart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {

       String jwt = authService.createUser(req);

       AuthResponse res = new AuthResponse();
       res.setJwt(jwt);
       res.setMessage("register success");
       res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);


    }



    @PostMapping("/sent/signup-login-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

        authService.sentLoginOtp(req.getEmail(),req.getRole());

        ApiResponse res = new ApiResponse();
        res.setMessage("otp sent successfully");
        return ResponseEntity.ok(res);


    }



    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {

        AuthResponse authResponse= authService.signing(req);

        return ResponseEntity.ok(authResponse);


    }
}
