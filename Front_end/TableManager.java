package Front_end;

import Back_end.*;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class TableManager {
    TableManager(){

    }

    //We will display the array of our List<EmployeeViews>
    //I know its an array because our while loop keep adding contents to our list
    public VBox createEmployeeTable(List<EmployeeViews> employees) {
        TableView<EmployeeViews> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<EmployeeViews, String> idColumn = new TableColumn<>("EmployeeID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        idColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> nameColumn = new TableColumn<>("Employee Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        nameColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("employeeRole"));
        roleColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("employeeEmail"));
        emailColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        phoneColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> password = new TableColumn<>("Password");
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        password.setReorderable(false);

        table.getColumns().addAll(idColumn, nameColumn, roleColumn, emailColumn, phoneColumn,password);
        table.setTableMenuButtonVisible(false);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);
        table.getItems().addAll(employees);
        return new VBox(scrollPane);
    }

    public VBox createInventoryTable(List<InventoryViews> inventory){
        TableView<InventoryViews> table = new TableView<>();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<InventoryViews, String> idColumn = new TableColumn<>("InventoryID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryID"));
        idColumn.setReorderable(false);

        TableColumn<InventoryViews, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameColumn.setReorderable(false);

        TableColumn<InventoryViews, String> supplierColumn = new TableColumn<>("Supplier Name");
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockInColumn = new TableColumn<>("Date");
        stockInColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        stockInColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockAvailability = new TableColumn<>("Availability");
        stockAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        stockAvailability.setReorderable(false);

        table.getColumns().addAll(idColumn, nameColumn, supplierColumn, stockColumn, stockInColumn,stockAvailability);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);
        table.getItems().addAll(inventory);
        return new VBox(scrollPane);
    }

    public VBox createMenuTable(List<MenuViews> products) {
        TableView<MenuViews> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<MenuViews, String> idColumn = new TableColumn<>("Product ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        idColumn.setReorderable(false);

        TableColumn<MenuViews, String> prodNameColumn = new TableColumn<>("Product Name");
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        prodNameColumn.setReorderable(false);

        TableColumn<MenuViews, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setReorderable(false);
        priceColumn.setCellFactory(column -> new TableCell<MenuViews, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);

                if (empty || price == null) {
                    setText(null);
                } else {
                    setText("₱" + String.format("%.2f", price));
                }
            }
        });

        table.getColumns().addAll(idColumn,prodNameColumn,priceColumn);
        table.setTableMenuButtonVisible(false);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);
        table.getItems().addAll(products);

        return new VBox(scrollPane);
    }

    public VBox createRoleTable(List<RoleView> roles) {
        TableView<RoleView> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<RoleView, String> idColumn = new TableColumn<>("RoleID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("roleID"));
        idColumn.setReorderable(false);

        TableColumn<RoleView, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        roleColumn.setReorderable(false);

        TableColumn<RoleView, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setReorderable(false);

        TableColumn<RoleView, String> shiftColumn = new TableColumn<>("Shift(Day/Night/FullTime)");
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));
        shiftColumn.setReorderable(false);

        table.getColumns().addAll(idColumn,roleColumn,descriptionColumn,shiftColumn);
        table.setTableMenuButtonVisible(false);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);
        table.getItems().addAll(roles);
        return new VBox(scrollPane);
    }

    public VBox createProductTable(List<ProductView> products) {
        TableView<ProductView> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<ProductView, String> idColumn = new TableColumn<>("Product ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        idColumn.setReorderable(false);

        TableColumn<ProductView, String> prodNameColumn = new TableColumn<>("Product Name");
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        prodNameColumn.setReorderable(false);

        TableColumn<ProductView, Double> priceColumn = new TableColumn<>("Product Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setReorderable(false);
        priceColumn.setCellFactory(column -> new TableCell<ProductView, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);

                if (empty || price == null) {
                    setText(null);
                } else {
                    setText("₱" + String.format("%.2f", price));
                }
            }
        });

        TableColumn<ProductView, Double> costColumn = new TableColumn<>("Product Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        costColumn.setReorderable(false);
        costColumn.setCellFactory(column -> new TableCell<ProductView, Double>(){
            @Override
            protected void updateItem(Double cost, boolean empty){
                if(empty || cost == null){
                    setText(null);
                }else{
                    setText("₱" + String.format("%.2f", cost));
                }
            }
        });
        TableColumn<ProductView, String> supplierColumn = new TableColumn<>("Product Supplier");
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        supplierColumn.setReorderable(false);

        table.getColumns().addAll(idColumn,prodNameColumn,priceColumn,costColumn,supplierColumn);
        table.setTableMenuButtonVisible(false);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);
        table.getItems().addAll(products);

        return new VBox(scrollPane);
    }

    public VBox createSupplierTable(List<SupplierView> suppliers) {
        TableView<SupplierView> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<SupplierView, String> idColumn = new TableColumn<>("Supplier ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        idColumn.setReorderable(false);

        TableColumn<SupplierView, String> supNameColumn = new TableColumn<>("Supplier Name");
        supNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supNameColumn.setReorderable(false);

        TableColumn<SupplierView, String> personColumn = new TableColumn<>("Contact Person");
        personColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        personColumn.setReorderable(false);

        TableColumn<SupplierView, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setReorderable(false);

        TableColumn<SupplierView, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("supplierMail"));
        emailColumn.setReorderable(false);

        TableColumn<SupplierView, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("supplierNumber"));
        phoneColumn.setReorderable(false);

        table.getColumns().addAll(idColumn,supNameColumn,personColumn,addressColumn,emailColumn,phoneColumn);
        table.setTableMenuButtonVisible(false);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);
        table.getItems().addAll(suppliers);
        return new VBox(scrollPane);
    }

    public VBox createReportTable() {
        TableView<EmployeeViews> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<EmployeeViews, String> idColumn = new TableColumn<>("Inventory ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryID"));
        idColumn.setMaxWidth(250);
        idColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> prodNameColumn = new TableColumn<>("Product Name");
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("prodName"));
        prodNameColumn.setMaxWidth(250);
        prodNameColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> supNameColumn = new TableColumn<>("Supplier Name");
        supNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supNameColumn.setMaxWidth(250);
        supNameColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setMaxWidth(250);
        stockColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> detailsColumn = new TableColumn<>("Details");
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));
        detailsColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setReorderable(false);

        table.getColumns().addAll(idColumn,prodNameColumn,supNameColumn,stockColumn,detailsColumn,dateColumn);
        table.setTableMenuButtonVisible(false);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        return new VBox(scrollPane);
    }

    public VBox setupMenuTable(CashierOrder cashierOrder) {
        TableView<MenuItem> menuTable = new TableView<>();
        menuTable.getStyleClass().add("menu-table");
        menuTable.setPrefSize(750, 900);

        // Columns
        TableColumn<MenuItem, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeCol.setPrefWidth(180);
        codeCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");
        codeCol.setReorderable(false);
        codeCol.setResizable(false);
        codeCol.setSortable(false);

        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(280);
        nameCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");
        nameCol.setReorderable(false);
        nameCol.setResizable(false);
        nameCol.setSortable(false);

        TableColumn<MenuItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(280);
        priceCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");
        priceCol.setReorderable(false);
        priceCol.setResizable(false);
        priceCol.setSortable(false);
        priceCol.setCellFactory(col -> new TableCell<MenuItem, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText("₱" + String.format("%.2f", price));
                }
            }
        });

        TableColumn<MenuItem, String> availabilityCol = new TableColumn<>("Availability");
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        availabilityCol.setPrefWidth(150);
        availabilityCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");
        availabilityCol.setReorderable(false);
        availabilityCol.setResizable(false);
        availabilityCol.setSortable(false);

        TableColumn<MenuItem, Integer> quantityCol = new TableColumn<>("Quantity Available");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityAvailable"));
        quantityCol.setPrefWidth(150);
        quantityCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");
        quantityCol.setReorderable(false);
        quantityCol.setResizable(false);
        quantityCol.setSortable(false);


        menuTable.getColumns().addAll(codeCol, nameCol, priceCol, availabilityCol, quantityCol);

        // Data from database
        ObservableList<MenuItem> menuItems = DatabaseHandler.getMenuItemsFromDatabase();
        menuTable.setItems(menuItems);

        // Setup actions for menu table
        menuTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                MenuItem selectedItem = menuTable.getSelectionModel().getSelectedItem();
                if (selectedItem != null && "Available".equals(selectedItem.getAvailability())) {
                    cashierOrder.addToOrder(selectedItem);
                }
            }
        });
        VBox.setVgrow(menuTable, Priority.ALWAYS);
        menuTable.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(menuTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return new VBox(scrollPane);
    }
}
