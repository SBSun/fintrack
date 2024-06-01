package com.sun.fintrack;

import com.sun.fintrack.common.response.SuccessResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping
  public ResponseEntity<?> home() {
    return ResponseEntity.ok(new SuccessResponse());
  }
}
