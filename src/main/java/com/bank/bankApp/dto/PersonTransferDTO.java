package com.bank.bankApp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
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
public class PersonTransferDTO {
    @Column(name = "amount")
    @Min(value = 0,message = "There is not enough money on your card")
    private int amount;
    @Column(name = "card_number")
    @NotEmpty(message = "This field shouldn't be empty")
    @Pattern(regexp = "[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}",message = "Your card number should be in this format: 1111 1111 1111 1111")
    @Size(min = 16, max = 20,message = "Card number must be 16 digits")
    private String card_number;
}
