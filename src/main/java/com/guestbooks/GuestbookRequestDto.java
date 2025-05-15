package com.guestbooks;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GuestbookRequestDto {

  private String name;
  private String title;
  private String content;
  private MultipartFile image;

  // getters, setters
}
