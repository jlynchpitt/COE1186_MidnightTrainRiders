package MTR.NorthShoreExtension.ProofOfConcept;

import java.sql.SQLException;

import MTR.NorthShoreExtension.Backend.DBHelper;

public class DBTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		DBHelper db = new DBHelper();
		
		db.addTrack(1001, "Green", "A", 1, 100, 0.5, 50, "", 10, 100, 12, 20, 13, 21, 1, 2, "on");
		
	}

}
