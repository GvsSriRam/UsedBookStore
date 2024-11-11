package ood.usedbookstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {
    private OrderOverallStatus orderOverallStatus;
    private double amount;
}
