package com.bv.pet.jeduler.config;

import com.bv.pet.jeduler.dtos.ErrorDto;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
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
}
