package hs.sample.depositoffer.integration.c1.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AssessmentResponseSchema {
    private boolean status;
    private String message;
    private String customerGrade;
}
