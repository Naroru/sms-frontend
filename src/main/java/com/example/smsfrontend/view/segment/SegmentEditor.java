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
import lombok.Getter;
import lombok.Setter;


@SpringComponent
@Setter
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
    closeButton.addClickShortcut(Key.ESCAPE);

    saveButton.addClickListener(event -> validateAndSave()); // <1>
    // delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean()))); // <2>
    closeButton.addClickListener(event -> fireEvent(new CloseEvent(this))); // <3>

    add(name,
        getButtonPanel()
    );
    setVisible(false);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(segment);
      fireEvent(new SaveEvent(this, segment)); // <6>
    } catch (ValidationException e) {
      throw new RuntimeException(e);
    }

  /*  if(binder.isValid()) {
      fireEvent(new SaveEvent(this, binder.getBean())); // <6>
    }*/
  }

  public void editSegment(Segment segment) {
    this.segment = segment;
    setVisible(true);
    binder.readBean(segment);
  }

  private Component getButtonPanel() {
    return new HorizontalLayout(saveButton, closeButton);
  }

  // Events
  @Getter
  public static abstract class SegmentFormEvent extends ComponentEvent<SegmentEditor> {
    private final Segment segment;

    protected SegmentFormEvent(SegmentEditor source, Segment segment) {
      super(source, false);
      this.segment = segment;
    }

  }

  public static class SaveEvent extends SegmentFormEvent {
    SaveEvent(SegmentEditor source, Segment segment) {
      super(source, segment);
    }
  }

  public static class DeleteEvent extends SegmentFormEvent {
    DeleteEvent(SegmentEditor source, Segment segment) {
      super(source, segment);
    }

  }

  public static class CloseEvent extends SegmentFormEvent {
    CloseEvent(SegmentEditor source) {
      super(source, null);
    }
  }

  public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
    return addListener(DeleteEvent.class, listener);
  }

  public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
    return addListener(SaveEvent.class, listener);
  }

  public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
    return addListener(CloseEvent.class, listener);
  }

}
