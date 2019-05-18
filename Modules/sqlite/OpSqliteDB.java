package sqlite;
public class OpSqliteDB {
    
    private static final String Class_Name = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\SJTUwwz\\test.db";

    public static void main(String args[]) {
        // load the sqlite-JDBC driver using the current class loader
        Connection connection = null;
        try {
            connection = createConnection();
            func1(connection);
            System.out.println("Success!");
        }  catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
    
    // 创建Sqlite数据库连接
    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(Class_Name);
        return DriverManager.getConnection(DB_URL);
    }

    public static void func1(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        Statement statement1 = connection.createStatement();
        statement.setQueryTimeout(30); // set timeout to 30 sec.
        // 执行查询语句
        ResultSet rs = statement.executeQuery("select * from table_name1");
        while (rs.next()) {
            String col1 = rs.getString("col1_name");
            String col2 = rs.getString("col2_name");
            System.out.println("col1 = " + col1 + "  col2 = " + col2);
            
            System.out.println(location);
            // 执行插入语句操作
            statement1.executeUpdate("insert into table_name2(col2) values('" + col2_value + "')");
            // 执行更新语句
            statement1.executeUpdate("update table_name2 set 字段名1=" +  字段值1 + " where 字段名2='" +  字段值2 + "'");
        }
    }