package com.example.smsfrontend.view.segment;

import com.example.smsfrontend.proxy.segment.Segment;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;


@SpringComponent
public class SegmentEditor extends FormLayout implements KeyNotifier {

  private final TextField name = new TextField("Наименование");

  private final Button saveButton = new Button("Сохранить");
  private final Button closeButton = new Button("Закрыть");
  private Segment segment;

  //BeanValidationBinder  в отличии от обычного Binder смотрит на проверки заданные в энтити (дто) аннотациями типа @NotNull, @NotEmpty  и т д
  //и выполняет их на фронте
  private final Binder<Segment> binder = new BeanValidationBinder<>(Segment.class);

  public SegmentEditor() {
    addClassName("segment-editor");

    binder.bindInstanceFields(this);

    saveButton.addClickShortcut(Key.ENTER);
    saveButton.addClickListener(buttonClickEvent -> this.updateSegment());
    closeButton.addClickShortcut(Key.ESCAPE);

    add(name,
        getButtonPanel()
    );
    setVisible(false);
  }

  public void updateSegment() {

      try {
          binder.writeBean(segment);
      } catch (ValidationException e) {
          throw new RuntimeException(e);
      }
  }
  public void editSegment(Segment segment) {
    this.segment = segment;
    setVisible(true);
    //setBean заполняет форму данными из объекта, тогда как readBean обновляет объект данными из формы.
    binder.readBean(segment);
  }

  private Component getButtonPanel() {
    return new HorizontalLayout(saveButton, closeButton);
  }
}
