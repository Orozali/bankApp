package com.bank.bankApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "Person")
@Data
@NoArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "This field shouldn't be empty")
    @Column(name = "username")
    private String username;
    @NotEmpty(message = "This field shouldn't be empty")
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    @NotEmpty(message = "This field shouldn't be empty")
    private String email;
    @Column(name = "amount")
    @Min(value = 0,message = "There is not enough money on your card")
    private int amount;
    @Column(name = "card_number")
    @NotEmpty(message = "This field shouldn't be empty")
    @Pattern(regexp = "[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}",message = "Your card number should be in this format: 1111 1111 1111 1111")
    @Size(min = 16, max = 20,message = "Card number must be 16 digits")
    private String card_number;
    @Column(name = "role")
    private String role;
}
