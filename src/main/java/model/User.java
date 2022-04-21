package model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private int ID;
    private String name,nvCode,phone,email,address,username,password;
}
