package com.example.smsfrontend.view.segment;

import com.example.smsfrontend.proxy.segment.Segment;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;


@SpringComponent
@UIScope
@Setter
public class SegmentEditorByEvents extends FormLayout implements KeyNotifier {

  private TextField name;

  private Button saveButton;
  private Button closeButton;

  private Segment segment;

  //BeanValidationBinder  в отличии от обычного Binder смотрит на проверки заданные в энтити (дто) аннотациями типа @NotNull, @NotEmpty  и т  выполняет их на фронте
  private final Binder<Segment> binder = new BeanValidationBinder<>(Segment.class);

  public SegmentEditorByEvents() {
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
    closeButton.addClickListener(event -> fireEvent(new CloseEvent(this)));
  }

  private void saveSegment() {
   if(binder.isValid()) {
      fireEvent(new SaveEvent(this, binder.getBean()));
    }
  }

  public void editSegment(Segment segment) {
    this.segment = segment;
    setVisible(true);
    binder.setBean(segment);
  }

  private Component getButtonPanel() {
    return new HorizontalLayout(saveButton, closeButton);
  }

  @Getter
  public static abstract class SegmentFormEvent extends ComponentEvent<SegmentEditorByEvents> {

    private final Segment segment;

    protected SegmentFormEvent(SegmentEditorByEvents source, Segment segment) {
      super(source, false);
      this.segment = segment;
    }
  }

  public static class SaveEvent extends SegmentFormEvent {
    SaveEvent(SegmentEditorByEvents source, Segment segment) {
      super(source, segment);
    }
  }

  public static class CloseEvent extends SegmentFormEvent {
    CloseEvent(SegmentEditorByEvents source) {
      super(source, null);
    }
  }

  public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
    return addListener(SaveEvent.class, listener);
  }

  public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
    return addListener(CloseEvent.class, listener);
  }

}
