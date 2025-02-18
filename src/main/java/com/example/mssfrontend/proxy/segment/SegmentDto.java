package com.example.mssfrontend.proxy.segment;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SegmentDto {
  private Long id;
  private String name;
  private boolean isDeleted;
}
