package sqlite;
import java.sql.*;


public class SQLiteJDBC {
  public static void main( String args[] )
  {
	SqliteTest sq;
	sq.get_sights("1", 2);
//    Connection c = null;
//    try {
//      Class.forName("org.sqlite.JDBC");
//      c = DriverManager.getConnection("jdbc:sqlite:test.db");
//    } catch ( Exception e ) {
//      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//      System.exit(0);
//    }
//    System.out.println("Opened database successfully");
  }
}
