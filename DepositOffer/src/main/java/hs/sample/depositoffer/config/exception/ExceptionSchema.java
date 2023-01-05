package hs.sample.depositoffer.config.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionSchema {
    int status;
    String message;
    String path;
}
