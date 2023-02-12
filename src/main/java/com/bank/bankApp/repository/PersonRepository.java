package com.bank.bankApp.repository;

import com.bank.bankApp.models.Person;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByUsername(String username);
    @Query("SELECT t FROM Person t WHERE t.card_number=?1")
    Optional<Person> findPersonByCard_number(String card_number);
}
