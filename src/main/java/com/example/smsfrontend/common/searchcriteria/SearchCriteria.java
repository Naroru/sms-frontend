package com.example.smsfrontend.common.searchcriteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchCriteria {

  //todo добавить возможность объединять таблицы?
  private final List<CriteriaPosition> criteriaPositions = new ArrayList<>();

  public void addCriteria(String fieldName, Object value, SearchCriteriaOperation operation) {
    criteriaPositions.add(new CriteriaPosition(fieldName, value, operation));
  }

  @AllArgsConstructor
  @Getter
  public static class CriteriaPosition {
    private String fieldName;
    private Object value;
    private SearchCriteriaOperation operation;
  }
}
