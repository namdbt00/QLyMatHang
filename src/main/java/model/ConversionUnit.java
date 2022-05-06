package model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionUnit {
    String unitName;
    Integer unitValue;
}
