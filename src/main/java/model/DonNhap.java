package model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class DonNhap {
    private Integer id;
    private String code;
    private Integer providerId;
    //    private Long discount;
    private List<Product> products;
    private Boolean hasReceived;
    private Boolean hasConfirmed;
    private Boolean hasPaid;
    private NhaCungCap provider;
    private Date paymentTime;
    private Date importTime;
    private Date confirmTime;
    private Date createdTime;

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Product {
        private Integer id;
        @SerializedName("importPrice")
        private Long price;
        private Integer quantity;
        private String name;
        @SerializedName("matHangCode")
        private String code;
    }

    public Long getTotalPrice() {
        Long price = 0L;
        for (Product pro : products) {
            price += pro.quantity * pro.price;
        }
        return price;
    }

    public Long getTotal() {
        Long total = 0L;
        for (Product pro : products) {
            total += pro.quantity;
        }
        return total;
    }
}

