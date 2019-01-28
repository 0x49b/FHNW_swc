package ch.fhnw.swc.mrs.view;

import java.time.LocalDate;

import ch.fhnw.swc.mrs.api.RentalDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Java FX controller class for Rentals.
 */
public class RentalController extends AbstractController {
    @FXML
    private TableView<RentalDTO> rentalTable;
    @FXML
    private TableColumn<RentalDTO, Number> idColumn;
    @FXML
    private TableColumn<RentalDTO, Number> rentalDaysColumn;
    @FXML
    private TableColumn<RentalDTO, LocalDate> rentalDateColumn;
    @FXML
    private TableColumn<RentalDTO, String> surnameColumn;
    @FXML
    private TableColumn<RentalDTO, String> firstNameColumn;
    @FXML
    private TableColumn<RentalDTO, String> titleColumn;
    @FXML
    private TableColumn<RentalDTO, Number> rentalFeeColumn;
    @FXML
    private Button deleteButton;

    private ObservableList<RentalDTO> rentalList = FXCollections.observableArrayList();

    @Override
    public void reload() {
        rentalList.clear();
        for (RentalDTO r : getBackend().getAllRentals()) {
            rentalList.add(r);
        }
        rentalTable.setItems(rentalList);
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has
     * been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the movie table.
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        rentalDaysColumn.setCellValueFactory(cellData -> cellData.getValue().getRentalDays());
        rentalDateColumn.setCellValueFactory(cellData -> cellData.getValue().getRentalDate());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getUserName());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getUserFirstName());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
        rentalFeeColumn.setCellValueFactory(cellData -> cellData.getValue().getRentalFee());

        rentalTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handleSelectionChange(oldValue, newValue));

        deleteButton.setDisable(true);
    }

    private Object handleSelectionChange(RentalDTO oldValue, RentalDTO newValue) {
        deleteButton.setDisable(newValue == null);
        return null;
    }

    @FXML
    private void handleDelete() {
        deleteButton.setDisable(true);
        RentalDTO r = rentalTable.getSelectionModel().getSelectedItem();
        if (getBackend().returnRental(r.getId().get())) {
            rentalTable.getItems().remove(r);
            rentalTable.getSelectionModel().clearSelection();
        }
    }

}
