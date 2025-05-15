package com.guestbooks;

import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/guestbooks")
public class GuestbookController {

  private final GuestbookService service;

  public GuestbookController(GuestbookService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<GuestbookResponseDto> create(
      @RequestParam String name,
      @RequestParam String title,
      @RequestParam String content,
      @RequestParam(required = false) MultipartFile image) throws IOException {

    var saved = service.save(name, title, content, image);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @GetMapping
  public Page<GuestbookResponseDto> list(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {
    return service.findAll(page, size);
  }

  @GetMapping("/{id}")
  public GuestbookResponseDto detail(@PathVariable Long id) {
    return service.findById(id);
  }
}
