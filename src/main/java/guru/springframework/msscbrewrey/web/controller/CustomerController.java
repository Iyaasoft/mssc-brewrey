package guru.springframework.msscbrewrey.web.controller;

import guru.springframework.msscbrewrey.services.CustomerService;
import guru.springframework.msscbrewrey.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping({"/{customerId}"})
    public ResponseEntity<CustomerDto> doGet(@PathVariable("customerId") String customerId) {
        return new ResponseEntity(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@Valid @RequestBody CustomerDto customer) {
        CustomerDto dto = customerService.createCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer"+dto.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handlePut(@PathVariable("customerId") String customerId, @Valid @RequestBody CustomerDto customer) {
        customerService.updateCustomer(customer);
    }

    @DeleteMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.OK)
    public void doDelete(@PathVariable("customerId") String customerId) {
        customerService.deleteCustomer(customerId);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException e){
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());

        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
