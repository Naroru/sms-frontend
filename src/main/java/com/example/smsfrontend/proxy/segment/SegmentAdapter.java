package com.example.smsfrontend.proxy.segment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "segments", url = "http://localhost:8081/api/v1/segments")
public interface SegmentAdapter {

  @GetMapping
  List<Segment> findAll();

  @GetMapping("{id}")
  Segment findById(@PathVariable("id") Long id);

  @GetMapping("/listByName")
  List<Segment> findByName(@RequestParam("name") String name);

  @PostMapping
  Segment save(@RequestBody Segment segment);

  @DeleteMapping("{id}")
  void setDeleted(@PathVariable Long id);
}
