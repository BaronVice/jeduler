package com.bv.pet.jeduler.exceptions;

import com.bv.pet.jeduler.datacarriers.dtos.ErrorDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {ApplicationException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(ApplicationException e){
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorDto(e.getLocalizedMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(
                        e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                );
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(DataIntegrityViolationException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto("This name is already reserved"));
    }

    @ExceptionHandler(value = {InterruptedException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(InterruptedException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Lock is interrupted"));
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("Entity not found"));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(IllegalArgumentException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(e.getMessage()));
    }
}
