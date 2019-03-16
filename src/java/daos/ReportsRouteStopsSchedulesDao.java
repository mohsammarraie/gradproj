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
import models.Stop;
/**
 *
 * @author MOH
 */
public class ReportsRouteStopsSchedulesDao extends ConnectionDao {
    
       public ArrayList<Stop> buildReportsRouteStopsSchedules(int tripId) throws Exception {
        ArrayList<Stop> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT STOPS.STOP_ID, STOP_NAME_AR, STOP_NAME_EN, TIME, TRIPS.SCHEDULE_ID"
                    + " FROM BUSES.STOPS, BUSES.ROUTES_STOPS_SCHEDULES, BUSES.TRIPS"
                    + " WHERE BUSES.STOPS.STOP_ID = BUSES.ROUTES_STOPS_SCHEDULES.STOP_ID"
                    + " AND BUSES.TRIPS.ROUTE_ID = BUSES.ROUTES_STOPS_SCHEDULES.ROUTE_ID"
                    + " AND BUSES.TRIPS.SCHEDULE_ID = BUSES.ROUTES_STOPS_SCHEDULES.SCHEDULE_ID"
                    + " AND BUSES.TRIPS.TRIP_ID  = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tripId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateRouteScheduleStop(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
       
    private Stop populateRouteScheduleStop(ResultSet rs) throws SQLException {
        Stop stops = new Stop();
        stops.setTime(rs.getTimestamp("TIME"));
        stops.setStopId(rs.getInt("STOP_ID"));
        stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stops.setStopNameAr(rs.getString("STOP_NAME_AR"));
        stops.setScheduleId(rs.getInt("SCHEDULE_ID"));
        return stops;
    }
    
}
