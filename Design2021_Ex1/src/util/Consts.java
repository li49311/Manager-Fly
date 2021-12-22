package util;

import java.net.URLDecoder;

public class Consts {
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR =
			"jdbc:ucanaccess://" + DB_FILEPATH;
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";
	
	
	public static final String SQL_SEL_FLIGHTS = "SELECT Flight.flightID, Flight.originAirportID, Flight.destinationAirportID, Flight.status, Flight.airplaneID  FROM Flight";
	public static final String SQL_SEL_OBJECT_FLIGHT = "SELECT Flight.flightID, Flight.departureTime, Flight.landingTime, Flight.status, Flight.originAirportID, Flight.destinationAirportID, Flight.airplaneID FROM Flight";
	public static final String SQL_SEL_AIRPORTS = "SELECT Airport.* FROM Airport";
	public static final String SQL_SEL_AIRPLANES = "SELECT Airplane.* FROM Airplane";
	public static final String SQL_SEL_PILOTS = "SELECT Pilot.* FROM Pilot";
	public static final String SQL_INS_FLIGHT ="{ call quer_insert_flight(?,?,?,?,?,?,?,?,?)";
	public static final String SQL_INS_AIRPLANE ="{ call quer_insert_airplane(?,?)";
	public static final String SQL_INS_AIRPORT ="{ call quer_insert_airport(?,?,?,?)";
	public static final String SQL_INS_SEATS = "{ call quer_insert_seat(?,?,?,?)";
	public static final String SQL_UPD_FLIGHT_STATUS ="{ call quer_upd_flight_status(?,?) }";
	public static final String SQL_UPD_FLIGHT_PLANE = "{ call quer_upd_flight_plane(?,?) }";
	public static final String SQL_FLIGHTS_BY_STATUS = "{ call quer_flight_by_status(?) }";
	public static final String SQL_SEL_PLANES_BY_FLIGHT = "{ call quer_planes(?,?,?) }";
	public static final String SQL_DEL_AIRPLANE = "{ call quer_del_airplanes(?) }";
	public static final String SQL_VALIDATE_FLIGHT_BY_ORIGIN = "{ call quer_validate_flight_by_origin(?,?) }";
	public static final String SQL_VALIDATE_FLIGHT_BY_DESTINATION = "{ call quer_validate_flight_by_destination(?,?) }";
	public static final String SQL_SEL_SEATS_BY_AIRPLANE = "{ call quer_seats_by_airplane(?) }";
	
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/src/entity/db.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "/src/entity/db.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
