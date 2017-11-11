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
import models.StopsInfo;
/**
 *
 * @author MOH
 */
public class RouteStopsDao extends ConnectionDao{
    
    
     public ArrayList<StopsInfo> buildRouteStopsInfo(int routeID) throws Exception {
        ArrayList<StopsInfo> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT BUSES.STOPS.STOP_ID, STOP_NAME_EN, STOP_NAME_AR from BUSES.STOPS JOIN"
                    + " BUSES.ROUTE_STOPS ON BUSES.STOPS.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID" 
                    + " JOIN BUSES.ROUTES ON "
                    + " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTE_STOPS.ROUTE_ID"
                    + " WHERE BUSES.ROUTES.ROUTE_ID=?";
                                           
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, routeID);
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateRouteStopsInfo(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public HashMap<Integer, StopsInfo> buildRouteStopsInfoMap() throws Exception {
        HashMap<Integer, StopsInfo> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT BUSES.STOPS.STOP_ID, STOP_NAME_EN, STOP_NAME_AR from BUSES.STOPS  JOIN"
                    + " BUSES.ROUTE_STOPS ON BUSES.STOPS.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID" 
                    + " JOIN BUSES.ROUTES ON "
                    + " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTE_STOPS.ROUTE_ID"
                    + "  ORDER BY BUSES.STOPS.STOP_ID";   
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                StopsInfo stopsInfo = populateRouteStopsInfo(rs);
                map.put(stopsInfo.getStopID(), stopsInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private StopsInfo populateRouteStopsInfo(ResultSet rs) throws SQLException {
        StopsInfo stopsInfo = new StopsInfo();
        
        stopsInfo.setStopID(rs.getInt("STOP_ID"));
        stopsInfo.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stopsInfo.setStopNameAr(rs.getString("STOP_NAME_AR"));
     
        return stopsInfo;
    } 
    public void insertRouteStop(StopsInfo stopsInfo) throws Exception {                
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.STOPS  (STOP_ID,"
                    + " STOP_NAME_EN,"
                    + " STOP_NAME_AR)"
                    + " VALUES ((select max(STOP_ID) from BUSES.STOPS)+1,?,?)";
             PreparedStatement ps = conn.prepareStatement(sql); 
            
             ps.setString(1, stopsInfo.getStopNameEn());
             ps.setString(2, stopsInfo.getStopNameAr());
           
        
             
             ps.executeUpdate();
             ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateRouteStop(int routeStopID,int stopID, int routeID) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.ROUTE_STOPS SET"
                    + " STOP_ID=?"
                    + " WHERE STOP_ID=? AND ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, routeStopID);
            ps.setInt(2, stopID);
            ps.setInt(3, routeID);
            
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteRouteStop(int stopID, int routeID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.ROUTE_STOPS WHERE STOP_ID=? AND ROUTE_ID=?"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, stopID);
            ps.setInt(2, routeID);
            ps.executeUpdate();

            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public StopsInfo getRouteStopsInfo(int stopID) throws Exception {
        try {   
            StopsInfo stopsInfo = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.STOPS JOIN"
                    + " BUSES.ROUTE_STOPS ON BUSES.STOPS.STOP_ID = BUSES.ROUTE_STOPS.STOP_ID" 
                    + " JOIN BUSES.ROUTES ON "
                    + " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTE_STOPS.ROUTE_ID"
                    + " WHERE BUSES.ROUTE_STOPS.STOP_ID=?";  
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, stopID);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                stopsInfo = populateRouteStopsInfo(rs);
                stopsInfo.setStopNameEn(rs.getString("STOP_NAME_EN"));
                stopsInfo.setStopNameAr(rs.getString("STOP_NAME_AR"));

            }

            rs.close();
            ps.close();
            
            return stopsInfo;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
}
