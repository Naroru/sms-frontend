package com.example.mssfrontend.view;

import com.example.mssfrontend.model.employee.entity.Customer;
import com.example.mssfrontend.model.employee.entity.CustomerRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

  private final CustomerRepository repo;
  final Grid<Customer> grid = new Grid<>(Customer.class);;

	public MainView(CustomerRepository repo) {
    add(new Button("Click me", e -> Notification.show("Hello, Spring+Vaadin user!")));
      this.repo = repo;
      add(grid);
      listCustomers();
    }

    private void listCustomers() {
      grid.setItems(repo.findAll());
    }

  }
