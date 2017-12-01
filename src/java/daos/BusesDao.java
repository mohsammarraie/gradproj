package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import models.Buses;
import models.Drivers;

/**
 *
 * @author MOH
 */

public class BusesDao extends ConnectionDao {

        public ArrayList<Buses> buildBuses() 
            throws Exception {
        ArrayList<Buses> list = new ArrayList<>();        
        
        try {   
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM BUSES.BUSES";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateBuses(rs));
            }
            
            rs.close();
            ps.close();
            
            return list;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

        private Buses populateBuses(ResultSet rs) throws SQLException {
        Buses buses = new Buses();
        
        buses.setBusId(rs.getInt("BUS_ID")); 
        buses.setChasisNumber(rs.getInt("CHASIS_NUMBER"));
        buses.setLicenseNumber(rs.getString("LICENSE_NUMBER")); 
        buses.setCapacity(rs.getInt("CAPACITY")); 
             
        return buses;
    }
        
    public Buses getBuses(int BusId) throws Exception {
        try {   
            Buses buses = null;
            Connection conn = getConnection();
            
               String sql = "SELECT * FROM BUSES.BUSES"
                    + " WHERE BUS_ID=?";                      
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, BusId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                buses = populateBuses(rs);
                buses.setChasisNumber(rs.getInt("CHASIS_NUMBER"));
                buses.setCapacity(rs.getInt("CAPACITY"));
                buses.setLicenseNumber(rs.getString("LICENSE_NUMBER"));
            }

            rs.close();
            ps.close();
            
            return buses;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
        public void insertBus(Buses buses) throws Exception {                
        try {
            Connection conn = getConnection();
            
            String sql = "INSERT INTO BUSES.BUSES (BUS_ID,"
                    + " CHASIS_NUMBER,"
                    + " LICENSE_NUMBER,"
                    + " CAPACITY)"
                    + " VALUES ((select max(BUS_ID) from BUSES.BUSES)+1,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql); 
            
            ps.setInt(1, buses.getChasisNumber());
            ps.setString(2, buses.getLicenseNumber());
            ps.setInt(3, buses.getCapacity());
            
            ps.executeUpdate();
            
            ps.close();
 
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void updateBus(Buses buses) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.BUSES SET"
             
                    + " LICENSE_NUMBER=?,"
                    + " CHASIS_NUMBER=?,"
                    + " CAPACITY=?"
                    + " WHERE BUS_ID=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, buses.getLicenseNumber());
            ps.setInt(2, buses.getChasisNumber());
            ps.setInt(3, buses.getCapacity());     
            ps.setInt(4, buses.getBusId());

            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteBus(int busId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.BUSES WHERE BUS_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, busId);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        
}