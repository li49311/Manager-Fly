package boundary;

import java.sql.Date;
import java.time.LocalDate;
import control.ControlShibutz;
import entity.FlightAttendant;
import entity.GroundAttendant;
import entity.Pilot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AddEmployee {
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
	void initialize() {
		fieldsPane.setVisible(false);
		roleCombo.getItems().add("Pilot");
		roleCombo.getItems().add("Flight Attendant");
		roleCombo.getItems().add("Ground Attendant");
		
		roleCombo.setOnAction(event -> {
			fieldsPane.setVisible(true);
			if(roleCombo.getValue().equals("Pilot"))
				pilotVBox.setVisible(true);
			else
				pilotVBox.setVisible(false);
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
	 }

	private void clearFields() {
		employeeIDField.clear();
		employeeFNameField.clear();
		employeeLNameField.clear();
		pilotLicenseNumField.clear();
		pilotLicenseStartDate.setValue(null);
		roleCombo.getSelectionModel().clearSelection();	
	}

}
