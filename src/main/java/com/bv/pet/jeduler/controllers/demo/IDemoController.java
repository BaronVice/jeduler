package com.bv.pet.jeduler.controllers.demo;

import com.bv.pet.jeduler.dtos.DemoDto;
import org.springframework.http.ResponseEntity;

public interface IDemoController {
    ResponseEntity<DemoDto> showDemo();
}
