package com.example.mssfrontend;

import com.example.mssfrontend.proxy.segment.SegmentAdapter;
import com.example.mssfrontend.proxy.segment.SegmentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SmsFrontendApplicationTests {

  @Autowired
  SegmentAdapter segmentAdapter;

  @Test
  void contextLoads() {

    List<SegmentDto> segmentDtoList = segmentAdapter.findAll();
    System.out.println(segmentDtoList);
  }

}
