package entity;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private int id;
    private String login;
    private String password;
    private String salt;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
