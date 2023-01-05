package hs.sample.depositoffer.domain.entity;


import hs.sample.depositoffer.config.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity // 테이블명과 칼럼명을 동일하게 셋팅
public class DepositOfferEntity extends BaseEntity {

    @Column
    String accountNumber;

    @Column
    String customerId;

    @Column
    String productCode;

    @Column
    String openDate;

    @Column
    String closeDate;

    @Embedded
    CustomerEligibilityAssessmentEntity customerEligibilityAssessment;

    @Column
    private String status;
    @Column
    private String message;
    @Column
    private String lraId;


}
