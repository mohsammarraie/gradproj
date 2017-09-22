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
    
    
}
