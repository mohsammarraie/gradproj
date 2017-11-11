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
public class StopsDao extends ConnectionDao {
    
    public ArrayList<StopsInfo> buildStopsInfo() throws Exception {
        ArrayList<StopsInfo> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.STOPS ORDER BY STOP_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateStopsInfo(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public HashMap<Integer, StopsInfo> buildStopsInfoMap() throws Exception {
        HashMap<Integer, StopsInfo> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.STOPS  ORDER BY STOP_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                StopsInfo stopsInfo = populateStopsInfo(rs);
                map.put(stopsInfo.getStopID(), stopsInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private StopsInfo populateStopsInfo(ResultSet rs) throws SQLException {
        StopsInfo stopsInfo = new StopsInfo();
        
        stopsInfo.setStopID(rs.getInt("STOP_ID"));
        stopsInfo.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stopsInfo.setStopNameAr(rs.getString("STOP_NAME_AR"));
     
        return stopsInfo;
    } 
    public void insertStop(StopsInfo stopsInfo) throws Exception {                
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
        public void updateStop(StopsInfo stopsInfo) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.STOPS SET"
                    + " STOP_NAME_EN=?,"
                    + " STOP_NAME_AR=?"
                    + " WHERE STOP_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
             ps.setString(1, stopsInfo.getStopNameEn());
             ps.setString(2, stopsInfo.getStopNameAr());       
             ps.setInt(3, stopsInfo.getStopID());

            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteStop(int stopID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.STOPS WHERE STOP_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, stopID);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public StopsInfo getStopsInfo(int stopID) throws Exception {
        try {   
            StopsInfo stopsInfo = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.STOPS"
                    + " WHERE STOP_ID=?";   
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, stopID);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                stopsInfo = populateStopsInfo(rs);
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
