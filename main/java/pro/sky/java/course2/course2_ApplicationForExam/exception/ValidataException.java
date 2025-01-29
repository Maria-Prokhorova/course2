package pro.sky.java.course2.course2_ApplicationForExam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidataException extends IllegalArgumentException {
    public ValidataException(String message) {
        super(message);
    }
}
