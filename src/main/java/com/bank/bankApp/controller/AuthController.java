package com.bank.bankApp.controller;

import com.bank.bankApp.dto.PersonDTO;
import com.bank.bankApp.models.Person;
import com.bank.bankApp.service.PersonService;
import com.bank.bankApp.utils.PersonValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final PersonService personService;
    private final PersonValidator personValidator;
    private final ModelMapper modelMapper;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("person",new PersonDTO());
        return "register";
    }
    @PostMapping("/registration")
    public String registered(@ModelAttribute("person") @Valid PersonDTO personDTO
            ,BindingResult bindingResult){
        Person person = convert(personDTO);
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors()){
            return "register";
        }
        personService.save(person);
        return "redirect:/bank";
    }


    public Person convert(PersonDTO personDTO){
        return modelMapper.map(personDTO,Person.class);
    }
}
