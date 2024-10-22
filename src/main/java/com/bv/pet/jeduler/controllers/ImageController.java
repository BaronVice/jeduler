package com.bv.pet.jeduler.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("images/image")
public class ImageController {
    @GetMapping
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImageDynamicType(
            @RequestParam(value = "ext", defaultValue = "jpg") String ext,
            @RequestParam(value = "name", defaultValue = "default") String name
    ) {
        MediaType contentType = switch (ext){
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.IMAGE_JPEG;
        };

        InputStream in = getClass().getResourceAsStream("/com/images/" + name + "." + ext);
        if (in == null){
            in = getClass().getResourceAsStream("/com/images/default.jpg");
        }

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(in));
    }
}
