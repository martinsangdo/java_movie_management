package com.java.spring.movie_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller
public class MovieController {
    
    @Operation(
        summary = "Đây là tóm tắt chức năng của API này",
        description = "Các mô tả chi tiết hơn về API này"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Mô tả của API khi thực hiện thành công",
        content = @Content(
            mediaType = "text/plain",
            schema = @Schema(implementation = String.class)
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "Mô tả các lỗi của API"
    )
    @GetMapping("/hello_message")
    public ResponseEntity<String> showHelloMe(
            @Parameter(
                description = "Mô tả về tham số này", example = "Alice"
            )
            @RequestParam(defaultValue = "") String myName) {
        if (myName.isBlank()) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        return new ResponseEntity<>("Hi! My name is: " + myName, HttpStatus.OK);
    }
}
