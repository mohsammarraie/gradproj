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
            String sql= "SELECT ROUTES_SCHEDULES.*, STOP_NAME_EN,STOP_NAME_AR FROM" +
                        " BUSES.ROUTES," +
                        " BUSES.STOPS," +
                        " BUSES.ROUTES_SCHEDULES" +
                        " WHERE" +
                        " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTES_SCHEDULES.ROUTE_ID" +
                        " AND" +
                        " BUSES.STOPS.STOP_ID = BUSES.ROUTES_SCHEDULES.STOP_ID" +
                        " AND" +
                        " BUSES.ROUTES.ROUTE_ID=?";
                     
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
//            stopsInfo.setStopOrder(rs.getInt("STOP_ORDER"));
//            stopsInfo.setRouteCode(rs.getString("ROUTE_CODE"));
//            stopsInfo.setSourceEn(rs.getString("SOURCE_EN"));
//            stopsInfo.setSourceAr(rs.getString("SOURCE_AR"));
//            stopsInfo.setDestinationEn(rs.getString("DESTINATION_EN"));                  
//            stopsInfo.setDestinationAr(rs.getString("DESTINATION_AR"));                  
//            stopsInfo.setActive(rs.getInt("ACTIVE")); 
            return routeSchedules;
        } 
    
        public void deleteRouteSchedule(int scheduleId, int routeId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.ROUTES_SCHEDULES WHERE SCHEDULE_ID=? AND ROUTE_ID=?"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, scheduleId);
            ps.setInt(2, routeId);
            ps.executeUpdate();

            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
}
