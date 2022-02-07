package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;

public class ExportControl {
	
	private static ExportControl instance;
	
	public static ExportControl getInstance() {
		if (instance == null)
			instance = new ExportControl();
		return instance;
	}
	
	public void exportToJSON() 
	{
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_EXPORT_DATA)){
					ResultSet rs = stmt.executeQuery(); {
				JsonArray updatedFlights = new JsonArray();
				while (rs.next()) {
					JsonObject updatedFlight = new JsonObject();
					String flightID = rs.getString(1);
					System.out.println(flightID);
					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
						updatedFlight.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					
						Class.forName(Consts.JDBC_STR);
						try (Connection conn2 = DriverManager.getConnection(Consts.CONN_STR);
								CallableStatement stmt2 = conn.prepareCall(Consts.SQL_SEATS_DETAILS)){
								stmt2.setString(1, flightID);
								ResultSet rs2 = stmt2.executeQuery(); {
									JsonArray updatedSeats = new JsonArray();
									while (rs2.next()) {
										JsonObject updatedSeat = new JsonObject();
										
										for (int i = 1; i <= rs2.getMetaData().getColumnCount(); i++)
											updatedSeat.put(rs2.getMetaData().getColumnName(i), rs2.getString(i));
										updatedSeats.add(updatedSeat);
									}
									updatedFlight.put("SeatsInFlight", updatedSeats);
								}
						}
					updatedFlights.add(updatedFlight);
				}
				JsonObject doc = new JsonObject();
				doc.put("flights", updatedFlights);

				File file = new File("json/flights.json");
				file.getParentFile().mkdir();

				try (FileWriter writer = new FileWriter(file)) {
					writer.write(Jsoner.prettyPrint(doc.toJson()));
					writer.flush();
					 Alert alert = new Alert(AlertType.INFORMATION, "flights data exported successfully!");
					 alert.setHeaderText("Success");
					 alert.setTitle("Success Data Export");
					 alert.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
					}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
}
