package com.example.mssfrontend.view;

import com.example.mssfrontend.model.employee.component.CustomerEditor;
import com.example.mssfrontend.proxy.segment.SegmentAdapter;
import com.example.mssfrontend.proxy.segment.SegmentDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route
@PageTitle("Покупатели")
public class MainView extends VerticalLayout {

  private final SegmentAdapter adapter;

  private final CustomerEditor editor;

  private final TextField filter = new TextField("");
  private final Button addNewButton = new Button("Add new segment", VaadinIcon.PLUS.create());
  private final Grid<SegmentDto> grid = new Grid<>(SegmentDto.class);
  private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton); //можно создать в методе и получить значение в конструкторе, а не объвлять в классе

  public MainView(SegmentAdapter adapter, CustomerEditor editor) {

    this.adapter = adapter;
    this.editor = editor;


    setClassName("main-view"); //создает css класс, в котором можно будет настраивать стили

    setSizeFull();// по умолчанию вертикальный слой занимает столько места, сколько его элементы. А нам нужно чтобы он был на весь экран. Но т.к. Таблица (Grid) по ширине на весь экран то все оК
    //однако по длина таблица может быть не весь экран. ЧТобы таб была весь экран - тут ставим     setSizeFull() а в таблице grid.setHeightFull();

    add(new Button("Click me 4", e -> Notification.show("Hello, Spring+Vaadin user!")));


    //grid.setWidth("500px");
    //grid.setHeight("300px");
    grid.setHeightFull(); //будет занимать всю длину родительского элемента, т.е. формачким


    // grid.setColumns("id", "firstName", "lastName");
    grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);  //запрещаем колонке в таблице динамически расширяться. Например если экранного пространства станет больше

    //  grid.setColumns("id", "firstName", "lastName", "status"); //по умолчанию таблица показывает все колонки из энтити. Тут можно настроить, чтобы не все, а определенные
    //при этом, для данных колонок будет выводиться строковое представление. Если у нас есть колонка, содержащая другой объект, то наверно строковое представление toString будет нехорошо
    //поэтому можно конкретно задать, что там показывать
    //grid.addColumn(customer -> customer.getCompany().getName()).setHeader("Организация"));

    filter.setPlaceholder("Type to filter");
    filter.setValueChangeMode(ValueChangeMode.LAZY); // что событие изменения значения будет генерироваться не сразу после изменения, а с задержкой.
    // Таким образом, событие будет отправлено только тогда, когда пользователь перестанет вводить внутри компонента на определенное время (по умолчанию 300 мс).

    filter.addValueChangeListener(field -> listCustomers(field.getValue()));
    filter.setClearButtonVisible(true);
    // Connect selected Customer to editor or hide if none is selected
    // grid.asSingleSelect().addValueChangeListener(e -> {
    //  this.editor.editCustomer(e.getValue());
    //  });

    // Instantiate and edit new Customer the new button is clicked
    // addNewButton.addClickListener(e -> editor.editCustomer(new Customer()));

    add(toolbar, grid, editor);


    // Listen changes made by the editor, refresh data from backend
/*    editor.setChangeHandler(() -> {
      editor.setVisible(false);
      listCustomers(filter.getValue());
    });*/

    // Initialize listing
    listCustomers(null);

  }

  ;


  void listCustomers(String filterText) {
    if (StringUtils.hasText(filterText)) {
      grid.setItems(adapter.findAll());
    } else {
      grid.setItems(adapter.findAll());
      //данный способ плох для больших таблиц, т.к. данный способ хранит весь список данных в ОЗУ. При этом в браузер данные отправляются порционно - по страницам
      //  grid.setItems(repo.findAll());
      //  grid.setItems(VaadinSpringDataHelpers.fromPagingRepository(repo));
    }
  }

}
