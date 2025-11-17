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
import java.sql.Timestamp;
import java.util.ArrayList;

public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static ResultSet resultSet;

    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            addScheduleEntry = connection.prepareStatement(
                "INSERT INTO Schedule (Semester, StudentID, CourseCode, Status, Timestamp) VALUES (?, ?, ?, ?, ?)"
            );
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getStudentID());
            addScheduleEntry.setString(3, entry.getCourseCode());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp()); // <-- if you have timestamp in ScheduleEntry
            addScheduleEntry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        System.out.println("Fetching schedule for semester: " + semester + " student: " + studentID);
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();

        try {
            getScheduleByStudent = connection.prepareStatement(
                "SELECT CourseCode, Status, Timestamp FROM Schedule WHERE Semester = ? AND StudentID = ?"
            );
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();

            while (resultSet.next()) {
                String courseCode = resultSet.getString("CourseCode");
                String status = resultSet.getString("Status");
                Timestamp timestamp = resultSet.getTimestamp("Timestamp");

                ScheduleEntry entry = new ScheduleEntry(semester, courseCode, studentID, status, timestamp);

                schedule.add(entry);
}

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return schedule;
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        try {
            getScheduleByCourse = connection.prepareStatement(
                "SELECT Semester, CourseCode, StudentID, Status, Timestamp FROM Schedule WHERE Semester = ? AND CourseCode = ? ORDER BY Status ASC, Timestamp"
            );
            getScheduleByCourse.setString(1, semester);
            getScheduleByCourse.setString(2, courseCode);
            resultSet = getScheduleByCourse.executeQuery();
            while (resultSet.next()) {
                schedule.add(new ScheduleEntry(
                    resultSet.getString("Semester"),
                    resultSet.getString("CourseCode"),
                    resultSet.getString("StudentID"),
                    resultSet.getString("Status"),
                    resultSet.getTimestamp("Timestamp")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropScheduleByCourse = connection.prepareStatement(
                "DELETE FROM Schedule WHERE Semester = ? AND CourseCode = ?"
            );
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
    ArrayList<ScheduleEntry> list = new ArrayList<>();
    connection = DBConnection.getConnection();
    try {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Schedule WHERE Semester = ? AND CourseCode = ? AND Status = 'S'");
        ps.setString(1, semester);
        ps.setString(2, courseCode);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new ScheduleEntry(
                rs.getString("Semester"),
                rs.getString("CourseCode"),
                rs.getString("StudentID"),
                rs.getString("Status"),
                rs.getTimestamp("Timestamp")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
}

