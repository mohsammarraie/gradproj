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
      public ArrayList<RouteSchedules> buildRouteSchedulesInfo(int routeID) throws Exception {
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
//                   String sql= "SELECT * FROM BUSES.ROUTE_STOPS,"
//                           + " BUSES.STOPS WHERE ROUTE_ID = ? AND"
//                           + " BUSES.STOPS.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID ORDER BY STOP_ORDER";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, routeID);
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateRouteSchedulesInfo(rs));
             
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }       
    }
      
        private RouteSchedules populateRouteSchedulesInfo(ResultSet rs) throws SQLException {
            RouteSchedules stopsInfo = new RouteSchedules();
            stopsInfo.setTime(rs.getTimestamp("TIME"));
            stopsInfo.setScheduleID(rs.getInt("SCHEDULE_ID"));
            stopsInfo.setRouteScheduleID(rs.getInt("ROUTE_SCHEDULE_ID"));
            stopsInfo.setRouteID(rs.getInt("ROUTE_ID"));
            stopsInfo.setStopID(rs.getInt("STOP_ID"));
            stopsInfo.setStopNameEn(rs.getString("STOP_NAME_EN"));
            stopsInfo.setStopNameAr(rs.getString("STOP_NAME_AR"));
//            stopsInfo.setStopOrder(rs.getInt("STOP_ORDER"));
//            stopsInfo.setRouteCode(rs.getString("ROUTE_CODE"));
//            stopsInfo.setSourceEn(rs.getString("SOURCE_EN"));
//            stopsInfo.setSourceAr(rs.getString("SOURCE_AR"));
//            stopsInfo.setDestinationEn(rs.getString("DESTINATION_EN"));                  
//            stopsInfo.setDestinationAr(rs.getString("DESTINATION_AR"));                  
//            stopsInfo.setActive(rs.getInt("ACTIVE")); 
            return stopsInfo;
        } 
    
        public void deleteRouteSchedule(int scheduleID, int routeID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.ROUTES_SCHEDULES WHERE SCHEDULE_ID=? AND ROUTE_ID=?"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, scheduleID);
            ps.setInt(2, routeID);
            ps.executeUpdate();

            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
}
