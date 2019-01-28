package ch.fhnw.swc.mrs.view;

import java.time.LocalDate;

import ch.fhnw.swc.mrs.api.MovieDTO;
import ch.fhnw.swc.mrs.api.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/** Java FX controller class for rent movies tab. */
public class RentMovieController extends AbstractController {
    @FXML
    private TableView<MovieDTO> availableMoviesTable;
    @FXML
    private TableColumn<MovieDTO, Number> idColumn;
    @FXML
    private TableColumn<MovieDTO, String> titleColumn;
    @FXML
    private TableColumn<MovieDTO, LocalDate> releaseDateColumn;
    @FXML
    private TableColumn<MovieDTO, String> priceCategoryColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField firstnameField;
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private DatePicker rentalDatePicker;
    @FXML
    private CheckBox newUser;
    @FXML
    private Button getUserButton;
    @FXML
    private Button clearAllButton;
    @FXML
    private Button saveButton;

    private ObservableList<MovieDTO> rentMovieList = FXCollections.observableArrayList();
    private UserDTO found = null;

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has
     * been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the movie table.
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
        releaseDateColumn.setCellValueFactory(cellData -> cellData.getValue().getReleaseDate());
        priceCategoryColumn.setCellValueFactory(cellData -> cellData.getValue().getPriceCategory());
        availableMoviesTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handleSelectionChange(oldValue, newValue));
    }

    @Override
    public void reload() {
        rentMovieList.clear();
        for (MovieDTO m : getBackend().getAllMovies(false)) {
            rentMovieList.add(m);
        }
        availableMoviesTable.setItems(rentMovieList);
    }

    @FXML
    private void handleNewUser() {
        if (newUser.isSelected()) {
            setNewUserEnabling();
        } else {
            setReadyEnabling();
        }
        clearAllFields();
        surnameField.requestFocus();
    }

    private void setReadyEnabling() {
        newUser.setDisable(false);
        getUserButton.setDisable(false);
        saveButton.setDisable(true);
        idField.setDisable(false);
        surnameField.setDisable(false);
        firstnameField.setDisable(true);
        birthdatePicker.setDisable(true);
        rentalDatePicker.setDisable(true);
    }

    private void setNewUserEnabling() {
        newUser.setDisable(false);
        getUserButton.setDisable(true);
        saveButton.setDisable(false);
        idField.setDisable(true);
        surnameField.setDisable(false);
        firstnameField.setDisable(false);
        birthdatePicker.setDisable(false);
        rentalDatePicker.setDisable(false);
    }

    private void clearAllFields() {
        surnameField.clear();
        firstnameField.clear();
        idField.clear();
        rentalDatePicker.setValue(null);
        birthdatePicker.setValue(null);
    }

    @FXML
    private void handleClearAll() {
        newUser.setSelected(false);
        setReadyEnabling();
        clearAllFields();
    }

    @FXML
    private void handleGetUser() {
        String username = surnameField.getText();
        String idstring = idField.getText();
        try {
            int id = Integer.parseInt(idstring);
            found = getBackend().getUserById(id);
        } catch (NumberFormatException e) {
            found = getBackend().getUserByName(username);
        }
        if (found != null) {
            idField.setText(Integer.toString(found.getId().get()));
            surnameField.setText(found.getName().get());
            firstnameField.setText(found.getFirstName().get());
            birthdatePicker.setValue(found.getBirthdate().get());
            rentalDatePicker.setValue(LocalDate.now());
            idField.setDisable(true);
            surnameField.setDisable(true);
        } else {
            idField.setText(null);
            surnameField.setText(null);
            firstnameField.setText(null);
            surnameField.requestFocus();
        }
    }

    @FXML
    private void handleSave() {
        MovieDTO m = availableMoviesTable.getSelectionModel().getSelectedItem();
        reload();
        try {
            getBackend().createRental(found.getId().get(), m.getId().get(), LocalDate.now());
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        handleClearAll();
    }

    @FXML
    private void enterPressed() {
        if (newUser.isSelected() && !saveButton.isDisabled()) { // enter means save
            handleSave();
        } else { // enter means get User
            handleGetUser();
        }
    }

    private void handleSelectionChange(MovieDTO oldMovie, MovieDTO newMovie) {
        saveButton.setDisable(newMovie == null);
    }

}
