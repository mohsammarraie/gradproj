package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Student;

/**
 *
 * @author Taha Al_khaffaf
 */
public class StudentsDao extends ConnectionDao {

    public ArrayList<Student> buildStudents()
            throws Exception {
        ArrayList<Student> list = new ArrayList<>();

        try {
            Connection conn = getConnection();

            String sql = "SELECT * FROM BUSES.STUDENTS";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateStudents(rs));
            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Student populateStudents(ResultSet rs) throws SQLException {
        Student students = new Student();
        students.setStudentIncId(rs.getInt("STUDENT_INC_ID"));
        students.setStudentId(rs.getString("STUDENT_ID"));
        students.setFirstNameEn(rs.getString("FIRST_NAME_EN"));
        students.setLastNameEn(rs.getString("LAST_NAME_EN"));
        students.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
        students.setLastNameAr(rs.getString("LAST_NAME_AR"));

        return students;
    }

    public Student getStudents(int StudentIncId) throws Exception {
        try {
            Student students = null;
            Connection conn = getConnection();

            String sql = "SELECT * FROM BUSES.STUDENTS"
                    + " WHERE STUDENT_INC_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, StudentIncId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                students = populateStudents(rs);
            }

            rs.close();
            ps.close();

            return students;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void insertStudent(Student students) throws Exception {
        try {
            Connection conn = getConnection();
            
            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.STUDENTS";
            PreparedStatement preparedStm = conn.prepareStatement(query);
            ResultSet resultSet = preparedStm.executeQuery();

            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt("ROW_COUNTER");
            }
            String countOrMax = "";
            if (count > 0) {
                countOrMax = "max";
            } else {
                countOrMax = "count";
            }
            
            String sql = "INSERT INTO BUSES.STUDENTS (STUDENT_ID,"
                    + " FIRST_NAME_EN,"
                    + " LAST_NAME_EN,"
                    + " FIRST_NAME_AR,"
                    + " LAST_NAME_AR,"
                    + " STUDENT_INC_ID)"
                    + " VALUES (?,?,?,?,?,(select " + countOrMax + "(STUDENT_INC_ID) from BUSES.STUDENTS)+1)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, students.getStudentId());
            ps.setString(2, students.getFirstNameEn());
            ps.setString(3, students.getLastNameEn());
            ps.setString(4, students.getFirstNameAr());
            ps.setString(5, students.getLastNameAr());

            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateStudent(Student students, int studentIncId) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.STUDENTS SET"
                    + " FIRST_NAME_EN=?,"
                    + " LAST_NAME_EN=?,"
                    + " FIRST_NAME_AR=?,"
                    + " LAST_NAME_AR=?,"
                    + " STUDENT_ID=?"
                    + " WHERE STUDENT_INC_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, students.getFirstNameEn());
            ps.setString(2, students.getLastNameEn());
            ps.setString(3, students.getFirstNameAr());
            ps.setString(4, students.getLastNameAr());
            ps.setString(5, students.getStudentId());
            ps.setInt(6, studentIncId);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteStudent(String studentId) throws Exception {
        Connection conn = getConnection();

        try {
            String sql = "DELETE FROM BUSES.STUDENTS WHERE STUDENT_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
