package hs.sample.depositoffer.integration.c1.service;


import hs.sample.depositoffer.integration.c1.dto.AssessmentRequestSchema;
import hs.sample.depositoffer.integration.c1.dto.AssessmentResponseSchema;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CustomerEligibilityIntegration {

    @Autowired
    RestTemplate restTemplate;

    public AssessmentResponseSchema call(
            String customerId,
            String productCode
    ){
        // String forObject = restTemplate.getForObject("http://localhost:9102/api/customer-eligibility-assessment", String.class);
        //        URI uri = UriComponentsBuilder
//                .fromUriString("http://localhost:9090")
//                .path("/api/server/user/{userId}/name/{userName}")
//                .encode()
//                .build()
//                .expand(100, "steve") // {userId}, {userName}에 들어갈 값을 순차적으로 입력
//                .toUri();
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9102")
                .path("/api/customer-eligibility-assessment")
                .encode()
                .build()
                .toUri();
        System.out.println("URI : "+uri);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        // headers.add("Authorization", "웹 헤더에 있던 값");

        System.out.println("Headers : " + headers);

        // object를 넣어주면 object mapper가 json으로 바꿔주고
        // rest template에서 http body에 json을 넣어줄 것이다.
        AssessmentRequestSchema req = AssessmentRequestSchema.builder()
                .customerId(customerId)
                .productCode(productCode)
                .build();

        // 기본
//        ResponseEntity<AssessmentResponseSchema> response
//                = restTemplate.postForEntity(uri, req, AssessmentResponseSchema.class);

        // 헤더변경
        HttpEntity httpEntity = new HttpEntity(req,headers);
        ResponseEntity<AssessmentResponseSchema> response
                = restTemplate.postForEntity(uri, httpEntity, AssessmentResponseSchema.class);

        // Response 출력
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());
        return response.getBody();
    }

}
