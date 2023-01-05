package hs.sample.depositoffer.api.deposit.controller;

import hs.sample.depositoffer.api.deposit.dto.DepositOfferRequestSchema;
import hs.sample.depositoffer.api.deposit.dto.DepositOfferResponseSchema;
import hs.sample.depositoffer.domain.dto.DepositOfferServiceRequestDto;
import hs.sample.depositoffer.domain.dto.DepositOfferServiceResponseDto;
import hs.sample.depositoffer.domain.entity.CustomerEligibilityAssessmentEntity;
import hs.sample.depositoffer.domain.entity.DepositOfferEntity;
import hs.sample.depositoffer.domain.repo.DepositOfferRepository;
import hs.sample.depositoffer.domain.service.DepositOfferService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RestController
//@RequestMapping(value = "/api")
public class DepositOfferController {
    @Autowired
    DepositOfferRepository depositOfferRepository;

    @Autowired
    DepositOfferService depositOfferService;


    @GetMapping("/deposit-offer/all")
    public ResponseEntity<List<DepositOfferEntity>> depositOfferInquiry (){
        return ResponseEntity.ok(depositOfferRepository.findAll());
    }

    @GetMapping("/account/save")
    public ResponseEntity<DepositOfferEntity> depositOfferSave (){

        DepositOfferEntity account = DepositOfferEntity.builder()
                .customerId("customerId")
                .accountNumber("04390204000001")
                .productCode("04")
                .openDate("20221009")
                .closeDate("")
                .customerEligibilityAssessment(
                        CustomerEligibilityAssessmentEntity.builder().build()
                )
                .build();
        return ResponseEntity.ok(depositOfferRepository.save(account));
    }

    /*
    * 1. Controller 수신 > Camel Template Router 전송
    * 2. 새로운 Thread 로 처리 됨 > 이말인 즉슨 RequestContextHolder 사용 못 함
    *
    * 프로그램에서 필요하면 어떻게 하는데??
    * [ANSWER] 1) 사용자 인증정보 - Spring Security에서 사용자마다 다른 사용자 인증 정보 세션을 사용할 때.
    * [ANSWER] 2) 트랜잭션 컨텍스트 - 트랜잭션 매니저가 트랜잭션 컨텍스트를 전파할 때.
    * */
    @PostMapping("/deposit-offer")
    public ResponseEntity<DepositOfferResponseSchema> depositOffer (
            @RequestBody DepositOfferRequestSchema depositOfferRequestSchema
    ){
        DepositOfferServiceRequestDto req = DepositOfferServiceRequestDto.builder()
                .customerId(depositOfferRequestSchema.getCustomerId())
                .productCode(depositOfferRequestSchema.getProductCode())
                .build();

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("request : {}", servletRequest);
        log.info("Thread Information -> {}", Thread.currentThread());

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        // Saga Route 등록
        DepositOfferServiceResponseDto res =
                depositOfferService.call(req);
        ;
        log.info("Done");

//        DepositOfferServiceResponseDto res = depositOfferService.call(DepositOfferServiceRequestDto.builder()
//                .customerId(depositOfferRequestSchema.getCustomerId())
//                .productCode(depositOfferRequestSchema.getProductCode())
//                .build());
        return ResponseEntity.ok(DepositOfferResponseSchema.builder().id(res.getId()).build());
    }

}
