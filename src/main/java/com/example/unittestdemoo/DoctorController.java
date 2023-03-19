package com.example.unittestdemoo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unit")
public class DoctorController {

    private final DoctorService doctorService;

    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<DoctorDto> save(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(objectMapper
                .convertValue(doctorService.save(objectMapper.convertValue(doctorDto, Doctor.class)), DoctorDto.class));
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> getAll() {
        return ResponseEntity.ok(doctorService.getAll());
    }

    @GetMapping("/")
    public ResponseEntity<Resource> getImage() throws IOException {
        Path imagePath = Paths.get("C:\\Users\\User\\Pictures\\Screenshots\\Screenshot (117).png");

        byte[] imageBytes = Files.readAllBytes(imagePath);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        byte[] decodedImage = Base64.getDecoder().decode(base64Image);
        ByteArrayResource resource = new ByteArrayResource(decodedImage);

        return ResponseEntity.ok()
                .contentLength(decodedImage.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(objectMapper.convertValue(resource,Resource.class));}
}
