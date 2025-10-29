package com.java.spring.movie_management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.spring.movie_management.model.Author;
import com.java.spring.movie_management.model.Song;
import com.java.spring.movie_management.model.SongWithAuthor;
import com.java.spring.movie_management.service.SongService;


@Controller
public class SongController {
    @Autowired
    SongService songService;
    
    //3.1 1
    @GetMapping({"/api/song/latest", "/api/public/songs"})
    @CrossOrigin(origins = "http://localhost:8088")
    public ResponseEntity<List<Song>> getLatestSongs() {
        return new ResponseEntity<>(songService.getLatestSongs(), HttpStatus.OK);
    }

    //3.1 2
    @GetMapping("/api/song/list_by_author")
    public ResponseEntity<List<Song>> findByAuthorId(@RequestParam(value = "author_id") Long authorId) {
        return new ResponseEntity<>(songService.findByAuthorId(authorId), HttpStatus.OK);
    }

    //3.1 3
    @GetMapping("/api/song/list_by_year")
    public ResponseEntity<Map<Long, Long>> countSongsByAuthor() {
        return new ResponseEntity<Map<Long, Long>>(songService.getSongCountByAuthor(), HttpStatus.OK);
    }
    //3.2
    @PutMapping("/api/song/update_duration")
    public ResponseEntity<Long> updateDurationsToFormatted() {
        return new ResponseEntity<Long>(songService.updateDurationsToFormatted(), HttpStatus.OK);
    }

    //3.3
    @DeleteMapping("/api/song/{id}")
    public ResponseEntity<Map<String, Object>> deleteSong(@PathVariable Long id) {
        boolean deleted = songService.deleteSongById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", deleted);

        if (deleted) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response);
        }
    }
    //authors
    //pagination
    @GetMapping(value = "/authors", produces = MediaType.TEXT_HTML_VALUE)
    public String listAuthors(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 3;
        Page<Author> authorPage = songService.findAllAuthorsPagination(PageRequest.of(page, pageSize, Sort.by("birth_year").descending()));
        int totalPages = authorPage.getTotalPages();
        int currentPage = page;
        // max number of pagination links to display
        int maxPagesToShow = 3;
        int startPage = Math.max(0, currentPage - maxPagesToShow / 2);
        int endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);
        // Adjust if we don’t have enough pages at the end
        if ((endPage - startPage) < (maxPagesToShow - 1)) {
            startPage = Math.max(0, endPage - (maxPagesToShow - 1));
        }
        model.addAttribute("list", authorPage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "author_pagination";
    }
    //4.2
    @GetMapping("/authors/filter")
    public String authorsPageWithFilter(Model model) {
        model.addAttribute("countries", songService.getAllCountries());
        return "authors_by_country";
    }
    //4.2
    @GetMapping("/api/authors/by_country")
    public ResponseEntity<List<Author>> getAuthorsByCountry(@RequestParam String country) {
        List<Author> results = songService.getAuthorsByCountry(country);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    //5.1
    @GetMapping("/songs/with_authors")
    public String songsPage(Model model) {
        List<SongWithAuthor> songs = songService.getAllSongsWithAuthors();
        model.addAttribute("songs", songs);
        return "songs_with_author"; // Thymeleaf template songs.html
    }
    //5.2
    // Show edit form
    @GetMapping("/songs/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Song song = songService.getSongDetail(id);
        List<Author> authors = songService.getAllAuthors();
        model.addAttribute("song", song);
        model.addAttribute("authors", authors);
        return "song_edit";
    }

    // Handle form submit
    @PostMapping("/songs/edit/{id}")
    public String updateSong(@PathVariable Long id, @ModelAttribute Song updatedSong) {
        songService.updateSong(id, updatedSong);
        return "redirect:/songs/with_authors";
    }
    //6.1
    @GetMapping("/songs/filter")
    public String filterSongs(
            @RequestParam(required = false) Long minDuration,
            @RequestParam(required = false) Long maxDuration,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            Model model
    ) {
        List<Song> songs = songService.filterByDurationAndYear(minDuration, maxDuration, minYear, maxYear);
        if (songs == null) {
            songs = List.of(); // never null
        }
        model.addAttribute("songs", songs);
        model.addAttribute("minDuration", minDuration);
        model.addAttribute("maxDuration", maxDuration);
        model.addAttribute("minYear", minYear);
        model.addAttribute("maxYear", maxYear);
        return "songs_filter";
    }
    //6.2
    @GetMapping("/authors/list")
    public String showAuthors(Model model) {
        List<Author> authors = songService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors_list";
    }

    @PostMapping("/authors/update")
    public String updateAuthors(@RequestParam(required = false) List<Long> authorIds) {
        songService.updateAuthorsActiveStatus(authorIds);
        return "redirect:/authors/list";
    }
    //6.3
    @GetMapping("/authors/search")
    public String searchAuthors(@RequestParam(required = false) String keyword, Model model) {
        List<Author> authors = songService.searchAuthors(keyword);
        model.addAttribute("authors", authors);
        model.addAttribute("keyword", keyword);
        return "authors_search";
    }
    //7.1
    @GetMapping("/songs/list")
    public String showSongsInTemplate(Model model) {
        Set<String> firstLetters = songService.getSongChars();
        model.addAttribute("firstLetters", firstLetters);
        List<SongWithAuthor> songs = songService.getAllSongsWithAuthors();
        model.addAttribute("songs", songs);

        List<Song> latestSongs = songService.get4LatestSongs();
        model.addAttribute("latestSongs", latestSongs);
        return "one_music/albums-store";
    }
}
