package com.example.smsfrontend.view.product;

import com.example.smsfrontend.proxy.segment.Segment;
import com.vaadin.flow.component.combobox.ComboBox;

import java.util.List;

public class ProductEditor {

  private final ComboBox<Segment> comboBox = new ComboBox<>();

  public ProductEditor(List<Segment> segments) {

    comboBox.setItems(segments);
    comboBox.setItemLabelGenerator(Segment::getName);

  }
}
