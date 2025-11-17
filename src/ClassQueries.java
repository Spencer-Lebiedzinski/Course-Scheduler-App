/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author spenc
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement dropClass;
    private static ResultSet resultSet;

    public static void addClass(ClassEntry classEntry) {
        connection = DBConnection.getConnection();
        try {
            addClass = connection.prepareStatement(
                "INSERT INTO Class (semester, coursecode, seats) VALUES (?, ?, ?)"
            );
            addClass.setString(1, classEntry.getSemester());
            addClass.setString(2, classEntry.getCourseCode());
            addClass.setInt(3, classEntry.getSeats());
            addClass.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ClassEntry> getAllClasses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<ClassEntry> classList = new ArrayList<>();

        try {
            PreparedStatement getClasses = connection.prepareStatement(
                "SELECT c.CourseCode, ce.Description, c.Seats " +
                "FROM Class c JOIN Course ce ON c.CourseCode = ce.CourseCode " +
                "WHERE c.Semester = ?"
            );
            getClasses.setString(1, semester);
            resultSet = getClasses.executeQuery();

            while (resultSet.next()) {
                String courseCode = resultSet.getString("CourseCode");
                String description = resultSet.getString("Description");
                int seats = resultSet.getInt("Seats");

                // Create a ClassEntry and set description separately if needed
                ClassEntry entry = new ClassEntry(semester, courseCode, seats);
                entry.setDescription(description); // assuming you have a setter
                classList.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classList;
    }
    
    public static void dropClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropClass = connection.prepareStatement("DELETE FROM Class WHERE semester = ? AND coursecode = ?");
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            dropClass.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

