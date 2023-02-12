package com.bank.bankApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @NotEmpty(message = "This field shouldn't be empty")
    @Column(name = "username")
    private String username;
    @NotEmpty(message = "This field shouldn't be empty")
    @Column(name = "password")
    private String password;
    @Column(name = "card_number")
    @Pattern(regexp = "[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}",message = "Your card number should be in this format: 1111 1111 1111 1111")
    @Size(min = 16, max = 20,message = "Card number must be 16 digits")
    @NotEmpty(message = "This field shouldn't be empty")
    private String card_number;
    @Column(name = "email")
    @NotEmpty(message = "This field shouldn't be empty")
    private String email;
}
