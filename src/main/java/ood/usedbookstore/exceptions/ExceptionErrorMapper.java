package ood.usedbookstore.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionErrorMapper {
    private String url;
    private String message;
    private LocalDateTime timestamp;

    public ExceptionErrorMapper(String url, String message, LocalDateTime timestamp) {
        this.url = url;
        this.message = message;
        this.timestamp = timestamp;
    }
}
