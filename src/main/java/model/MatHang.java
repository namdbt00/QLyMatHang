package model;

import lombok.*;

import java.io.InputStream;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.Locale;

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

    private final static Locale localeVN = new Locale("vi", "VN");
    private final static NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    private String formatNumber(Double number) {
        return number == null ? currencyVN.format(0) : currencyVN.format(number);
    }

    public String getWholesalePriceF() {
        return formatNumber(wholesalePrice);
    }

    public String getRetailPriceF() {
        return formatNumber(retailPrice);
    }
}
