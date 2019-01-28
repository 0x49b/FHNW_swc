package ch.fhnw.swc.mrs.view;

import java.time.LocalDate;

import ch.fhnw.swc.mrs.api.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Java FX controller class for Users.
 */
public class UserController extends AbstractController {
    @FXML
    private TableView<UserDTO> userTable;
    @FXML
    private TableColumn<UserDTO, Number> idColumn;
    @FXML
    private TableColumn<UserDTO, String> surnameColumn;
    @FXML
    private TableColumn<UserDTO, String> firstNameColumn;
    @FXML
    private TableColumn<UserDTO, LocalDate> birthdateColumn;
    @FXML
    private GridPane grid;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private Button cancelButton;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;

    private UserDTO editing = null; // currently no user is being edited.
    private ObservableList<UserDTO> userList = FXCollections.observableArrayList();

    /**
     * Reload data used/processed/displayed in this ServiceUtilizer.
     */
    public void reload() {
        userList.clear();
        for (UserDTO u : getBackend().getAllUsers()) {
            userList.add(u);
        }
        userTable.setItems(userList);
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has
     * been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the movie table.
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
        birthdateColumn.setCellValueFactory(cellData -> cellData.getValue().getBirthdate());

        userTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> handleSelectionChange(oldValue, newValue));

        grid.setDisable(true);
        showUserDetails(null);
    }

    private Object handleSelectionChange(UserDTO oldValue, UserDTO newValue) {
        handleCancel();
        if (newValue != null) {
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
        return null;
    }

    private void showUserDetails(UserDTO user) {
        if (user != null) {
            // fill the labels with info from the Movie object
            surnameField.setText(user.getName().get());
            firstNameField.setText(user.getFirstName().get());
            birthdatePicker.setValue(user.getBirthdate().get());
        } else {
            // clear the content and set default values
            surnameField.setText("");
            firstNameField.setText("");
            birthdatePicker.setValue(null);
        }
    }

    @FXML
    private void handleCancel() {
        cancelButton.setDisable(true);
        newButton.setDisable(false);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(true);
        showUserDetails(null);
        surnameField.setEditable(false);
        firstNameField.setEditable(false);
        birthdatePicker.setEditable(false);
        grid.setDisable(true);
        editing = null;
    }

    @FXML
    private void handleNew() {
        cancelButton.setDisable(false);
        newButton.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(false);
        showUserDetails(null);
        surnameField.setEditable(true);
        firstNameField.setEditable(true);
        birthdatePicker.setEditable(true);
        grid.setDisable(false);
        surnameField.requestFocus();
        editing = null;
    }

    @FXML
    private void handleSave() {
        if (editing == null) {
            UserDTO u = getBackend().createUser(surnameField.getText(), firstNameField.getText(),
                    birthdatePicker.getValue());
            userTable.getItems().add(u);
        } else {
            editing.setName(surnameField.getText());
            editing.setFirstName(firstNameField.getText());
            editing.setBirthdate(birthdatePicker.getValue());
            getBackend().updateUser(editing);
        }
        handleCancel();
    }

    @FXML
    private void handleEdit() {
        cancelButton.setDisable(false);
        newButton.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(false);
        UserDTO u = userTable.getSelectionModel().getSelectedItem();
        showUserDetails(u);
        surnameField.setEditable(true);
        firstNameField.setEditable(true);
        birthdatePicker.setEditable(true);
        grid.setDisable(false);
        surnameField.requestFocus();
        editing = u;
    }

    @FXML
    private void handleDelete() {
        cancelButton.setDisable(true);
        newButton.setDisable(false);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(true);
        UserDTO user = userTable.getSelectionModel().getSelectedItem();
        if (getBackend().deleteUser(user.getId().get())) {
            userTable.getItems().remove(user);
            userTable.getSelectionModel().clearSelection();
        }
        showUserDetails(null);
        surnameField.setEditable(false);
        firstNameField.setEditable(false);
        birthdatePicker.setEditable(false);
        grid.setDisable(true);
        editing = null;
    }

}
