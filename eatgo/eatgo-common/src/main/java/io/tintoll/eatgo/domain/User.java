package io.tintoll.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Setter
    private String email;

    @NotEmpty
    @Setter
    private String name;

    private String password;

    @NotNull
    @Setter
    private Long level;

    public boolean isAdmin() {
        return level == 100;
    }

    public boolean isActivate() {
        return level > 0;
    }

    public void deactivate() {
        this.level = 0L;
    }

    @JsonIgnore
    public String getAccessToken() {
        if(this.password == null) {
            return "";
        }
        return this.password.substring(0,10);
    }
}
