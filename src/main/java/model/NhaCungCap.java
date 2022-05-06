package model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NhaCungCap implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String email;
    private String phone;
    private String address;

    public String get() {
        return code + " - " + name;
    }
}
