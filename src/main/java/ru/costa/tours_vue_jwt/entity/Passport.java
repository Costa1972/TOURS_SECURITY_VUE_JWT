package ru.costa.tours_vue_jwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "passports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "series")
    private String series;
    @Column(name = "number")
    private String number;
}
