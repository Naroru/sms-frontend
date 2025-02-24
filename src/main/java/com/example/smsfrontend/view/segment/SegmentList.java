package com.example.smsfrontend.view.segment;

import com.example.smsfrontend.proxy.segment.Segment;
import com.example.smsfrontend.proxy.segment.SegmentAdapter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

@Route("/segments")
@PageTitle("Сегменты")
@SpringComponent
public class SegmentList extends VerticalLayout {

  private final Grid<Segment> grid = new Grid<>(Segment.class);
  private final TextField filterName = new TextField("Наименование");
  private final TextField filterCode = new TextField("Код");
  private Text header = new Text("Сегменты");
  private final SegmentAdapter adapter;
  private final SegmentEditor editor;

  public SegmentList(SegmentAdapter adapter, SegmentEditor editor) {
    this.adapter = adapter;
    this.editor = editor;

    addListener(SegmentEditor.SaveEvent.class, saveEvent -> header.setText("test"));
    setClassName("segment-list-view");

    configureGrid();
    configureEditor();

    updateList();
    add(header, getFilterToolbar(), grid, editor);

  }

  private void configureGrid() {

    grid.getColumnByKey("id").setHeader("Код");
    grid.getColumnByKey("name").setHeader("Наименование");

    grid.getColumnByKey("id").setWidth("10%").setFlexGrow(0);
    grid.addItemDoubleClickListener(event -> editor.editSegment(event.getItem()));
  }

  private void configureEditor() {
    editor.setWidth("25em");
    editor.addSaveListener(this::saveSegment); // <1>
    // editor.addDeleteListener(this::deleteContact); // <2>
    editor.addCloseListener(e -> closeEditor()); // <3>
    closeEditor();
  }

  private void saveSegment(SegmentEditor.SaveEvent event) {
    adapter.save(event.getSegment());
    updateList();
    closeEditor();
  }

  private void updateList() {
    //todo реализовать вывод через grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(adapter));
    grid.setItems(adapter.findAll());
  }

  private Component getFilterToolbar() {

    //неактуально т.к. тут только 1 фильтр. Актуально когда несколько
    filterName.setValueChangeMode(ValueChangeMode.LAZY);
    filterName.setClearButtonVisible(true);
    filterName.addValueChangeListener(event -> listSegments());

    //неактуально т.к. тут только 1 фильтр. Актуально когда несколько
    filterCode.setValueChangeMode(ValueChangeMode.LAZY);
    filterCode.setClearButtonVisible(true);
    filterCode.addValueChangeListener(event -> listSegments());

    FormLayout formLayout = new FormLayout();
    formLayout.add(filterName, filterCode);

    return formLayout;

  }

  private void listSegments() {
    if (filterName.getValue().isBlank())
      //todo реализовать вывод через grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(adapter));
      grid.setItems(adapter.findAll());
    else
      grid.setItems(adapter.findByName(filterName.getValue()));
  }

  private void closeEditor() {
    editor.setSegment(null);
    editor.setVisible(false);
    removeClassName("editing");
  }
}
