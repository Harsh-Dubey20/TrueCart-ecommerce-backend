package com.truecart.modal;

import com.truecart.domain.PaymentStatus;
import lombok.*;

@Data
public class PaymentDetails {

    private String paymentId;
    private String rayzorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentIdZWSP;
    private PaymentStatus status;


}
