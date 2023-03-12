package Model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Connect {

    static String url = "jdbc:sqlite:/Users/muhamed-pt7045/Desktop/UMS/UMS/db/ums.db"; //"jdbc:sqlite:E:Github/Internship/UMS/db/ums.db"
/*
 * Don't forget to add sqlite-jdbc-3.40.0.0.jar into Referenced Libraries folder
 */

    public static Connection connection() throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }

    public static String[][] createArrayFromTable(String sqlQuery, String[] resultSetStrings) throws SQLException{
        ArrayList<ArrayList<String>> multidimensionalArrayList = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connection();Statement stmt = connection.createStatement()) {
            resultSet = stmt.executeQuery(sqlQuery);
            while(resultSet.next()){
                arrayList = new ArrayList<>();
                for(String string : resultSetStrings) {
                    arrayList.add(resultSet.getString(string));
                }
                multidimensionalArrayList.add(arrayList);
            }
        }
        String[][] returnArray = new String[multidimensionalArrayList.size()][arrayList.size()];
        for (int i = 0; i < multidimensionalArrayList.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                returnArray[i][j] = multidimensionalArrayList.get(i).get(j);
            }
        }
        return returnArray;
    }
}
