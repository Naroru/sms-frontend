package com.example.smsfrontend;

import com.example.smsfrontend.proxy.segment.Segment;
import com.example.smsfrontend.proxy.segment.SegmentAdapter;
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

    List<Segment> segmentList = segmentAdapter.findAll();
    System.out.println(segmentList);
  }

}
