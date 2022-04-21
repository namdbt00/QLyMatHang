package model;

import lombok.*;

import java.io.InputStream;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionUnit {
    String unitName;
    Integer unitValue;
}
