package com.luan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_SEQ")
    @SequenceGenerator(name = "CARD_SEQ", sequenceName = "CARD_SEQ", allocationSize = 1)
    private Long id;
    private String name;
    private String resourcePath;
}
