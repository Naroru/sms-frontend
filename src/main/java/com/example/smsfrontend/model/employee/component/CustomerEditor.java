package com.example.smsfrontend.model.employee.component;


import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class CustomerEditor extends FormLayout implements KeyNotifier {
  //форм слой автоматом распихивает поля и кнопки красиво: поля могут быть в 1 или 2 колонки в зависимости от ширины компонента. автоматом выравнивает красиво
/*
  private final CustomerRepository repository;
  private Customer customer;
  //KeyNotifier позволяет, чтобы этот элемент (форма) обрабатывала нажатия клавиш клавиатуры
  //customer будет привязан к данным полям с помощью binder. По конвенции имен, т.к. поля ниже
  //имееют такие же имена как в customer, то они будут связаны через рефлексию - при изменении в текстовом поле
  //будет меняться такое же поле в customer. И наоборот

  *//* Fields to edit properties in Customer entity *//*
  private TextField firstName = new TextField("First name");
  private TextField lastName = new TextField("Last name");
  private Checkbox status = new Checkbox("Status");

  *//* Action buttons *//*
  private Button save = new Button("Save", VaadinIcon.CHECK.create());
  private Button cancel = new Button("Cancel");
  private Button delete = new Button("Delete", VaadinIcon.TRASH.create());

  private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);


  private Binder<Customer> binder = new Binder<>(Customer.class);


  @Setter
  private ChangeHandler changeHandler;

  public interface ChangeHandler {
    void onChange();
  }

  @Autowired
  public CustomerEditor(CustomerRepository repository) {
    this.repository = repository;

*//*
    add(firstName, lastName, status, actions);
*//*

    // bind using naming convention
    binder.bindInstanceFields(this);

    //setSpacing(true); актуально для вертикального слоя, а не Форм слоя

    save.getElement().getThemeList().add("primary");
    delete.getElement().getThemeList().add("error");

    addKeyPressListener(Key.ENTER, e -> save());

    //можно установить выравивание элементов в слое. например по низу. Это актуально, когда, например, у кнопок есть label - надпись над кнопкой
//actions.setDefaultVerticalComponentAlignment(Alignment.BASELINE); ктуально для вертикального слоя, а не Форм слоя.

    save.addClickListener(e -> save());
    delete.addClickListener(e -> delete());
    cancel.addClickListener(e -> editCustomer(customer));
    setVisible(false);
  }

  private void delete() {
    repository.delete(customer);
    changeHandler.onChange();
  }

  private void save() {
    repository.save(customer);
    changeHandler.onChange();
  }

  public final void editCustomer(Customer newCustomer) {
    if (newCustomer == null) {
      setVisible(false);
      return;
    }

    if (newCustomer.getId() != null) {
      customer = repository.findById(newCustomer.getId()).orElse(newCustomer);
    } else {
      customer = newCustomer;
    }

    binder.setBean(newCustomer);

    setVisible(true);
    firstName.focus();
  }*/

}

