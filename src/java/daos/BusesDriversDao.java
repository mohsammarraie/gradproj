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
import models.BusesDrivers;
import models.Drivers;

/**
 *
 * @author MOH
 */
public class BusesDriversDao  extends ConnectionDao{
    
     public ArrayList<BusesDrivers> buildAvailableBusesDrivers() throws Exception {
        ArrayList<BusesDrivers> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN),', '),NATIONAL_ID) AS DRIVER_NAME_EN,"
                    + "                    CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR),', '),NATIONAL_ID) AS DRIVER_NAME_AR,"
                    + "                    DRIVER_ID,NATIONAL_ID"
                    + "                    FROM BUSES.DRIVERS "
                    + "                    WHERE DRIVER_ID NOT IN (SELECT DRIVER_ID FROM BUSES_DRIVERS)"
                    + "                    ORDER BY DRIVER_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateAvailableBusesDrivers(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    
    private BusesDrivers populateAvailableBusesDrivers(ResultSet rs) throws SQLException {
        BusesDrivers driverInfo = new BusesDrivers();
        
        driverInfo.setDriverId(rs.getInt("DRIVER_ID"));
        driverInfo.setDriverNameEn(rs.getString("DRIVER_NAME_EN"));
        driverInfo.setDriverNameAr(rs.getString("DRIVER_NAME_AR"));
        driverInfo.setNationalId(rs.getString("NATIONAL_ID"));
        
        return driverInfo;
    } 
    
    public ArrayList<BusesDrivers> buildBusesDrivers() throws Exception {
        ArrayList<BusesDrivers> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN),', '),NATIONAL_ID) AS DRIVER_NAME_EN,"
                    + "                                 CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR),', '),NATIONAL_ID) AS DRIVER_NAME_AR,"
                    + "                                 BUSES.BUSES_DRIVERS.DRIVER_ID,NATIONAL_ID,BUSES.BUSES_DRIVERS.BUS_ID"
                    + "                                   FROM BUSES.DRIVERS,BUSES.BUSES_DRIVERS "
                    + "                                  WHERE BUSES.BUSES_DRIVERS.DRIVER_ID = BUSES.DRIVERS.DRIVER_ID"
                    + "                                 ORDER BY DRIVER_ID";                       
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateBusesDrivers(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    private BusesDrivers populateBusesDrivers(ResultSet rs) throws SQLException {
        BusesDrivers driverInfo = new BusesDrivers();
        
        driverInfo.setDriverId(rs.getInt("DRIVER_ID"));
        driverInfo.setDriverNameEn(rs.getString("DRIVER_NAME_EN"));
        driverInfo.setDriverNameAr(rs.getString("DRIVER_NAME_AR"));
        driverInfo.setNationalId(rs.getString("NATIONAL_ID"));
        driverInfo.setBusId(rs.getInt("BUS_ID"));
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
                    + " PHONE_NUMBER,"
                    + " NATIONAL_ID)"
                    + " VALUES ((select max(DRIVER_ID) from BUSES.DRIVERS)+1,?,?,?,?,?,?)";
             PreparedStatement ps = conn.prepareStatement(sql); 
            
             ps.setString(1, drivers.getFirstNameEn());
             ps.setString(2, drivers.getFirstNameAr());
             ps.setString(3, drivers.getLastNameEn());
             ps.setString(4, drivers.getLastNameAr());
             ps.setString(5, drivers.getPhoneNumber());
             ps.setString(6, drivers.getNationalId());
             
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
                    + " PHONE_NUMBER=?,"
                    + " NATIONAL_ID=?"
                    + " WHERE DRIVER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
             ps.setString(1, drivers.getFirstNameEn());
             ps.setString(2, drivers.getFirstNameAr());
             ps.setString(3, drivers.getLastNameEn());
             ps.setString(4, drivers.getLastNameAr());
             ps.setString(5, drivers.getPhoneNumber());
             ps.setString(6, drivers.getNationalId()); 
             ps.setInt(7, drivers.getDriverId());

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
    
     public BusesDrivers getBusesDrivers() throws Exception {
        try {   
            BusesDrivers busesDrivers = null;
            Connection conn = getConnection();
            
                String sql = "SELECT CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_EN,' '), LAST_NAME_EN),', '),NATIONAL_ID) AS DRIVER_NAME_EN,"
                    + "                                 CONCAT(CONCAT(CONCAT(CONCAT(FIRST_NAME_AR,' '), LAST_NAME_AR),', '),NATIONAL_ID) AS DRIVER_NAME_AR,"
                    + "                                 BUSES.BUSES_DRIVERS.DRIVER_ID,NATIONAL_ID,BUSES.BUSES_DRIVERS.BUS_ID"
                    + "                                   FROM BUSES.DRIVERS,BUSES.BUSES_DRIVERS "
                    + "                                  WHERE BUSES.BUSES_DRIVERS.DRIVER_ID = BUSES.DRIVERS.DRIVER_ID"
                    + "                                 ORDER BY DRIVER_ID";                      
            PreparedStatement ps = conn.prepareStatement(sql);            
         
            
            ResultSet rs = ps.executeQuery();           
    
            while (rs.next()) {
                busesDrivers = populateBusesDrivers(rs);
            }

            rs.close();
            ps.close();
            
            return busesDrivers;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    
}
