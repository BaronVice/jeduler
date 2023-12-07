package com.bv.pet.jeduler.controllers.demo;

import com.bv.pet.jeduler.datacarriers.dtos.DemoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/showcase")
public class DemoController implements IDemoController {

    @Override
    @GetMapping
    public ResponseEntity<DemoDto> showDemo() {
        return ResponseEntity.ok(
                new DemoDto()
        );
    }
}
