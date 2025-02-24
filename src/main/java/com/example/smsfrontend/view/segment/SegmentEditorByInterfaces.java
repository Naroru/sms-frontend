package com.example.smsfrontend.view.segment;

import com.example.smsfrontend.proxy.segment.Segment;
import com.example.smsfrontend.proxy.segment.SegmentAdapter;
import com.example.smsfrontend.common.EventHandler;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;


@SpringComponent
@UIScope
@Setter
public class SegmentEditorByInterfaces extends FormLayout implements KeyNotifier {

  private TextField name;
  private Button saveButton;
  private Button closeButton;

  private EventHandler saveEvent;
  private EventHandler closeEvent;

  //BeanValidationBinder  в отличии от обычного Binder смотрит на проверки заданные в энтити (дто) аннотациями типа @NotNull, @NotEmpty  и т д и выполняет их на фронте
  private final Binder<Segment> binder = new BeanValidationBinder<>(Segment.class);
  private final SegmentAdapter adapter;

  private Segment segment;

  public SegmentEditorByInterfaces(SegmentAdapter adapter) {
    this.adapter = adapter;

    addClassName("segment-editor");
    configureForm();
  }

  private void configureForm(){
    name = new TextField("Наименование");

    configureButtons();

    binder.bindInstanceFields(this);
    add(name, getButtonPanel());

    setVisible(false);
  }

  private void configureButtons(){

    saveButton = new Button("Сохранить");
    closeButton = new Button("Закрыть");

    saveButton.addClickShortcut(Key.ENTER);
    closeButton.addClickShortcut(Key.ESCAPE);

    saveButton.addClickListener( event -> saveSegment());
    closeButton.addClickListener(event -> close());
  }

  public void addSaveEventHandler(EventHandler handler) {
    this.saveEvent = handler;
  }
  public void addCloseEventHandler(EventHandler handler) {
    this.closeEvent = handler;
  }

  private void saveSegment() {
    if(binder.isValid()) {
      adapter.save(segment);
      saveEvent.handle();
    }
  }
  private void close() {
    setVisible(false);
    setSegment(null);
    closeEvent.handle();
  }

  public void editSegment(Segment segment) {
    this.segment = segment;
    setVisible(true);
    binder.setBean(segment);
  }

  private Component getButtonPanel() {
    return new HorizontalLayout(saveButton, closeButton);
  }

}
