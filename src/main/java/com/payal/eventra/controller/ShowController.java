package com.payal.eventra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payal.eventra.dto.ShowRequest;
import com.payal.eventra.dto.ShowResponse;
import com.payal.eventra.dto.ShowUpdateRequest;
import com.payal.eventra.service.ShowService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> create(@Valid @RequestBody ShowRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ShowResponse>> getAll(
            @RequestParam(required = false) Long eventId) {
        return ResponseEntity.ok(showService.getAll(eventId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody ShowUpdateRequest request) {
        return ResponseEntity.ok(showService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        showService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
