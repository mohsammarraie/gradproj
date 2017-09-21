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
import models.BusesInfo;

/**
 *
 * @author MOH
 */
public class BusesDao extends ConnectionDao {
  
 
        public ArrayList<BusesInfo> buildBusesInfo() throws Exception {
        ArrayList<BusesInfo> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM BUSES_INFO";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateBusesInfo(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        
//    public HashMap<Integer, BusesInfo> buildBusesInfoMap() throws Exception {
//        HashMap<Integer, BusesInfo> map = new HashMap<>();
//        Connection conn = getConnection();
//        
//        try {            
//            String sql = "SELECT * FROM BUSES";                        
//            PreparedStatement ps = conn.prepareStatement(sql);            
//
//            ResultSet rs = ps.executeQuery();           
//
//            while (rs.next()) {
//                BusesInfo busesInfo = populateBusesInfo(rs);
//                map.put(busesInfo.getBusID(), busesInfo);
//            }
//            
//            rs.close();
//            ps.close();
//
//            return map;
//        } catch (SQLException e) {
//            throw new SQLException(e.getMessage());
//        }
//    }

        private BusesInfo populateBusesInfo(ResultSet rs) throws SQLException {
        BusesInfo busesInfo = new BusesInfo();
        
        busesInfo.setBusID(rs.getInt("BUS_ID"));
        busesInfo.setDriverID(rs.getInt("DRIVER_ID"));
        busesInfo.setDepartureTime(rs.getString("DEPARTURE_TIME"));                    
        busesInfo.setArrivalTime(rs.getString("ARRIVAL_TIME"));                    
        busesInfo.setRouteEn(rs.getString("ROUTE_EN"));                    
        busesInfo.setRouteAr(rs.getString("ROUTE_AR"));                    
        busesInfo.setLicensePlate(rs.getString("LICENSE_PLATE")); 
        busesInfo.setCapacity(rs.getInt("CAPACITY"));
        return busesInfo;
    } 
    
}
