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
import java.util.ArrayList;
import java.util.HashMap;
import models.RouteSchedules;
import models.RouteStops;
import models.Stops;
/**
 *
 * @author MOH
 */
public class RouteSchedulesDao extends ConnectionDao{
      public ArrayList<RouteSchedules> buildRouteSchedules(int routeId) throws Exception {
        ArrayList<RouteSchedules> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql= "SELECT ROUTES_SCHEDULES.*,ROUTE_STOPS.*, STOP_NAME_EN,STOP_NAME_AR FROM" +
                        " BUSES.ROUTES," +
                        " BUSES.STOPS," +
                        " BUSES.ROUTE_STOPS," +
                        " BUSES.ROUTES_SCHEDULES" +
                        " WHERE" +
                        " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTE_STOPS.ROUTE_ID" +
                        " AND" +
                        " BUSES.STOPS.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID" +
                        " AND" +
                        " BUSES.ROUTES_SCHEDULES.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID" +
                        " AND" +
                        " BUSES.ROUTES.ROUTE_ID=?" + 
                        " ORDER BY STOP_ORDER";
                     
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateRouteSchedules(rs));
             
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }       
    }
      
        private RouteSchedules populateRouteSchedules(ResultSet rs) throws SQLException {
            RouteSchedules routeSchedules = new RouteSchedules();
            routeSchedules.setTime(rs.getTimestamp("TIME"));
            routeSchedules.setScheduleId(rs.getInt("SCHEDULE_ID"));
            routeSchedules.setRouteScheduleId(rs.getInt("ROUTE_SCHEDULE_ID"));
            routeSchedules.setRouteId(rs.getInt("ROUTE_ID"));
            routeSchedules.setStopId(rs.getInt("STOP_ID"));
            routeSchedules.setStopNameEn(rs.getString("STOP_NAME_EN"));
            routeSchedules.setStopNameAr(rs.getString("STOP_NAME_AR"));
            routeSchedules.setStopOrder(rs.getInt("STOP_ORDER"));
//            stopsInfo.setRouteCode(rs.getString("ROUTE_CODE"));
//            stopsInfo.setSourceEn(rs.getString("SOURCE_EN"));
//            stopsInfo.setSourceAr(rs.getString("SOURCE_AR"));
//            stopsInfo.setDestinationEn(rs.getString("DESTINATION_EN"));                  
//            stopsInfo.setDestinationAr(rs.getString("DESTINATION_AR"));                  
//            stopsInfo.setActive(rs.getInt("ACTIVE")); 
            return routeSchedules;
        } 
    
        public void deleteRouteScheduleTime(int scheduleId, int routeId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "UPDATE BUSES.ROUTES_SCHEDULES SET TIME = NULL WHERE SCHEDULE_ID=? AND ROUTE_ID=?"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, scheduleId);
            ps.setInt(2, routeId);
            ps.executeUpdate();

            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
          
      public void insertRouteSchedule(int stopId, int routeId,int scheduleId, Timestamp time ) throws Exception {                
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.ROUTES_SCHEDULES(TIME)"
                    + " VALUES (?) WHERE SCHEDULE_ID =? AND STOP_ID=? AND ROUTE_ID=?";
             PreparedStatement ps = conn.prepareStatement(sql); 
            ps.setTimestamp(1, time);
            ps.setInt(2, scheduleId);
            ps.setInt(3, stopId);
            ps.setInt(4, routeId);

             ps.executeUpdate();
             ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateRouteSchedule(Timestamp time,int stopId, int routeId) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.ROUTES_SCHEDULES SET"
                    + " TIME=?"
                    + " WHERE STOP_ID=? AND ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setTimestamp(1, time);
            ps.setInt(2, stopId);
            ps.setInt(3, routeId);
            
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
        public RouteSchedules getRouteSchedules(int routeId) throws Exception {
        try {   
            RouteSchedules routeSchedules = null;
            Connection conn = getConnection();
            
//            String sql = "SELECT * FROM BUSES.STOPS JOIN"
//                    + " BUSES.ROUTE_STOPS ON BUSES.STOPS.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID" 
//                    + " JOIN BUSES.ROUTES ON "
//                    + " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTE_STOPS.ROUTE_ID"
//                    + " WHERE BUSES.ROUTE_STOPS.STOP_ID=?";  
            String sql= "SELECT ROUTES_SCHEDULES.*,ROUTE_STOPS.*, STOP_NAME_EN,STOP_NAME_AR FROM" +
                        " BUSES.ROUTES," +
                        " BUSES.STOPS," +
                        " BUSES.ROUTE_STOPS," +
                        " BUSES.ROUTES_SCHEDULES" +
                        " WHERE" +
                        " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTE_STOPS.ROUTE_ID" +
                        " AND" +
                        " BUSES.STOPS.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID" +
                        " AND" +
                        " BUSES.ROUTES_SCHEDULES.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID" +
                        " AND" +
                        " BUSES.ROUTES.ROUTE_ID=?" + 
                        " ORDER BY STOP_ORDER";   
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, routeId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                routeSchedules = populateRouteSchedules(rs);

            }

            rs.close();
            ps.close();
            
            return routeSchedules;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
