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
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import models.Report;
/**
 *
 * @author MOH
 */
public class RouteSchedulesViewDao  extends ConnectionDao{
    
      public ArrayList<Report> buildRouteSchedulesView() throws Exception {
        ArrayList<Report> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM BUSES.ALL_SCHEDULES_VIEW";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateRouteSchedulesView(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Report populateRouteSchedulesView(ResultSet rs) throws SQLException {

        Report report = new Report();

        report.setDepartureTime(rs.getTimestamp("DEPARTURE_TIME"));
        report.setArrivalTime(rs.getTimestamp("ARRIVAL_TIME"));
        report.setSourceEn(rs.getString("SOURCE_EN"));
        report.setSourceAr(rs.getString("SOURCE_AR"));
        report.setDestinationEn(rs.getString("DESTINATION_EN"));
        report.setDestinationAr(rs.getString("DESTINATION_AR"));
        report.setScheduleId(rs.getInt("SCHEDULE_ID"));
        report.setRouteCode(rs.getString("ROUTE_CODE"));
        report.setRouteId(rs.getInt("ROUTE_ID"));

        return report;
    }
     public ArrayList<Report> buildRouteSchedulesBus() throws Exception {
        ArrayList<Report> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM BUSES.ALL_SCHEDULES_BUSES_VIEW";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateRouteSchedulesBus(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
     
         private Report populateRouteSchedulesBus(ResultSet rs) throws SQLException {

        Report report = new Report();

        report.setDepartureTime(rs.getTimestamp("DEPARTURE_TIME"));
        report.setArrivalTime(rs.getTimestamp("ARRIVAL_TIME"));
        report.setBusId(rs.getInt("BUS_ID"));
        report.setSourceEn(rs.getString("SOURCE_EN"));
        report.setSourceAr(rs.getString("SOURCE_AR"));
        report.setDestinationEn(rs.getString("DESTINATION_EN"));
        report.setDestinationAr(rs.getString("DESTINATION_AR"));
        report.setScheduleId(rs.getInt("SCHEDULE_ID"));
        report.setRouteCode(rs.getString("ROUTE_CODE"));
        report.setRouteId(rs.getInt("ROUTE_ID"));
        report.setLicenseNumber(rs.getString("LICENSE_NUMBER"));

        return report;
    }
         
         
    public ArrayList<Report> buildResultAllRouteSchedulesFilter(int scheduleId ,int routeId, Date departureTime, Date arrivalTime) throws Exception {
        ArrayList<Report> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT * FROM BUSES.ALL_SCHEDULES_VIEW";
            if ( scheduleId>0 || routeId > 0  || departureTime != null || arrivalTime != null) {
                sql += " WHERE ";
            }
              if (scheduleId > 0) {
                sql += "SCHEDULE_ID=" + scheduleId;
                if (routeId > 0 || departureTime != null || arrivalTime != null) {
                    sql += " AND ";
                }
            }
            
            if (routeId > 0) {
                sql += "ROUTE_ID=" + routeId;
                if ( departureTime != null || arrivalTime != null) {
                    sql += " AND ";
                }
            }
         
         
            if (departureTime != null) {
                Timestamp timeStampDepartureTime = new Timestamp(departureTime.getTime());
                Format formatt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String dateToString = formatt.format(timeStampDepartureTime);
                String[] splitDate = dateToString.split(" ");

                sql += "DEPARTURE_TIME='" + "01-JAN-70 " + splitDate[1] + "'";
                if (arrivalTime != null) {
                    sql += " AND ";
                }

            }
            if (arrivalTime != null) {
                Timestamp timeStampArrivalTime = new Timestamp(arrivalTime.getTime());
                Format formatt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String dateToString = formatt.format(timeStampArrivalTime);
                String[] splitDate = dateToString.split(" ");
                sql += "ARRIVAL_TIME='" + "01-JAN-70 " + splitDate[1] + "'";
               
            }
      

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateRouteSchedulesView(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
}
