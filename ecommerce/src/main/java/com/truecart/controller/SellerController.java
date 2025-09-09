package com.truecart.controller;


import com.truecart.modal.Seller;
import com.truecart.modal.VerificationCode;
import com.truecart.repository.VerificationCodeRepository;
import com.truecart.request.LoginRequest;
import com.truecart.response.ApiResponse;
import com.truecart.response.AuthResponse;
import com.truecart.service.AuthService;
import com.truecart.service.EmailService;
import com.truecart.service.SellerService;
import com.truecart.utils.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(
            @RequestBody LoginRequest req) throws Exception {
        String otp=req.getOtp();
        String email=req.getEmail();



        req.setEmail("seller_"+email);
        AuthResponse authResponse= authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp)
            throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("wrong otp....");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(),otp);
        return new  ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception, MessagingException {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp= OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = " True Cart Email Verfication Code";
        String text = " Welcome to True Cart , verify your account using this link ";
        String frontend_url = "http://localhost:3000/verify-seller/";
        emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text+frontend_url);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws Exception{
        Seller seller = sellerService.getSellerById(id);
        return new  ResponseEntity<>(seller, HttpStatus.OK);
    }



}
