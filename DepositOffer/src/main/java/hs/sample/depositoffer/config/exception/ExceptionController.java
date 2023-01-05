package hs.sample.depositoffer.config.exception;

import java.security.InvalidParameterException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    // 400
    @ExceptionHandler({
            InvalidParameterException.class,
            RuntimeException.class
    })
    public ResponseEntity<Object> BadRequestException(final RuntimeException ex) {
        log.warn("error", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({
            BusinessException.class
    })
    public ResponseEntity<ExceptionSchema> BusinessException(
            final BusinessException ex, final HttpServletRequest request) {
        log.info("####");
        log.warn("error", ex);

        // https://stackoverflow.com/questions/59824851/get-controller-name-and-java-service-path-in-controller-advice
        ExceptionSchema exceptionSchema
                = ExceptionSchema.builder()
                .message(ex.getMessage())
                .status(ex.status)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(exceptionSchema);
    }

// 401
//    @ExceptionHandler({ AccessDeniedException.class })
//    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
//        log.warn("error", ex);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
//    }
// 500
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
