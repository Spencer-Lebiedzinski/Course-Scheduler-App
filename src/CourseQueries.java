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

public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static ResultSet resultSet;

    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        try {
            addCourse = connection.prepareStatement(
                "INSERT INTO Course (CourseCode, Description) VALUES (?, ?)"
            );
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());
            addCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<String> getAllCourseCodes() {
        ArrayList<String> courseCodes = new ArrayList<>();
        connection = DBConnection.getConnection();

        try {
            getAllCourseCodes = connection.prepareStatement(
                "SELECT CourseCode FROM Course"
            );
            resultSet = getAllCourseCodes.executeQuery();

            while (resultSet.next()) {
                courseCodes.add(resultSet.getString("CourseCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseCodes;
    }
}
