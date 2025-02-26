package com.example.smsfrontend.view.segment;

import com.example.smsfrontend.common.searchcriteria.SearchCriteria;
import com.example.smsfrontend.common.searchcriteria.SearchCriteriaOperation;
import com.example.smsfrontend.proxy.segment.Segment;
import com.example.smsfrontend.proxy.segment.SegmentAdapter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoIcon;

import java.util.List;
import java.util.Objects;

import static com.vaadin.flow.data.provider.SortDirection.ASCENDING;

@Route("/segments")
@PageTitle("Сегменты")
@UIScope
@SpringComponent
public class SegmentList extends VerticalLayout implements KeyNotifier {

  private final Grid<Segment> grid = new Grid<>(Segment.class, false);
  private final TextField filterName = new TextField("Наименование");
  private final TextField filterId = new TextField("Код");
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

    addKeyPressListener(Key.DELETE, event -> {
      Segment segment = grid.asSingleSelect().getValue();
      if (Objects.nonNull(segment))
        adapter.setDeleted(segment.getId());
      updateList();
    });

    updateList();
    add(header, getFilterToolbar(), grid, editorByEvents, editorByInterfaces);
  }

  private void configureGrid() {

    grid.addColumn(createDeleteComponentRenderer()).setWidth("3em").setFlexGrow(0).setKey("isDeleted");
    grid.addColumn(Segment::getId).setHeader("Код").setWidth("10%").setFlexGrow(0).setKey("code");
    grid.addColumn(Segment::getName).setHeader("Наименование").setKey("name");

    grid.sort(List.of(new GridSortOrder<>(grid.getColumnByKey("code"), ASCENDING)));

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

  private ComponentRenderer<Div, Segment> createDeleteComponentRenderer() {
    return new ComponentRenderer<>(Div::new, (div, segment) -> {
      if (segment.isDeleted()) {
        div.add(new Icon("lumo", "cross"));
      } else {
        div.add(LumoIcon.CHECKMARK.create());
      }
    });
  }

  private void updateList() {

    SearchCriteria searchCriteria = new SearchCriteria();
    //todo реализовать вывод через grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(adapter)); Или пагинацию

    if (!filterName.getValue().isEmpty())
      searchCriteria.addCriteria("name", filterName.getValue(), SearchCriteriaOperation.LIKE);
    if (!filterId.getValue().isEmpty())
      searchCriteria.addCriteria("id", filterId.getValue(), SearchCriteriaOperation.EQUALS);

    if (searchCriteria.getCriteriaPositions().isEmpty())
      grid.setItems(adapter.findAll());
    else
      grid.setItems(adapter.findBySpec(searchCriteria));
  }

  private Component getFilterToolbar() {

    filterName.setValueChangeMode(ValueChangeMode.LAZY);
    filterName.setClearButtonVisible(true);
    filterName.addValueChangeListener(event -> updateList());

    filterId.setValueChangeMode(ValueChangeMode.LAZY);
    filterId.setHelperText("");
    filterId.setClearButtonVisible(true);
    filterId.addValueChangeListener(event -> updateList());

    FormLayout formLayout = new FormLayout();
    formLayout.add(filterName, filterId);

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
