package hs.sample.depositoffer.integration.c1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class AssessmentRequestSchema {
    private String customerId;
    private String productCode;
}
