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
  private final Text header = new Text("Сегменты");
  private final SegmentAdapter adapter;

  public SegmentList(SegmentAdapter adapter, SegmentEditor editor) {
    this.adapter = adapter;

    setClassName("segment-list-view");

//todo конфигурацию таблицы вынести в отдельный метод
    grid.getColumnByKey("id").setHeader("Код");
    grid.getColumnByKey("name").setHeader("Наименование");

    grid.getColumnByKey("id").setWidth("10%").setFlexGrow(0);


    //todo реализовать вывод через grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(adapter));
    grid.setItems(adapter.findAll());
    grid.addItemDoubleClickListener(event -> editor.editSegment(event.getItem()));

    add(header, getFilterToolbar(), grid, editor);
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

}
