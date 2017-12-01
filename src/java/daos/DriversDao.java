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
import models.Drivers;
/**
 *
 * @author MOH
 */
public class DriversDao extends ConnectionDao {
    
        public ArrayList<Drivers> buildDriver() throws Exception {
        ArrayList<Drivers> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.DRIVERS ORDER BY DRIVER_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateDrivers(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public HashMap<Integer, Drivers> buildDriverMap() throws Exception {
        HashMap<Integer, Drivers> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES.DRIVERS ORDER BY DRIVER_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                Drivers driverInfo = populateDrivers(rs);
                map.put(driverInfo.getDriverId(), driverInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private Drivers populateDrivers(ResultSet rs) throws SQLException {
        Drivers driverInfo = new Drivers();
        
        driverInfo.setDriverId(rs.getInt("DRIVER_ID"));
        driverInfo.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
        driverInfo.setFirstNameEn(rs.getString("FIRST_NAME_EN"));                    
        driverInfo.setLastNameEn(rs.getString("LAST_NAME_EN")); 
        driverInfo.setLastNameAr(rs.getString("LAST_NAME_AR"));
        driverInfo.setPhoneNumber(rs.getString("PHONE_NUMBER")); 
        return driverInfo;
    } 
    public void insertDriver(Drivers drivers) throws Exception {                
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
            
             ps.setString(1, drivers.getFirstNameEn());
             ps.setString(2, drivers.getFirstNameAr());
             ps.setString(3, drivers.getLastNameEn());
             ps.setString(4, drivers.getLastNameAr());
             ps.setString(5, drivers.getPhoneNumber());
             
             ps.executeUpdate();
             ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateDriver(Drivers drivers) throws Exception {
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
            
             ps.setString(1, drivers.getFirstNameEn());
             ps.setString(2, drivers.getFirstNameAr());
             ps.setString(3, drivers.getLastNameEn());
             ps.setString(4, drivers.getLastNameAr());
             ps.setString(5, drivers.getPhoneNumber());      
             ps.setInt(6, drivers.getDriverId());

            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteDriver(int driverId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.DRIVERS WHERE DRIVER_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, driverId);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public Drivers getDrivers(int driverId) throws Exception {
        try {   
            Drivers drivers = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.DRIVERS"
                    + " WHERE DRIVER_ID=?";   
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, driverId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                drivers = populateDrivers(rs);
                drivers.setFirstNameEn(rs.getString("FIRST_NAME_EN"));
                drivers.setFirstNameAr(rs.getString("FIRST_NAME_AR"));
                drivers.setLastNameEn(rs.getString("LAST_NAME_EN"));
                drivers.setLastNameAr(rs.getString("LAST_NAME_AR"));
                drivers.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            }

            rs.close();
            ps.close();
            
            return drivers;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        
}
    
