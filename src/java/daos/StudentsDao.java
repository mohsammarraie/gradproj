package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import models.Students;

/**
 *
 * @author Taha Al_khaffaf
 */

public class StudentsDao extends ConnectionDao {

        public ArrayList<Students> buildStudents() 
            throws Exception {
        ArrayList<Students> list = new ArrayList<>();        
        
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

        private Students populateStudents(ResultSet rs) throws SQLException {
        Students students = new Students();
        
        students.setStudentId(rs.getString("STUDENT_ID")); 
        students.setFirstNameEn(rs.getString("FIRST_NAME_EN")); 
        students.setLastNameEn(rs.getString("LAST_NAME_EN"));
        students.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
        students.setLastNameAr(rs.getString("LAST_NAME_AR"));
             
        return students;
    }
        
    public Students getStudents(String StudentId) throws Exception {
        try {   
            Students students = null;
            Connection conn = getConnection();
            
               String sql = "SELECT * FROM BUSES.STUDENTS"
                    + " WHERE STUDENT_ID=?";                      
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setString(1, StudentId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                students = populateStudents(rs);
                students.setStudentId(rs.getString("STUDENT_ID")); 
                students.setFirstNameEn(rs.getString("FIRST_NAME_EN")); 
                students.setLastNameEn(rs.getString("LAST_NAME_EN"));
                students.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
                students.setLastNameAr(rs.getString("LAST_NAME_AR"));
            }

            rs.close();
            ps.close();
            
            return students;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
        public void insertStudent(Students students) throws Exception {                
        try {
            Connection conn = getConnection();
            
            String sql = "INSERT INTO BUSES.STUDENTS (STUDENT_ID,"
                    + " FIRST_NAME_EN,"
                    + " LAST_NAME_EN,"
                    + " FIRST_NAME_AR,"
                    + " LAST_NAME_AR)"
                    + " VALUES (?,?,?,?,?)";
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
    
    public void updateStudent(Students students) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.STUDENTS SET"
             
                    + " FIRST_NAME_EN=?,"
                    + " LAST_NAME_EN=?,"
                    + " FIRST_NAME_AR=?"
                    + " LAST_NAME_AR=?"
                    + " WHERE STUDENT_ID=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, students.getFirstNameEn());
            ps.setString(2, students.getLastNameEn());
            ps.setString(3, students.getFirstNameAr());
            ps.setString(4, students.getLastNameAr());
            ps.setString(5, students.getStudentId());

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