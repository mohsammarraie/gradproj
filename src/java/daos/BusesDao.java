package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import models.BusesInfo;
import models.DriverInfo;

/**
 *
 * @author MOH
 */

public class BusesDao extends ConnectionDao {

        public ArrayList<BusesInfo> buildBusesInfo(HashMap<Integer, DriverInfo> driverInfos) 
            throws Exception {
        ArrayList<BusesInfo> list = new ArrayList<>();        
        
        try {   
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES_INFO";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateBusesInfoWithDriverInfo(rs, driverInfos));
            }
            
            rs.close();
            ps.close();
            
            return list;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    private BusesInfo populateBusesInfoWithDriverInfo(ResultSet rs, HashMap<Integer, DriverInfo> driverInfos) 
            throws SQLException {
        BusesInfo busesInfo = new BusesInfo();
        
        busesInfo.setBusID(rs.getInt("BUS_ID"));
        busesInfo.setDriverID(rs.getInt("DRIVER_ID"));
        busesInfo.setDepartureTime(rs.getString("DEPARTURE_TIME"));                    
        busesInfo.setArrivalTime(rs.getString("ARRIVAL_TIME"));                    
        busesInfo.setRouteEn(rs.getString("ROUTE_EN"));                    
        busesInfo.setRouteAr(rs.getString("ROUTE_AR"));                    
        busesInfo.setLicensePlate(rs.getString("LICENSE_PLATE")); 
        busesInfo.setCapacity(rs.getInt("CAPACITY"));
        
        DriverInfo driverInfo = driverInfos.get(rs.getInt("DRIVER_ID"));        
        busesInfo.setDriverInfo(driverInfo);                
        
        return busesInfo;
    } 
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
        
        DriverInfo driverInfo = new DriverInfo();
        driverInfo.setDriverID(rs.getInt("DRIVER_ID"));        
        busesInfo.setDriverInfo(driverInfo);               
    
        return busesInfo;
    }
        
    public BusesInfo getBusesInfo(int BusID) throws Exception {
        try {   
            BusesInfo busesInfo = null;
            Connection conn = getConnection();
            
            String sql = "SELECT BUSES_INFO.*, "
                    + " DRIVER_INFO.NAME_EN as TYPE_EN,"
                    + " DRIVER_INFO.NAME_AR as TYPE_AR "
                    + " FROM BUSES_INFO, DRIVER_INFO "
                    + " WHERE BUSES_INFO.DRIVER_ID=DRIVER_INFO.DRIVER_ID AND"
                    + " BUS_ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, BusID);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                busesInfo = populateBusesInfo(rs);
                busesInfo.getDriverInfo().setNameEn(rs.getString("TYPE_EN"));
                busesInfo.getDriverInfo().setNameAr(rs.getString("TYPE_AR"));
            }

            rs.close();
            ps.close();
            
            return busesInfo;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        
}