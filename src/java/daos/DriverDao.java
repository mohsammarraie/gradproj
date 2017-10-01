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
import models.DriverInfo;
/**
 *
 * @author MOH
 */
public class DriverDao extends ConnectionDao {
    
        public ArrayList<DriverInfo> buildDriverInfo() throws Exception {
        ArrayList<DriverInfo> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM DRIVER_INFO ORDER BY DRIVER_ID";                        
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
    public HashMap<Integer, DriverInfo> buildDriverInfoMap() throws Exception {
        HashMap<Integer, DriverInfo> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM DRIVER_INFO ORDER BY DRIVER_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                DriverInfo driverInfo = populateDriverInfo(rs);
                map.put(driverInfo.getDriverID(), driverInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private DriverInfo populateDriverInfo(ResultSet rs) throws SQLException {
        DriverInfo driverInfo = new DriverInfo();
        
        driverInfo.setDriverID(rs.getInt("DRIVER_ID"));
        driverInfo.setNameAr(rs.getString("NAME_AR"));
        driverInfo.setNameEn(rs.getString("NAME_EN"));                    
        
        return driverInfo;
    } 
    public void insertDriver(DriverInfo driverInfo) throws Exception {                
        try {
            Connection conn = getConnection();

            String sql2 = "INSERT INTO DRIVER_INFO (DRIVER_ID,"
                    + " NAME_EN,"
                    + " NAME_AR"
                    + " VALUES ((select max(DRIVER_ID) from DRIVER_INFO)+1,?,?)";
             PreparedStatement ps2 = conn.prepareStatement(sql2); 
            
             ps2.setString(2, driverInfo.getNameEn());
             ps2.setString(3, driverInfo.getNameAr());
               
            ps2.executeUpdate();
            
            ps2.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateDriver(DriverInfo driverInfo) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE DRIVER_INFO SET"
                    + " NAME_EN=?,"
                    + " NAME_AR=?,"
                    + " WHERE DRIVER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, driverInfo.getNameEn());
            ps.setString(2, driverInfo.getNameAr());       
            ps.setInt(3, driverInfo.getDriverID());

            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteDriver(int driverID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM DRIVER_INFO WHERE DRIVER_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, driverID);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public DriverInfo getDriverInfo(int driverID) throws Exception {
        try {   
            DriverInfo driverInfo = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM DRIVER_INFO"
                    + " WHERE DRIVER_ID=?";   
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, driverID);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                driverInfo = populateDriverInfo(rs);
                driverInfo.setNameEn(rs.getString("NAME_EN"));
                driverInfo.setNameAr(rs.getString("NAME_AR"));
            }

            rs.close();
            ps.close();
            
            return driverInfo;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        
}
    
