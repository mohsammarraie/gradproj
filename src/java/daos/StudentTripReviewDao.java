/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.StudentTripReview;
/**
 *
 * @author MOH
 */
public class StudentTripReviewDao extends ConnectionDao{
    
      public ArrayList<StudentTripReview> buildStudentTripReviews(int tripId) throws Exception {
        ArrayList<StudentTripReview> list = new ArrayList<>();
        Connection conn = getConnection();

        try {


            String sql = "SELECT * FROM STUDENT_TRIP_REVIEWS"
                    + " WHERE TRIP_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tripId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateAvailableBusesDrivers(rs));
            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private StudentTripReview populateAvailableBusesDrivers(ResultSet rs) throws SQLException {
        StudentTripReview studentTripReview = new StudentTripReview();

        studentTripReview.setReviewId(rs.getInt("REVIEW_ID"));
        studentTripReview.setStudentId(rs.getString("STUDENT_ID"));
        studentTripReview.setReviewText(rs.getString("REVIEW_TEXT"));
        studentTripReview.setTripId(rs.getInt("TRIP_ID"));
        studentTripReview.setRating1(rs.getInt("RATING_1"));
        studentTripReview.setRating2(rs.getInt("RATING_2"));
        studentTripReview.setRating3(rs.getInt("RATING_3"));
        studentTripReview.setAvgRating(rs.getInt("AVG_RATING"));

        return studentTripReview;
    }

    
    
    public void insertStudentTripReview(StudentTripReview studentTripReview) throws Exception {
        try {
            Connection conn = getConnection();

            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.STUDENT_TRIP_REVIEWS";
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
            
            studentTripReview.setAvgRating((studentTripReview.getRating1()+studentTripReview.getRating2()+studentTripReview.getRating3())/3);
            
            
            String sql = "INSERT INTO BUSES.STUDENT_TRIP_REVIEWS (REVIEW_ID,"
                    + " TRIP_ID,"
                    + " STUDENT_ID,"
                    + " RATING_1,"
                    + " RATING_2,"
                    + " RATING_3,"
                    + " AVG_RATING,"
                    + " REVIEW_TEXT)"
                    + " VALUES ((select " + countOrMax + "(REVIEW_ID) from BUSES.STUDENT_TRIP_REVIEWS)+1,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, studentTripReview.getTripId());
            ps.setString(2, studentTripReview.getStudentId());
            ps.setInt(3, studentTripReview.getRating1());
            ps.setInt(4, studentTripReview.getRating2());
            ps.setInt(5, studentTripReview.getRating3());
            ps.setInt(6, studentTripReview.getAvgRating());
            ps.setString(7, studentTripReview.getReviewText());
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      public boolean checkStudentReview(int tripId, String studentId) {
        boolean flag = false;
        try {

            int i = 0;
            Connection conn = getConnection();

            String sql = "SELECT count(*) as ROW_COUNTER FROM BUSES.STUDENT_TRIP_REVIEWS"
                    + " WHERE"
                    + " TRIP_ID=?"
                    + " AND"
                    + " STUDENT_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tripId);
            ps.setString(2, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = rs.getInt("ROW_COUNTER");
            }
            rs.close();
            ps.close();

            if (i > 0) {
                flag = true;
            } else {
                flag = false;
            }

        } catch (Exception ex) {
            Logger.getLogger(RouteStopsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    
}
