package com.example.smsfrontend.view.segment;

import com.example.smsfrontend.proxy.segment.Segment;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;


@SpringComponent
public class SegmentEditor extends FormLayout implements KeyNotifier {

  private final TextField name = new TextField("Наименование");

  private final Button saveButton = new Button("Сохранить");
  private final Button closeButton = new Button("Закрыть");


  public SegmentEditor() {
    addClassName("segment-editor");

    saveButton.addClickShortcut(Key.ENTER);
    closeButton.addClickShortcut(Key.ESCAPE);

    add(name,
        getButtonPanel()
    );

    setVisible(false);
  }

  public void editSegment(Segment segment) {
    setVisible(true);
    name.setValue(segment.getName());
  }

  private Component getButtonPanel() {
    return new HorizontalLayout(saveButton, closeButton);
  }
}
