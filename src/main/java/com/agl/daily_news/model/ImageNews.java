package com.agl.daily_news.model;


import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "images_news")
public class ImageNews {
  @Id
  @UuidGenerator
  private String id;
  private String sharedUrl;
  private String imageName;

  @Lob
  @Column(columnDefinition = "MEDIUMBLOB")
  @JsonIgnore
  private byte[] data;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private News news;

  @UpdateTimestamp
  @JsonIgnore
  private LocalDateTime updatedAt;

  @JsonIgnore
  private Boolean isDeleted = false;

  public ImageNews(String imageName, byte[] data, News news) {
    this.imageName = imageName;
    this.data = data;
    this.news = news;
  }
}

