package com.guestbooks;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GuestbookService {

  @Value("${upload.path:/tmp/uploads}") // 이미지 저장 경로
  private String uploadPath;

  private final GuestbookRepository repository;

  public GuestbookService(GuestbookRepository repository) {
    this.repository = repository;
  }

  public GuestbookResponseDto save(String name, String title, String content, MultipartFile image) throws IOException {
    String imageUrl = null;
    if (image != null && !image.isEmpty()) {
      String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
      Path imagePath = Paths.get(uploadPath, fileName);
      Files.createDirectories(imagePath.getParent());
      Files.write(imagePath, image.getBytes());
      imageUrl = "/images/" + fileName;
    }

    Guestbook entity = new Guestbook();
    entity.setName(name);
    entity.setTitle(title);
    entity.setContent(content);
    entity.setImageUrl(imageUrl);

    return GuestbookResponseDto.from(repository.save(entity));
  }

  public Page<GuestbookResponseDto> findAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    return repository.findAll(pageable).map(GuestbookResponseDto::from);
  }

  public GuestbookResponseDto findById(Long id) {
    return repository.findById(id)
        .map(GuestbookResponseDto::from)
        .orElseThrow(() -> new NoSuchElementException("Not found"));
  }
}
