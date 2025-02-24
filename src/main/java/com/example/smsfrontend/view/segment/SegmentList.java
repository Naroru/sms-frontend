package com.example.smsfrontend.view.segment;

import com.example.smsfrontend.proxy.segment.Segment;
import com.example.smsfrontend.proxy.segment.SegmentAdapter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("/segments")
@PageTitle("Сегменты")
@UIScope
@SpringComponent
public class SegmentList extends VerticalLayout  implements KeyNotifier {

  private final Grid<Segment> grid = new Grid<>(Segment.class);
  private final TextField filterName = new TextField("Наименование");
  private final TextField filterCode = new TextField("Код");
  private final Text header = new Text("Сегменты");
  private final SegmentAdapter adapter;
  private final SegmentEditorByInterfaces editorByInterfaces;
  private final SegmentEditorByEvents editorByEvents;

  public SegmentList(SegmentAdapter adapter, SegmentEditorByEvents editorByEvents, SegmentEditorByInterfaces editorByInterfaces) {
    this.adapter = adapter;
    this.editorByEvents = editorByEvents;
    this.editorByInterfaces = editorByInterfaces;

    setClassName("segment-list-view");

    configureGrid();
    configureEditor();

    updateList();
    add(header, getFilterToolbar(), grid, editorByEvents,editorByInterfaces);
  }

  private void configureGrid() {
    grid.getColumnByKey("id").setHeader("Код");
    grid.getColumnByKey("name").setHeader("Наименование");
    grid.getColumnByKey("id").setWidth("10%").setFlexGrow(0);
    grid.addItemDoubleClickListener(event -> {
      editorByEvents.editSegment(event.getItem());
      editorByInterfaces.editSegment(event.getItem());
    });
  }

  private void configureEditor() {
    editorByEvents.setWidth("25em");
    editorByInterfaces.setWidth("25em");

    editorByEvents.addSaveListener(this::saveEventHandler);
    editorByEvents.addCloseListener(e -> closeEditor());

    editorByInterfaces.addSaveEventHandler(() -> {
        updateList();
        closeEditor();
      });

    editorByInterfaces.addCloseEventHandler(this::closeEditor);

    closeEditor();
  }

  private void saveEventHandler(SegmentEditorByEvents.SaveEvent event) {
    adapter.save(event.getSegment());
    updateList();
    closeEditor();
  }

  private void updateList() {

    if (filterName.getValue().isBlank())
      //todo реализовать вывод через grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(adapter));
      grid.setItems(adapter.findAll());
    else
      grid.setItems(adapter.findByName(filterName.getValue()));
  }

  private Component getFilterToolbar() {

    filterName.setValueChangeMode(ValueChangeMode.LAZY);
    filterName.setClearButtonVisible(true);
    filterName.addValueChangeListener(event -> updateList());

    filterCode.setValueChangeMode(ValueChangeMode.LAZY);
    filterCode.setHelperText("");
    filterCode.setClearButtonVisible(true);
    filterCode.addValueChangeListener(event -> updateList());

    FormLayout formLayout = new FormLayout();
    formLayout.add(filterName, filterCode);

    return formLayout;

  }

  private void closeEditor() {
    editorByInterfaces.setSegment(null);
    editorByInterfaces.setVisible(false);

    editorByEvents.setSegment(null);
    editorByEvents.setVisible(false);

    removeClassName("editing");
  }
}
