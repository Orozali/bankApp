package com.bank.bankApp.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "Person")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mirlan {
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

}
