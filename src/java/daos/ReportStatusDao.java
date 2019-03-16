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
import models.ReportStatus;
/**
 *
 * @author MOH
 */
public class ReportStatusDao extends ConnectionDao{
    public ArrayList<ReportStatus> buildReportStatus() throws Exception {
        ArrayList<ReportStatus> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM BUSES.REPORT_STATUS";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateReportStatus(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private ReportStatus populateReportStatus(ResultSet rs) throws SQLException {

        ReportStatus reportStatus = new ReportStatus();

        reportStatus.setStatusId(rs.getInt("STATUS_ID"));
        reportStatus.setStatusEn(rs.getString("STATUS_EN"));
        reportStatus.setStatusAr(rs.getString("STATUS_AR"));
        reportStatus.setArrivalTimeStatusAr(rs.getString("ARRIVAL_TIME_STATUS_AR"));
        reportStatus.setArrivalTimeStatusEn(rs.getString("ARRIVAL_TIME_STATUS_EN"));
        reportStatus.setDepartureTimeStatusAr(rs.getString("DEPARTURE_TIME_STATUS_AR"));
        reportStatus.setDepartureTimeStatusEn(rs.getString("DEPARTURE_TIME_STATUS_EN"));
        
        return reportStatus;
    }
    
    
}
