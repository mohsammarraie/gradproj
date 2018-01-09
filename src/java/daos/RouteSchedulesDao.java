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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.RouteSchedules;
import models.RouteStops;
import models.Stops;
/**
 *
 * @author MOH
 */
public class RouteSchedulesDao extends ConnectionDao {

    public ArrayList<RouteSchedules> buildRouteSchedules(int routeId) throws Exception {
        ArrayList<RouteSchedules> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM"
                    + " BUSES.ROUTES_SCHEDULES"
                    + " WHERE"
                    + " BUSES.ROUTES_SCHEDULES.ROUTE_ID=?";
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
      
         private Stops populateRouteScheduleStop(ResultSet rs) throws SQLException {
            Stops stops = new Stops();
            stops.setTime(rs.getTimestamp("TIME"));
            stops.setStopId(rs.getInt("STOP_ID"));
            stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
            stops.setStopNameAr(rs.getString("STOP_NAME_AR"));
            stops.setScheduleId(rs.getInt("SCHEDULE_ID"));
            return stops;
        } 
        
        
        private RouteSchedules populateRouteSchedules(ResultSet rs) throws SQLException {
            
            RouteSchedules routeSchedules = new RouteSchedules();
            routeSchedules.setRouteId(rs.getInt("ROUTE_ID"));
            routeSchedules.setRouteScheduleActive(rs.getInt("ROUTE_SCHEDULE_ACTIVE"));
            routeSchedules.setScheduleId(rs.getInt("SCHEDULE_ID"));
            return routeSchedules;
        } 
    
        public void deleteRouteSchedules(int scheduleId, int routeId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.ROUTES_SCHEDULES WHERE ROUTE_ID=? AND SCHEDULE_ID=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            
            ps.setInt(1, routeId);
            ps.setInt(2, scheduleId);
            ps.executeUpdate();

            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public void deleteRouteScheduleStops(int scheduleId, int routeId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.ROUTES_STOPS_SCHEDULES WHERE ROUTE_ID=? AND SCHEDULE_ID=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            
            ps.setInt(1, routeId);
            ps.setInt(2, scheduleId);
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
              
            String sql= "SELECT ROUTES_SCHEDULES.*,ROUTES_STOPS_SCHEDULES.*,ROUTES_STOPS.*, STOP_NAME_EN,STOP_NAME_AR FROM" +
                        " BUSES.ROUTES," +
                        " BUSES.STOPS," +
                        " BUSES.ROUTES_STOPS," +
                        " BUSES.ROUTES_SCHEDULES," +
                        " BUSES.ROUTES_STOPS_SCHEDULES" +
                        " WHERE" +
                        " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTES_STOPS.ROUTE_ID" +
                        " AND" +
                        " BUSES.STOPS.STOP_ID = BUSES.ROUTES_STOPS.STOP_ID" +
                        " AND" +
                        " BUSES.ROUTES_STOPS_SCHEDULES.STOP_ID = BUSES.ROUTES_STOPS.STOP_ID" +
                        " AND" +
                        " BUSES.ROUTES_STOPS_SCHEDULES.ROUTE_SCHEDULE_ID =BUSES.ROUTES_SCHEDULES.ROUTE_SCHEDULE_ID"+
                        " AND" +
                        " BUSES.ROUTES.ROUTE_ID=?"+
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
        
  
        public ArrayList<Stops> buildRouteScheduleStops(int routeId) throws Exception {
            ArrayList<Stops> list = new ArrayList<>();
            Connection conn = getConnection();

            try {            
                String sql= "SELECT STOPS.STOP_ID, STOP_NAME_AR, STOP_NAME_EN, TIME, SCHEDULE_ID"
                        + " FROM STOPS JOIN ROUTES_STOPS_SCHEDULES ON"
                        + " STOPS.STOP_ID = ROUTES_STOPS_SCHEDULES.STOP_ID"
                        + " WHERE ROUTE_ID=?"
                           ;

                PreparedStatement ps = conn.prepareStatement(sql);            
                ps.setInt(1, routeId);
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
      
        
//      public void insertRouteSchedule(int stopId, int routeId,int routeScheduleId, RouteSchedules routeSchedules ) throws Exception {                
//        try {
//            Connection conn = getConnection();
//
//            String sql = "INSERT INTO BUSES.ROUTES_STOPS_SCHEDULES(ROUTE_SCHEDULE_ID, TIME, STOP_ID)"
//                    + " VALUES ((select max(ROUTE_SCHEDULE_ID) from BUSES.ROUTES_STOPS_SCHEDULES)+1,?,?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            
//            ps.setTimestamp(1, routeSchedules.getScheduleTime());
//            ps.setInt(2, stopId);
//          
//
//            ps.executeUpdate();
//            ps.close();
//            
//            String sql2 = "INSERT INTO BUSES.ROUTES_SCHEDULES(ROUTE_SCHEDULE_ID, SCHEDULE_ID, ROUTE_ID)"
//                    + " VALUES ((select max(ROUTE_SCHEDULE_ID) from BUSES.ROUTES_SCHEDULES)+1,"
//                    + "(select max(SCHEDULE_ID) from BUSES.ROUTES_SCHEDULES)+1,?)";
//            PreparedStatement ps2 = conn.prepareStatement(sql2);
//          
//            
//            ps2.setInt(1, routeId);
//          
//
//            ps2.executeUpdate();
//            ps2.close();
//            
//        } catch (SQLException e) {
//            throw new SQLException(e.getMessage());
//        }
//    }
//        public void updateRouteSchedule(RouteSchedules routeSchedules, int stopId, int routeId, int scheduleId) throws Exception {
//        try {
//            Connection conn = getConnection();
//
//            String sql = "UPDATE BUSES.ROUTES_STOPS_SCHEDULES SET"
//                    + " TIME=?"
//                    + " WHERE STOP_ID=?"
//                    + " AND"
//                    + " ROUTE_SCHEDULE_ID=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            
//            ps.setTimestamp(1, routeSchedules.getScheduleTime());
//            ps.setInt(2, stopId);
//            ps.setInt(3, scheduleId);
//            ps.executeUpdate();
//            ps.close();
//            
//        } catch (SQLException e) {
//            throw new SQLException(e.getMessage());
//        }
//    }
}
