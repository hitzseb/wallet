package com.hitzseb.billeterapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "operations")
@Data
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Transaction transaction;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private LocalDate date;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
