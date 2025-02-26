package com.example.smsfrontend.proxy.segment;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Segment {
  private Long id;
  private String name;
  private boolean isDeleted;
}
