package control;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.HashMap;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;

public class ControlReport {
	
	public static ControlReport instance;
	
	public static ControlReport getInstance() {
		if (instance == null)
			instance = new ControlReport();
		return instance;
	}
	
	public JFrame produceReport(Integer seatNum, Date start, Date end) 
	{
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();

				params.put("tourist-seats", seatNum);
				params.put("from", start);
				params.put("to", end);
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary/BigFlightRepo.jasper"),
						params, conn);
				JFrame frame = new JFrame("Big Flight Report");
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

}
