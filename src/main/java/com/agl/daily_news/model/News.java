package com.agl.daily_news.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 2000)
    private String content;
    @Column(nullable = false)
    private Date publicationDate;
    // @Column(name = "image_path")
    // private String imagePath;
    @Column(nullable = false)
    private boolean isActive;
    @Column(name = "created_by")
    private Long createdBy;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    @Transient
    private List<Long> commentIds = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "news_tags",
        joinColumns = @JoinColumn(name = "news_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "view_count")
    private Long viewCount = 0L;
    // Getter and Setter methods

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public List<Long> getCommentIds() {
        return commentIds;
    }
    public void setCommentIds(List<Long> commentIds) {
        this.commentIds = commentIds;
    }
    public Set<Tag> getTags() {
        return tags;
    }
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    public void incrementViewCount() {
        this.viewCount++;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    // public String getImagePath() {
    //     return imagePath;
    // }

    // public void setImagePath(String imagePath) {
    //     this.imagePath = imagePath;
    // }
}
