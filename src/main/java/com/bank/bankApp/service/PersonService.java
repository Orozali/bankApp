package com.bank.bankApp.service;

import com.bank.bankApp.exceptions.AmountLessThanZeroException;
import com.bank.bankApp.exceptions.PersonNotFoundException;
import com.bank.bankApp.models.Person;
import com.bank.bankApp.repository.PersonRepository;
import com.bank.bankApp.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService implements UserDetailsService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    public int getAmount(int id) {
        Optional<Person> person = personRepository.findById(id);
        return person.get().getAmount();
    }
    public Optional<Person> getPersonByUsername(String username){
        return personRepository.findByUsername(username);
    }
    public Optional<Person> getPersonByCard(String cardNum){
        return personRepository.findPersonByCard_number(cardNum);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("User Not Found!");
        }
        return new PersonDetails(person.get());
    }
    @Transactional
    public void save(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setAmount(0);
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }

    public int takeAmountById(int id) {
        Optional<Person> person = personRepository.findById(id);
        return person.map(Person::getAmount).orElse(0);
    }
    @Transactional
    public void addAmount(Person person,int id) {
        Person p = personRepository.findById(id).get();
        p.setAmount(person.getAmount()+p.getAmount());
        personRepository.save(p);
    }
    @Transactional
    public void takeAmount(Person person, int principalId) {
        Person p = personRepository.findById(principalId).get();
        int amount = p.getAmount()-person.getAmount();
        p.setAmount(amount);
        personRepository.save(p);
    }

    public Optional<Person> findById(int principalId) {
        return personRepository.findById(principalId);
    }
    @Transactional
    public void transfer(int principalId, String cardNum, int amount) {
        Person principalPerson = personRepository.findById(principalId).get();
        Optional<Person> transferPerson = personRepository.findPersonByCard_number(cardNum);
        if(transferPerson.isPresent()){
            transferPerson.get().setAmount(transferPerson.get().getAmount()+amount);
            principalPerson.setAmount(principalPerson.getAmount()-amount);
        }else{
            throw new PersonNotFoundException("Person with this card not found!");
        }
    }
}
