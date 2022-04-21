package model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonNhapHang implements Serializable {

    private int donId, nhaCungCapID, isPayment, isImportToWarehouse, isConfirm;
    private String tenDon, nhaCungCap, totalPrice;
    private Date paymentTime, importTime, createDate, confirmDate;
}
