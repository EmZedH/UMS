package Model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Connect {

    static String url = "jdbc:sqlite:db/ums.db"; //"jdbc:sqlite:E:Github/Internship/UMS/db/ums.db"
/*
 * Don't forget to add sqlite-jdbc-3.40.0.0.jar into Referenced Libraries folder
 */

    public static Connection connection() throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }

    public static List<List<String>> createArrayFromTable(String sqlQuery, String[] resultSetStrings) throws SQLException{
        List<List<String>> multidimensionalArrayList = new ArrayList<>();
        List<String> arrayList = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connection();Statement stmt = connection.createStatement()) {
            resultSet = stmt.executeQuery(sqlQuery);
            while(resultSet.next()){
               for (String string : resultSetStrings) {
                    arrayList.add(resultSet.getString(string));
               }
                multidimensionalArrayList.add(arrayList);
                arrayList = new ArrayList<>();
            }
        }
        return multidimensionalArrayList;
    }
    
}
