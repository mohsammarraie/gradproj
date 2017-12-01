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
import models.Stops;
/**
 *
 * @author MOH
 */
public class StopsDao extends ConnectionDao {
    
    public ArrayList<Stops> buildStops() throws Exception {
        ArrayList<Stops> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.STOPS ORDER BY STOP_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateStops(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public HashMap<Integer, Stops> buildStopsMap() throws Exception {
        HashMap<Integer, Stops> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.STOPS  ORDER BY STOP_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                Stops stopsInfo = populateStops(rs);
                map.put(stopsInfo.getStopId(), stopsInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private Stops populateStops(ResultSet rs) throws SQLException {
        Stops stops = new Stops();
        
        stops.setStopId(rs.getInt("STOP_ID"));
        stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stops.setStopNameAr(rs.getString("STOP_NAME_AR"));
     
        return stops;
    } 
    public void insertStop(Stops stops) throws Exception {                
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.STOPS  (STOP_ID,"
                    + " STOP_NAME_EN,"
                    + " STOP_NAME_AR)"
                    + " VALUES ((select max(STOP_ID) from BUSES.STOPS)+1,?,?)";
             PreparedStatement ps = conn.prepareStatement(sql); 
            
             ps.setString(1, stops.getStopNameEn());
             ps.setString(2, stops.getStopNameAr());
           
        
             
             ps.executeUpdate();
             ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateStop(Stops stops) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.STOPS SET"
                    + " STOP_NAME_EN=?,"
                    + " STOP_NAME_AR=?"
                    + " WHERE STOP_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
             ps.setString(1, stops.getStopNameEn());
             ps.setString(2, stops.getStopNameAr());       
             ps.setInt(3, stops.getStopId());

            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteStop(int stopId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.STOPS WHERE STOP_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, stopId);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public Stops getStops(int stopId) throws Exception {
        try {   
            Stops stops = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.STOPS"
                    + " WHERE STOP_ID=?";   
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, stopId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                stops = populateStops(rs);
                stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
                stops.setStopNameAr(rs.getString("STOP_NAME_AR"));

            }

            rs.close();
            ps.close();
            
            return stops;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    
}
