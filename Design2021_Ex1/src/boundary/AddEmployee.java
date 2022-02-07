package boundary;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import control.ControlFlights;
import control.ControlShibutz;
import entity.Employee;
import entity.FlightAttendant;
import entity.GroundAttendant;
import entity.Pilot;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AddEmployee {
	@FXML
    private AnchorPane mainScreen;
	@FXML
	private ComboBox<String> roleCombo;
	@FXML
    private AnchorPane fieldsPane;
	@FXML
    private TextField employeeIDField;
    @FXML
    private TextField employeeFNameField;
    @FXML
    private TextField employeeLNameField;
    @FXML
    private VBox pilotVBox;
    @FXML
    private TextField pilotLicenseNumField;
    @FXML
    private DatePicker pilotLicenseStartDate;
    @FXML
    private TableView<Employee> allRoleView;
    @FXML
    private TableColumn<Employee, Integer> IDCol;

    @FXML
    private TableColumn<Employee, String> fNameCol;

    @FXML
    private TableColumn<Employee, String> lNameCol;

    @FXML
    private TableColumn<Pilot, Integer> licenseNumCol;

    @FXML
    private TableColumn<Pilot, LocalDate> licenseDateCol;

	
	@FXML
	void initialize() {
		fieldsPane.setVisible(false);
		roleCombo.getItems().add("Pilot");
		roleCombo.getItems().add("Flight Attendant");
		roleCombo.getItems().add("Ground Attendant");
		
		roleCombo.setOnAction(event -> {
			allRoleView.getItems().clear();
			addToTable();
			fieldsPane.setVisible(true);
			if(roleCombo.getValue().equals("Pilot"))
				pilotVBox.setVisible(true);
			else {
				pilotVBox.setVisible(false);
			}
		});
	}
	
	 @FXML
	 void addEmployee(ActionEvent event) {
		 String role = roleCombo.getValue();
		 Integer employeeID = Integer.parseInt(employeeIDField.getText());
		 String fName = employeeFNameField.getText();
		 String lName = employeeLNameField.getText();
		if(role.equals("Pilot")) {
			 Integer licenseNum = Integer.parseInt(pilotLicenseNumField.getText());
			 Date licenseStart = Date.valueOf(pilotLicenseStartDate.getValue());
			 Pilot pilot = new Pilot(employeeID, fName, lName, Date.valueOf(LocalDate.now()), licenseNum, licenseStart);
			 ControlShibutz.getInstance().addPilot(pilot);			 
		 }
		else if(role.equals("Flight Attendant")) {
			FlightAttendant fa = new FlightAttendant(employeeID, fName, lName, Date.valueOf(LocalDate.now()));	
			ControlShibutz.getInstance().addFlightAttendant(fa);
		}
		else {
			GroundAttendant ga = new GroundAttendant(employeeID, fName, lName, Date.valueOf(LocalDate.now()));	
			ControlShibutz.getInstance().addGroundAttendant(ga);	
		}
		clearFields();
		allRoleView.getItems().clear();
		addToTable();
	 }

	private void addToTable() {
		if(roleCombo.getValue().equals("Flight Attendant")) {
			allRoleView.getItems().addAll(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllFA()));
			licenseNumCol.setVisible(false);
			licenseDateCol.setVisible(false);
		}
		else if (roleCombo.getValue().equals("Ground Attendant")) {
			allRoleView.getItems().addAll(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllGA()));
			licenseNumCol.setVisible(false);
			licenseDateCol.setVisible(false);
		}
		else {
			licenseNumCol.setVisible(true);
			licenseDateCol.setVisible(true);
			allRoleView.getItems().addAll(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllPilots()));
			licenseNumCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<Integer>(p.getValue().getLicenceID()));
			licenseDateCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<LocalDate>(p.getValue().getLicenceStartDate().toLocalDate()));
		}
		IDCol.setCellValueFactory(a -> new ReadOnlyObjectWrapper<Integer>(a.getValue().getEmployeeID()));
		fNameCol.setCellValueFactory(a -> new ReadOnlyObjectWrapper<String>(a.getValue().getFirstName()));
		lNameCol.setCellValueFactory(a -> new ReadOnlyObjectWrapper<String>(a.getValue().getLastName()));
		
	}

	private void clearFields() {
		employeeIDField.clear();
		employeeFNameField.clear();
		employeeLNameField.clear();
		pilotLicenseNumField.clear();
		pilotLicenseStartDate.setValue(null);
	}
	
	
	@FXML
    void returnToMenu(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/MenuScreen.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}
