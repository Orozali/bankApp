package com.bank.bankApp.controller;

import com.bank.bankApp.dto.PersonDTO;
import com.bank.bankApp.dto.PersonTransferDTO;
import com.bank.bankApp.exceptions.AmountLessThanZeroException;
import com.bank.bankApp.exceptions.ErrorMessage;
import com.bank.bankApp.exceptions.PersonNotFoundException;
import com.bank.bankApp.models.Person;
import com.bank.bankApp.security.PersonDetails;
import com.bank.bankApp.service.PersonService;
import com.bank.bankApp.utils.PersonValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {
    private final PersonValidator personValidator;
    final static double dollar = 86.77;
    private final PersonService personService;
    private final ModelMapper modelMapper;
    @GetMapping()
    public String mainPage(){
        return "home";
    }
    @GetMapping("/admin")
    @ResponseBody
    public String adminPage(){
        return "ADMIN Page";
    }

    @GetMapping("/showAmount")
    @ResponseBody
    public String showAmount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return "Your current amount "+personService.takeAmountById(personDetails.getPerson().getId())+" som";
    }

    @GetMapping("/addAmount")
    public String addAmount(Model model){
        model.addAttribute("person",new Person());
        return "addAmount";
    }
    @PostMapping("/addAmount")
    public String added(@ModelAttribute("person") Person person){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int principalId = personDetails.getPerson().getId();
        personService.addAmount(person,principalId);
        return "redirect:/bank";
    }
    @GetMapping("/myCard_number")
    @ResponseBody
    public String myCardNumber(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return "Your card number is "+personDetails.getPerson().getCard_number();
    }

    @GetMapping("/takeAmount")
    public String takeAmount(Model model){
        model.addAttribute("person",new Person());
        return "takeMoney";
    }
    @PostMapping("/takeAmount")
    public String taken(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
//        personValidator.validate(person,bindingResult);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int principalId = personDetails.getPerson().getId();
        Optional<Person> foundPerson = personService.findById(principalId);
        if(foundPerson.get().getAmount()-person.getAmount()<0){
            throw new AmountLessThanZeroException("There is not enough money on your card!");
        }
        personService.takeAmount(person,principalId);
        return "redirect:/bank";
    }
    @GetMapping("/transfer")
    public String transfer(Model model){
        model.addAttribute("person",new Person());
        return "transfer";
    }
    @PostMapping("/transfer")
    public String transferred(@ModelAttribute("person") @Valid PersonTransferDTO personDTO,
                              BindingResult bindingResult){
        Person person = convert(personDTO);
        if(bindingResult.hasErrors()){
            return "transfer";
        }else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            int principalId = personDetails.getPerson().getId();
            String card_num = person.getCard_number();
            int amount = person.getAmount();
            personService.transfer(principalId, card_num, amount);
            return "redirect:/bank";
        }
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> response(AmountLessThanZeroException e){
        ErrorMessage errorMessage = new ErrorMessage(e.getMsg());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> response(PersonNotFoundException e){
        ErrorMessage errorMessage = new ErrorMessage(e.getMsg());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    public Person convert(PersonTransferDTO personDTO){
        return modelMapper.map(personDTO,Person.class);
    }
}
