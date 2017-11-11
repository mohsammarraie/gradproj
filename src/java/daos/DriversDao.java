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
import models.DriversInfo;
/**
 *
 * @author MOH
 */
public class DriversDao extends ConnectionDao {
    
        public ArrayList<DriversInfo> buildDriverInfo() throws Exception {
        ArrayList<DriversInfo> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.DRIVERS ORDER BY DRIVER_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateDriverInfo(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public HashMap<Integer, DriversInfo> buildDriverInfoMap() throws Exception {
        HashMap<Integer, DriversInfo> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.DRIVERS ORDER BY DRIVER_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                DriversInfo driverInfo = populateDriverInfo(rs);
                map.put(driverInfo.getDriverID(), driverInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private DriversInfo populateDriverInfo(ResultSet rs) throws SQLException {
        DriversInfo driverInfo = new DriversInfo();
        
        driverInfo.setDriverID(rs.getInt("DRIVER_ID"));
        driverInfo.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
        driverInfo.setFirstNameEn(rs.getString("FIRST_NAME_EN"));                    
        driverInfo.setLastNameEn(rs.getString("LAST_NAME_EN")); 
        driverInfo.setLastNameAr(rs.getString("LAST_NAME_AR"));
        driverInfo.setPhoneNumber(rs.getString("PHONE_NUMBER")); 
        return driverInfo;
    } 
    public void insertDriver(DriversInfo driverInfo) throws Exception {                
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.DRIVERS (DRIVER_ID,"
                    + " FIRST_NAME_EN,"
                    + " FIRST_NAME_AR,"
                    + " LAST_NAME_EN,"
                    + " LAST_NAME_AR,"
                    + " PHONE_NUMBER)"
                    + " VALUES ((select max(DRIVER_ID) from BUSES.DRIVERS)+1,?,?,?,?,?)";
             PreparedStatement ps = conn.prepareStatement(sql); 
            
             ps.setString(1, driverInfo.getFirstNameEn());
             ps.setString(2, driverInfo.getFirstNameAr());
             ps.setString(3, driverInfo.getLastNameEn());
             ps.setString(4, driverInfo.getLastNameAr());
             ps.setString(5, driverInfo.getPhoneNumber());
             
             ps.executeUpdate();
             ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateDriver(DriversInfo driverInfo) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.DRIVERS SET"
                    + " FIRST_NAME_EN=?,"
                    + " FIRST_NAME_AR=?,"
                    + " LAST_NAME_EN=?,"
                    + " LAST_NAME_AR=?,"
                    + " PHONE_NUMBER=?"
                    + " WHERE DRIVER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
             ps.setString(1, driverInfo.getFirstNameEn());
             ps.setString(2, driverInfo.getFirstNameAr());
             ps.setString(3, driverInfo.getLastNameEn());
             ps.setString(4, driverInfo.getLastNameAr());
             ps.setString(5, driverInfo.getPhoneNumber());      
             ps.setInt(6, driverInfo.getDriverID());

            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteDriver(int driverID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.DRIVERS WHERE DRIVER_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, driverID);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public DriversInfo getDriverInfo(int driverID) throws Exception {
        try {   
            DriversInfo driverInfo = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.DRIVERS"
                    + " WHERE DRIVER_ID=?";   
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, driverID);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                driverInfo = populateDriverInfo(rs);
                driverInfo.setFirstNameEn(rs.getString("FIRST_NAME_EN"));
                driverInfo.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
                driverInfo.setLastNameEn(rs.getString("LAST_NAME_EN"));
                driverInfo.setLastNameAr(rs.getString("LAST_NAME_AR"));
                driverInfo.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            }

            rs.close();
            ps.close();
            
            return driverInfo;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        
}
    
