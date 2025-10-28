package com.java.spring.movie_management.model;

import lombok.Data;

@Data
public class SongWithAuthor {
    private Long id;
    private String title;
    private Integer year;
    private String authorName;
    private String durationFormatted;

    public SongWithAuthor(Long id, String title, Integer year, String authorName, String durationFormatted) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.authorName = authorName;
        this.durationFormatted = durationFormatted;
    }

}
