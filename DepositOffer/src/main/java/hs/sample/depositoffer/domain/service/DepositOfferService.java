package hs.sample.depositoffer.domain.service;

import hs.sample.depositoffer.domain.dto.DepositOfferServiceRequestDto;
import hs.sample.depositoffer.domain.dto.DepositOfferServiceResponseDto;
import hs.sample.depositoffer.domain.entity.CustomerEligibilityAssessmentEntity;
import hs.sample.depositoffer.domain.entity.DepositOfferEntity;
import hs.sample.depositoffer.domain.repo.DepositOfferRepository;
import hs.sample.depositoffer.integration.c1.dto.AssessmentResponseSchema;
import hs.sample.depositoffer.integration.c1.service.CustomerEligibilityIntegration;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Slf4j
@Service
public class DepositOfferService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DepositOfferRepository depositOfferRepository;

    @Autowired
    CustomerEligibilityIntegration customerEligibilityIntegration;

    //@Transactional
    public DepositOfferServiceResponseDto call(DepositOfferServiceRequestDto depositOfferServiceRequestDto){

        log.info("deposit offer service - call : {}", depositOfferServiceRequestDto);
        log.info("Thread Information -> {}", Thread.currentThread());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
/*
        HttpServletRequest servletRequest =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
 */
        log.info("request {}", requestAttributes);

        DepositOfferEntity entity = depositOfferRepository.findByCustomerId(depositOfferServiceRequestDto.getCustomerId())
                .orElseGet(() -> {
                            DepositOfferEntity account = DepositOfferEntity.builder()
                                    .customerId(depositOfferServiceRequestDto.getCustomerId())
                                    .accountNumber("")
                                    .productCode(depositOfferServiceRequestDto.getProductCode())
                                    .openDate( new SimpleDateFormat("yyyyMMdd").format(new Date()))
                                    .closeDate("")
                                    .customerEligibilityAssessment(
                                            CustomerEligibilityAssessmentEntity.builder().build()
                                    )
                                    .build();
                            return depositOfferRepository.save(account);
                        }
                );
        log.info("instance {}", entity);

        AssessmentResponseSchema assessmentResponseSchema = customerEligibilityIntegration.call(
                entity.getCustomerId(),
                entity.getProductCode()
        );

        entity.getCustomerEligibilityAssessment().setCustomerGrade(assessmentResponseSchema.getCustomerGrade());
        entity.getCustomerEligibilityAssessment().setStatus(assessmentResponseSchema.isStatus());
        entity.getCustomerEligibilityAssessment().setMessage(assessmentResponseSchema.getMessage());
        depositOfferRepository.save(entity);

        return DepositOfferServiceResponseDto.builder().id(String.valueOf(entity.getId())).build();
    }

    public void complete(String id){
        log.info("[OK] Saga Service complete {}", id);

        DepositOfferEntity entity = depositOfferRepository.findByCustomerId(id).orElseThrow();
        log.info("instance {}", entity);

        entity.setStatus("Active");
        entity.setMessage("");
        //entity.setLraId(param.getLraId());

        depositOfferRepository.save(entity);
        log.info("[OK] Saga Service complete done");
    }

    public void compensate(String id){
        log.info("[FAIL] Saga Service compensate {}", id);
        DepositOfferEntity entity = depositOfferRepository.findByCustomerId(id).orElseThrow();
        log.info("instance {}", entity);

        entity.setStatus("Cancelled");
        entity.setMessage("LRA Fail compensate");
        //entity.setLraId(param.getLraId());

        depositOfferRepository.save(entity);
        log.info("[FAIL] Saga Service compensate done");
    }

}
