package com.guestbooks;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GuestbookResponseDto {

  private Long id;
  private String name;
  private String title;
  private String content;
  private String imageUrl;
  private LocalDateTime createdAt;

  public static GuestbookResponseDto from(Guestbook g) {
    return new GuestbookResponseDto(
        g.getId(), g.getName(), g.getTitle(),
        g.getContent(), g.getImageUrl(), g.getCreatedAt()
    );
  }

  // 생성자, getters 생략
}

