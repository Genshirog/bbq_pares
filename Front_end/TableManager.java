package Front_end;

import Back_end.EmployeeViews;
import Back_end.InventoryViews;
import Back_end.MenuViews;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TableManager {
    TableManager(){

    }

    public VBox createEmployeeTable() {
        TableView<EmployeeViews> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<EmployeeViews, String> idColumn = new TableColumn<>("EmployeeID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        idColumn.setMaxWidth(250);
        idColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> nameColumn = new TableColumn<>("Employee Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        nameColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.setMaxWidth(100);
        roleColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setMaxWidth(100);
        emailColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
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

        return new VBox(scrollPane);
    }

    public VBox createInventoryTable(){
        TableView<InventoryViews> table = new TableView<>();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<InventoryViews, String> idColumn = new TableColumn<>("InventoryID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryID"));
        idColumn.setReorderable(false);

        TableColumn<InventoryViews, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameColumn.setReorderable(false);

        TableColumn<InventoryViews, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockInColumn = new TableColumn<>("Date");
        stockInColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        stockInColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockDetails = new TableColumn<>("Details");
        stockDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        stockDetails.setReorderable(false);

        table.getColumns().addAll(idColumn, nameColumn, categoryColumn, stockColumn, stockInColumn,stockDetails);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        return new VBox(scrollPane);
    }

    public VBox createMenuTable(){
        TableView<MenuViews> table = new TableView<>();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<MenuViews, String> idColumn = new TableColumn<>("MenuID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("menuID"));
        idColumn.setReorderable(false);

        TableColumn<MenuViews, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameColumn.setReorderable(false);

        TableColumn<MenuViews, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setReorderable(false);

        TableColumn<MenuViews, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setReorderable(false);

        TableColumn<MenuViews, String> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        availabilityColumn.setReorderable(false);

        TableColumn<MenuViews, String> stockDetails = new TableColumn<>("Details");
        stockDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        stockDetails.setReorderable(false);

        table.getColumns().addAll(idColumn, nameColumn, categoryColumn, priceColumn, availabilityColumn,stockDetails);
        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        return new VBox(scrollPane);
    }

    public VBox createRoleTable() {
        TableView<EmployeeViews> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<EmployeeViews, String> idColumn = new TableColumn<>("RoleID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        idColumn.setMaxWidth(250);
        idColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.setMaxWidth(100);
        roleColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setMaxWidth(100);
        descriptionColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> shiftColumn = new TableColumn<>("Shift(Day/Night/FullTime)");
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

        return new VBox(scrollPane);
    }

    public VBox createProductTable() {
        TableView<EmployeeViews> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<EmployeeViews, String> idColumn = new TableColumn<>("Product ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        idColumn.setMaxWidth(250);
        idColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> prodNameColumn = new TableColumn<>("Product Name");
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        prodNameColumn.setMaxWidth(250);
        prodNameColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> priceColumn = new TableColumn<>("Product Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prodPrice"));
        priceColumn.setMaxWidth(250);
        priceColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> costColumn = new TableColumn<>("Product Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("prodCost"));
        costColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> supplierColumn = new TableColumn<>("Product Supplier");
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("prodSupplier"));
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

        return new VBox(scrollPane);
    }

    public VBox createSupplierTable() {
        TableView<EmployeeViews> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<EmployeeViews, String> idColumn = new TableColumn<>("Supplier ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        idColumn.setMaxWidth(250);
        idColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> supNameColumn = new TableColumn<>("Supplier Name");
        supNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supNameColumn.setMaxWidth(250);
        supNameColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> personColumn = new TableColumn<>("Contact Person");
        personColumn.setCellValueFactory(new PropertyValueFactory<>("prodPrice"));
        personColumn.setMaxWidth(250);
        personColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("prodCost"));
        emailColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("prodSupplier"));
        phoneColumn.setReorderable(false);

        table.getColumns().addAll(idColumn,supNameColumn,personColumn,emailColumn,phoneColumn);
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
}
