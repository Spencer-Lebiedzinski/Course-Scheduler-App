/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author acv
 */
public class DBConnection {
    private static Connection connection;
    private static final String user = "java";
    private static final String password = "java";
    private static final String database = "jdbc:derby://localhost:1527/CourseScedulerDBSpencersml7204";
    private static final String clientDriver = "org.apache.derby.client.ClientAutoloadedDriver";

    public static Connection getConnection()
    {
        if (connection == null)
        {
            try
            {
                Class.forName(clientDriver);
                connection = DriverManager.getConnection(database, user, password);
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
                System.out.println("Could not load Derby client driver.");
                System.exit(1);
            } catch (SQLException e)
            {
                e.printStackTrace();
                System.out.println("Could not open database.");
                System.exit(1);

            }
        }
        return connection;
    }

    
}
