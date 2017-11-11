package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import models.BusesInfo;
import models.DriversInfo;

/**
 *
 * @author MOH
 */

public class BusesDao extends ConnectionDao {

        public ArrayList<BusesInfo> buildBusesInfo() 
            throws Exception {
        ArrayList<BusesInfo> list = new ArrayList<>();        
        
        try {   
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.BUSES";                        
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

        private BusesInfo populateBusesInfo(ResultSet rs) throws SQLException {
        BusesInfo busesInfo = new BusesInfo();
        
        busesInfo.setBusID(rs.getInt("BUS_ID")); 
        busesInfo.setChasisNumber(rs.getInt("CHASIS_NUMBER"));
        busesInfo.setLicenseNumber(rs.getString("LICENSE_NUMBER")); 
        busesInfo.setCapacity(rs.getInt("CAPACITY")); 
             
        return busesInfo;
    }
        
    public BusesInfo getBusesInfo(int BusID) throws Exception {
        try {   
            BusesInfo busesInfo = null;
            Connection conn = getConnection();
            
               String sql = "SELECT * FROM BUSES.BUSES"
                    + " WHERE BUS_ID=?";                      
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, BusID);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                busesInfo = populateBusesInfo(rs);
                busesInfo.setChasisNumber(rs.getInt("CHASIS_NUMBER"));
                busesInfo.setCapacity(rs.getInt("CAPACITY"));
                busesInfo.setLicenseNumber(rs.getString("LICENSE_NUMBER"));
            }

            rs.close();
            ps.close();
            
            return busesInfo;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
        public void insertBus(BusesInfo busesInfo) throws Exception {                
        try {
            Connection conn = getConnection();
            
            String sql = "INSERT INTO BUSES.BUSES (BUS_ID,"
                    + " CHASIS_NUMBER,"
                    + " LICENSE_NUMBER,"
                    + " CAPACITY)"
                    + " VALUES ((select max(BUS_ID) from BUSES.BUSES)+1,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql); 
            
            ps.setInt(1, busesInfo.getChasisNumber());
            ps.setString(2, busesInfo.getLicenseNumber());
            ps.setInt(3, busesInfo.getCapacity());
            
            ps.executeUpdate();
            
            ps.close();
 
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void updateBus(BusesInfo busesInfo) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.BUSES SET"
             
                    + " LICENSE_NUMBER=?,"
                    + " CHASIS_NUMBER=?,"
                    + " CAPACITY=?"
                    + " WHERE BUS_ID=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, busesInfo.getLicenseNumber());
            ps.setInt(2, busesInfo.getChasisNumber());
            ps.setInt(3, busesInfo.getCapacity());     
            ps.setInt(4, busesInfo.getBusID());

            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteBus(int busID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.BUSES WHERE BUS_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, busID);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        
}