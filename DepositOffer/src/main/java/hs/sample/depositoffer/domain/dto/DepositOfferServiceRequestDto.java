package hs.sample.depositoffer.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Builder
@ToString
public class DepositOfferServiceRequestDto {

    private String customerId;
    private String productCode;
}
