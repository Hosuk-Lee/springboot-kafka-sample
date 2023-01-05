package hs.sample.depositoffer.api.deposit.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DepositOfferRequestSchema {

    private String customerId;
    private String productCode;
}
