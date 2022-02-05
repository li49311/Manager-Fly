package boundary;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import control.ControlShibutz;
import entity.GroundAttendant;
import entity.Shift;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import util.ShiftRole;

public class AddShift {
	
	@FXML
    private ChoiceBox<Integer> startHour;

    @FXML
    private ChoiceBox<Integer> startMinute;

    @FXML
    private ChoiceBox<Integer> endHour;

    @FXML
    private ChoiceBox<Integer> endMinute;
    
    @FXML
    private DatePicker startDate;
    
    @FXML
    private DatePicker endDate;
    
    @FXML
    private ChoiceBox<GroundAttendant> gaCombo;

    @FXML
    private ChoiceBox<ShiftRole> roleCombo;
	
	@FXML
	void initialize() {
		ArrayList<Integer> hoursList  = new ArrayList<>();
		ArrayList<Integer> minuteList  = new ArrayList<>();
		for(int i=0;i<24;i++) {
			hoursList.add(i);
		}
		startHour.setItems(FXCollections.observableArrayList(hoursList));	
		endHour.setItems(FXCollections.observableArrayList(hoursList));	
		for(int i=0;i<60;i++) {
			minuteList.add(i);
		}
		startMinute.setItems(FXCollections.observableArrayList(minuteList));
		endMinute.setItems(FXCollections.observableArrayList(minuteList));
		
		roleCombo.setItems(FXCollections.observableArrayList(ShiftRole.values()));
		gaCombo.setItems(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllGA()));
	}
	
	@FXML
	void addShift(ActionEvent event) {
		LocalDateTime startTime = LocalDateTime.of(startDate.getValue().getYear(), startDate.getValue().getMonth(), startDate.getValue().getDayOfMonth(), startHour.getValue(), startMinute.getValue());		
		LocalDateTime endTime = LocalDateTime.of(endDate.getValue().getYear(), endDate.getValue().getMonth(), endDate.getValue().getDayOfMonth(), endHour.getValue(), endMinute.getValue());
		
		Timestamp start = Timestamp.valueOf(startTime);
		Timestamp end = Timestamp.valueOf(endTime);
		
		Shift shift = new Shift(start, end);
		GroundAttendant ga = gaCombo.getValue();
		ControlShibutz.getInstance().addGAInShift(shift, ga.getEmployeeID(), roleCombo.getValue().toString());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Ground Attendant added successfully to shift");
		alert.setTitle("New ga added");
		alert.showAndWait();
		clearFields();
	}

	private void clearFields() {
		startDate.setValue(null);
		startHour.getSelectionModel().clearSelection();
		startMinute.getSelectionModel().clearSelection();
		endDate.setValue(null);
		endHour.getSelectionModel().clearSelection();
		endMinute.getSelectionModel().clearSelection();
		gaCombo.getSelectionModel().clearSelection();
		roleCombo.getSelectionModel().clearSelection();
		
	}

}
