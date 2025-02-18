package com.example.smsfrontend.proxy.segment;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Segment {
  private Long id;
  @NotEmpty
  private String name;
  private boolean isDeleted;
}
