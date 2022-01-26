package guru.springframework.msscbrewrey.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class BreweryExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody ResponseEntity<List>  handleConstraintViolation(
            ConstraintViolationException  ex) {

        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());
        ex.getConstraintViolations().forEach(constraintViolation -> errors.add(constraintViolation.getPropertyPath() +" : "+ constraintViolation.getMessage()));

        System.out.println("Error parsed to response :  "+errors);
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public @ResponseBody ResponseEntity<List>  handleConstraintViolation(
            BindException  ex) {
        return new ResponseEntity(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

}

