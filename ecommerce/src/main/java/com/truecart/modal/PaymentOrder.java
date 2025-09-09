package com.truecart.modal;

import com.truecart.domain.PaymentMethod;
import com.truecart.domain.PaymentOrderStatus;
import com.truecart.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status= PaymentOrderStatus.PENDING;

    private PaymentMethod paymentMethod;

    private String paymentLinkId;


    @ManyToOne
    private User user;

    @OneToMany
    private Set<Order> orders = new HashSet<>();
}
