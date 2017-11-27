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
import models.Routes;
/**
 *
 * @author MOH
 */
public class RoutesDao extends ConnectionDao {
    
      public ArrayList<Routes> buildRoutesInfo() throws Exception {
        ArrayList<Routes> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.ROUTES ORDER BY ROUTE_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateRoutesInfo(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public HashMap<Integer, Routes> buildRoutesInfoMap() throws Exception {
        HashMap<Integer, Routes> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.ROUTES ORDER BY ROUTE_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                Routes routesInfo = populateRoutesInfo(rs);
                map.put(routesInfo.getRouteID(), routesInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private Routes populateRoutesInfo(ResultSet rs) throws SQLException {
        Routes routesInfo = new Routes();
        
        routesInfo.setRouteID(rs.getInt("ROUTE_ID"));
        routesInfo.setSourceEn(rs.getString("SOURCE_EN"));
        routesInfo.setSourceAr(rs.getString("SOURCE_AR"));
        routesInfo.setDestinationEn(rs.getString("DESTINATION_EN"));                  
        routesInfo.setDestinationAr(rs.getString("DESTINATION_AR"));                  
        routesInfo.setRouteCode(rs.getString("ROUTE_CODE")); 
        routesInfo.setActive(rs.getString("ACTIVE")); 
        return routesInfo;
    } 
    public void insertRoute(Routes routesInfo) throws Exception {                
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.ROUTES (ROUTE_ID,"
                    + " SOURCE_EN,"
                    + " SOURCE_AR,"
                    + " DESTINATION_EN,"
                    + " DESTINATION_AR,"
                    + " ROUTE_CODE,"
                    + " ACTIVE)"
                    + " VALUES ((select max(ROUTE_ID) from ROUTES_INFO)+1,?,?,?,?,?,?)";
             PreparedStatement ps = conn.prepareStatement(sql); 
            
             ps.setString(1, routesInfo.getSourceEn());
             ps.setString(2, routesInfo.getSourceAr());
             ps.setString(3, routesInfo.getDestinationEn());
             ps.setString(4, routesInfo.getDestinationAr());
             ps.setString(5, routesInfo.getRouteCode());
             ps.setString(6, routesInfo.getActive());
             ps.executeUpdate();
             ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateRoute(Routes routesInfo) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.ROUTES SET"
                    + " SOURCE_EN=?,"
                    + " SOURCE_AR=?,"
                    + " DESTINATION_EN=?,"
                    + " DESTINATION_AR=?,"
                    + " ROUTE_CODE=?,"
                    + " ACTIVE=?"
                    + " WHERE ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
             ps.setString(1, routesInfo.getSourceEn());
             ps.setString(2, routesInfo.getSourceAr());
             ps.setString(3, routesInfo.getDestinationEn());
             ps.setString(4, routesInfo.getDestinationAr());
             ps.setString(5, routesInfo.getRouteCode());
             ps.setString(6, routesInfo.getActive());
             ps.setInt(7, routesInfo.getRouteID());

            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteRoute(int routeID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.ROUTES WHERE ROUTE_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeID);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public Routes getRoutesInfo(int routeID) throws Exception {
        try {   
            Routes routesInfo = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.ROUTES"
                    + " WHERE ROUTE_ID=?";   
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, routeID);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                routesInfo = populateRoutesInfo(rs);
                routesInfo.setSourceEn(rs.getString("SOURCE_EN"));
                routesInfo.setSourceAr(rs.getString("SOURCE_AR"));
                routesInfo.setDestinationEn(rs.getString("DESTINATION_EN"));
                routesInfo.setDestinationAr(rs.getString("DESTINATION_AR"));
                routesInfo.setRouteCode(rs.getString("ROUTE_CODE")); 
                routesInfo.setActive(rs.getString("ACTIVE")); 
            }

            rs.close();
            ps.close();
            
            return routesInfo;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
}
