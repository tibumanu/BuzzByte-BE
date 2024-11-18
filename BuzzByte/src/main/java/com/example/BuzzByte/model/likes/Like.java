package com.example.BuzzByte.model.likes;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"like\"")   // "like" is a reserved keyword in SQL
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Long userId;
}
