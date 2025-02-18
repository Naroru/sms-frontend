package com.example.mssfrontend.proxy.segment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "segments", url = "http://localhost:8081/api/v1/segments")
public interface SegmentAdapter {

  @GetMapping
  List<SegmentDto> findAll();

}
