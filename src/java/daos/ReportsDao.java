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
import models.Report;

/**
 *
 * @author MOH
 */
public class ReportsDao extends ConnectionDao{
         public ArrayList<Report> buildReports() throws Exception {
        ArrayList<Report> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM REPORTS_VIEW";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateReports(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Report populateReports(ResultSet rs) throws SQLException {

        Report report = new Report();

        report.setDepartureTime(rs.getTimestamp("DEPARTURE_TIME"));
        report.setArrivalTime(rs.getTimestamp("ARRIVAL_TIME"));
        report.setActualDepartureTime(rs.getTimestamp("ACTUAL_DEPARTURE_TIME"));
        report.setActualArrivalTime(rs.getTimestamp("ACTUAL_ARRIVAL_TIME"));
        report.setBusId(rs.getInt("BUS_ID"));
        report.setTripId(rs.getInt("TRIP_ID"));
        report.setSourceEn(rs.getString("SOURCE_EN"));
        report.setSourceAr(rs.getString("SOURCE_AR"));
        report.setDestinationEn(rs.getString("DESTINATION_EN"));
        report.setDestinationAr(rs.getString("DESTINATION_AR"));
        report.setScheduleId(rs.getInt("SCHEDULE_ID"));
        report.setRouteCode(rs.getString("ROUTE_CODE"));
        report.setDriverId(rs.getInt("DRIVER_ID"));
        report.setRouteId(rs.getInt("ROUTE_ID"));
        report.setDriverNameEn(rs.getString("DRIVER_NAME_EN"));
        report.setDriverNameAr(rs.getString("DRIVER_NAME_AR"));
        report.setLicenseNumber(rs.getString("LICENSE_NUMBER"));
        report.setStatusEn(rs.getString("STATUS_EN"));
        report.setStatusAr(rs.getString("STATUS_AR"));
        report.setCapacity(rs.getInt("CAPACITY"));
        report.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
        report.setLastNameAr(rs.getString("LAST_NAME_AR"));
        report.setFirstNameEn(rs.getString("FIRST_NAME_EN"));
        report.setLastNameEn(rs.getString("LAST_NAME_EN"));
        report.setNationalId(rs.getString("NATIONAL_ID"));
        report.setArrivalTimeStatusAr(rs.getString("ARRIVAL_TIME_STATUS_AR"));
        report.setArrivalTimeStatusEn(rs.getString("ARRIVAL_TIME_STATUS_EN"));
        report.setDepartureTimeStatusAr(rs.getString("DEPARTURE_TIME_STATUS_AR"));
        report.setDepartureTimeStatusEn(rs.getString("DEPARTURE_TIME_STATUS_EN"));
        
        return report;
    }
    
}
