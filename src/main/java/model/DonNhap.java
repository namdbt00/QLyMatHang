package model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
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
    @Builder
    @ToString
    @AllArgsConstructor
    public static class Product {
        private Integer id;
        private Long price;
        private Integer quantity;
        private String name;
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

