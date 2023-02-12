package com.bank.bankApp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonNotFoundException extends RuntimeException{
    public String msg;
    public PersonNotFoundException(String msg){
        this.msg = msg;
    }
}
