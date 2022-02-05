package boundary;

import control.ControlXml;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ImportXml {
	@FXML
	private Button importBtn;
	
	@FXML
    void importXml(ActionEvent event) {
		ControlXml.getInstance().importStatusFromXML("resources/flights.xml");
    }
}
