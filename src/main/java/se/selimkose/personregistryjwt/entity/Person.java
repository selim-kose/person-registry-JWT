package se.selimkose.personregistryjwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @NotBlank(message = "First name is mandatory")
    @NonNull
    String firstName;
    @NotBlank(message = "Last name is mandatory")
    @NonNull
    String lastName;
    @Min(1)
    @Max(120)
    Integer age;
}
