package model;

import lombok.*;

import java.io.InputStream;
import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatHang {
    private Long id;
    private String code;
    private String name;
    private InputStream image;
    private Double retailPrice;
    private Double wholesalePrice;
    private String description;
    private Integer unit;
    private String calculateUnit;
    private Float weight;
    private Category category;
    private Date createdDate;
    private String attribute;
    private int quantity;
}
