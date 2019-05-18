package sqlite;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

 
public class SqliteTest{
		public static void main(String[] args) throws ClassNotFoundException, SQLException {
//			Class.forName("org.sqlite.JDBC");
//			String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
//			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
//			Statement state = conn.createStatement();
//			ResultSet rs = state.executeQuery("select * from sight;"); //��ѯ����
//			while (rs.next()) { //����ѯ�������ݴ�ӡ����
//				 System.out.println("id = " + rs.getString("sight_id") + " ");
//	             System.out.println("name = " + rs.getString("name") + " ");
//	             System.out.println("price = " + rs.getInt("price") + " "); 
//	             System.out.println("environment = " + rs.getInt("environment") + " ");
//	             System.out.println("service = " + rs.getInt("service") + " ");
//	             System.out.println("latitude = " + rs.getDouble("latitude") + " ");
//	             System.out.println("longitude = " + rs.getDouble("longitude") + "\n");
//			}
//	        rs.close();
//			conn.close();
//			get_hotel(1);
			get_all_sight();
		}
		public static Sight get_sight(int id) throws ClassNotFoundException, SQLException  {
			Class.forName("org.sqlite.JDBC");
			String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("select * from sight where sight_id =\'"+ id +"\';"); 
//			System.out.println("longitude = " + rs.getDouble("longitude") + "\n");
			Sight res =  new Sight(rs.getInt("sight_id"), rs.getString("name"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getInt("popularity"), rs.getInt("total"), rs.getInt("environment"), rs.getInt("service"), rs.getInt("price"));
			res.print();
	        rs.close();
			conn.close();//��ѯ����
			return res;
		}
		public static Hotel get_hotel(int id) throws ClassNotFoundException, SQLException  {
			Class.forName("org.sqlite.JDBC");
			String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("select * from hotel where hotel_id =\'"+ id +"\';"); 
//			System.out.println("longitude = " + rs.getDouble("longitude") + "\n");
			Hotel res =  new Hotel(rs.getInt("hotel_id"), rs.getString("name"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getInt("popularity"), rs.getInt("total"), rs.getInt("price"));
			res.print();
	        rs.close();
			conn.close();//��ѯ����
			return res;
		}
		public static List<Sight> get_all_sight() throws ClassNotFoundException, SQLException  {
			Class.forName("org.sqlite.JDBC");
			String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("select * from sight;"); 
			List<Sight> sightList = new ArrayList<Sight>();
			while (rs.next()) { //����ѯ�������ݴ�ӡ����
				Sight res =  new Sight(rs.getInt("sight_id"), rs.getString("name"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getInt("popularity"), rs.getInt("total"), rs.getInt("environment"), rs.getInt("service"), rs.getInt("price"));
				res.print();
				sightList.add(res);
			}
	        rs.close();
			conn.close();//��ѯ����
			return sightList;
		}
}
