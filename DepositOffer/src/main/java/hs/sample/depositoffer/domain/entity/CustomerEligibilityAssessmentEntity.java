package hs.sample.depositoffer.domain.entity;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class CustomerEligibilityAssessmentEntity extends WorkStepAbstract {

    @Column
    private String customerGrade;


}
