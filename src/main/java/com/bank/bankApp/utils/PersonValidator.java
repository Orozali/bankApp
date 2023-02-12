package com.bank.bankApp.utils;

import com.bank.bankApp.models.Person;
import com.bank.bankApp.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonService personService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personService.getPersonByUsername(person.getUsername()).isPresent()){
            errors.rejectValue("username","","Person with this name is already registered!");
        }else if(person.getAmount()<0){
            errors.rejectValue("amount","","There is not enough money on your card!");
        }else if(personService.getPersonByCard(person.getCard_number()).isPresent()){
            errors.rejectValue("card_number","","This card is already exists!");
        }
    }
}
