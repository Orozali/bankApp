package com.bank.bankApp.exceptions;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AmountLessThanZeroException extends RuntimeException{
    public String msg;
}
