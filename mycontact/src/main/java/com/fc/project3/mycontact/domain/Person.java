package com.fc.project3.mycontact.domain;

import com.fc.project3.mycontact.domain.dto.Birthday;
import com.fc.project3.mycontact.domain.dto.PersonDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Getter
@Setter
@ToString
// @ToString(exclude = "phoneNumber") // phoneNumber를 제외한고 toString을 만들어준다.
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Where(clause = "deleted = false")
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    private int age;

    private String hobby;

    private String bloodType;

    @Embedded
    @Valid
    private Birthday birthDay;

    private String address;

    private String job;

    // phoneNumber를 제외한고 toString을 만들어준다.
    @ToString.Exclude
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Block block;

    @ColumnDefault("0")
    private boolean deleted;

    public void set(PersonDto personDto) {
        if (!StringUtils.isEmpty(personDto.getHobby())) {
            this.setHobby(personDto.getHobby());
        }

        if (!StringUtils.isEmpty(personDto.getAddress())) {
            this.setAddress(personDto.getAddress());
        }

        if (!StringUtils.isEmpty(personDto.getJob())) {
            this.setJob(personDto.getJob());
        }

        if (!StringUtils.isEmpty(personDto.getPhoneNumber())) {
            this.setPhoneNumber(personDto.getPhoneNumber());
        }

        if (personDto.getBirthday() != null) {
            this.setBirthDay(Birthday.of(personDto.getBirthday()));
        }
    }

}
