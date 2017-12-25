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
    
      public ArrayList<Routes> buildRoutes() throws Exception {
        ArrayList<Routes> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.ROUTES ORDER BY ROUTE_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateRoutes(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public HashMap<Integer, Routes> buildRoutesMap() throws Exception {
        HashMap<Integer, Routes> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.ROUTES ORDER BY ROUTE_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                Routes routesInfo = populateRoutes(rs);
                map.put(routesInfo.getRouteId(), routesInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private Routes populateRoutes(ResultSet rs) throws SQLException {
        Routes routes = new Routes();
        
        routes.setRouteId(rs.getInt("ROUTE_ID"));
        routes.setSourceEn(rs.getString("SOURCE_EN"));
        routes.setSourceAr(rs.getString("SOURCE_AR"));
        routes.setDestinationEn(rs.getString("DESTINATION_EN"));                  
        routes.setDestinationAr(rs.getString("DESTINATION_AR"));                  
        routes.setRouteCode(rs.getString("ROUTE_CODE")); 
        routes.setRouteActive(rs.getInt("ROUTE_ACTIVE")); 
        return routes;
    } 
    public void insertRoute(Routes routes) throws Exception {                
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.ROUTES (ROUTE_ID,"
                    + " SOURCE_EN,"
                    + " SOURCE_AR,"
                    + " DESTINATION_EN,"
                    + " DESTINATION_AR,"
                    + " ROUTE_CODE,"
                    + " ROUTE_ACTIVE)"
                    + " VALUES ((select max(ROUTE_ID) from ROUTES)+1,?,?,?,?,?,?)";
             PreparedStatement ps = conn.prepareStatement(sql); 
            
             ps.setString(1, routes.getSourceEn());
             ps.setString(2, routes.getSourceAr());
             ps.setString(3, routes.getDestinationEn());
             ps.setString(4, routes.getDestinationAr());
             ps.setString(5, routes.getRouteCode());
             ps.setInt(6, routes.getRouteActive());
             ps.executeUpdate();
             ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateRoute(Routes routes) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.ROUTES SET"
                    + " SOURCE_EN=?,"
                    + " SOURCE_AR=?,"
                    + " DESTINATION_EN=?,"
                    + " DESTINATION_AR=?,"
                    + " ROUTE_CODE=?,"
                    + " ROUTE_ACTIVE=?"
                    + " WHERE ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
             ps.setString(1, routes.getSourceEn());
             ps.setString(2, routes.getSourceAr());
             ps.setString(3, routes.getDestinationEn());
             ps.setString(4, routes.getDestinationAr());
             ps.setString(5, routes.getRouteCode());
             ps.setInt(6, routes.getRouteActive());
             ps.setInt(7, routes.getRouteId());

            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteRoute(int routeId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.ROUTES WHERE ROUTE_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public Routes getRoutes(int routeId) throws Exception {
        try {   
            Routes routes = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.ROUTES"
                    + " WHERE ROUTE_ID=?";   
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, routeId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                routes = populateRoutes(rs);
                routes.setSourceEn(rs.getString("SOURCE_EN"));
                routes.setSourceAr(rs.getString("SOURCE_AR"));
                routes.setDestinationEn(rs.getString("DESTINATION_EN"));
                routes.setDestinationAr(rs.getString("DESTINATION_AR"));
                routes.setRouteCode(rs.getString("ROUTE_CODE")); 
                routes.setRouteActive(rs.getInt("ROUTE_ACTIVE")); 
            }

            rs.close();
            ps.close();
            
            return routes;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
}
