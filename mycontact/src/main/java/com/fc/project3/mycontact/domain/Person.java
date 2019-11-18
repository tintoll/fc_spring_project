package com.fc.project3.mycontact.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
// @ToString(exclude = "phoneNumber") // phoneNumber를 제외한고 toString을 만들어준다.
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int age;

    private String hobby;

    private String bloodType;

    private LocalDate birthDay;

    private String address;

    private String job;

    // phoneNumber를 제외한고 toString을 만들어준다.
    @ToString.Exclude
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Block block;


}
