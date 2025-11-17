/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author spenc
 */
import java.sql.*;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static ResultSet resultSet;

    public static void addStudent(StudentEntry student) {
        connection = DBConnection.getConnection();
        try {
            addStudent = connection.prepareStatement("INSERT INTO student (studentid, firstname, lastname) VALUES (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();
        try {
            getAllStudents = connection.prepareStatement("SELECT * FROM student ORDER BY studentid");
            resultSet = getAllStudents.executeQuery();
            while (resultSet.next()) {
                students.add(new StudentEntry(
                    resultSet.getString("studentid"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
