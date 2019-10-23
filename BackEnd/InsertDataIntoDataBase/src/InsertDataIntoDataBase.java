import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.jdi.connect.spi.Connection;

public class InsertDataIntoDataBase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		Connection connection = (Connection) DriverManager.getConnection("jdbc:oracle:thin@localhost", "USERNAME", "PASSWORD");
		//Still have to enter server username and password
		Statement statement = ((java.sql.Connection) connection).createStatement();
		int update = statement.executeUpdate("insert into first_program values(1,'', '') ");
		System.out.println(update + "Row Inserted");
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
