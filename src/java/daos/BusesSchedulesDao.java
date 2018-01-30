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
import models.BusesSchedules;
import models.Buses;
/**
 *
 * @author MOH
 */
public class BusesSchedulesDao extends ConnectionDao {
    
       public ArrayList<BusesSchedules> buildAvailableBuses() throws Exception {
        ArrayList<BusesSchedules> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT BUS_ID, CONCAT(CONCAT(CONCAT(CONCAT(MANUFACTURER,', '), LICENSE_NUMBER),', '),CAPACITY) AS ASSIGNED_BUS"
                    + "                    FROM BUSES.BUSES"
                    + "                    ORDER BY BUS_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateAvailableBuses(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    
    private BusesSchedules populateAvailableBuses(ResultSet rs) throws SQLException {
        
        BusesSchedules busesSchedules = new BusesSchedules();
        busesSchedules.setBusId(rs.getInt("BUS_ID"));
        busesSchedules.setAssignedBus(rs.getString("ASSIGNED_BUS"));

        return busesSchedules;
    } 
    
    
        public ArrayList<BusesSchedules> buildBusesSchedules() throws Exception {
            ArrayList<BusesSchedules> list = new ArrayList<>();
            Connection conn = getConnection();

            try {            
                String sql = "SELECT ROUTE_ID,SCHEDULE_ID,BUSES.BUSES_SCHEDULES.BUS_ID,FROM_DATE,TO_DATE, CONCAT(CONCAT(CONCAT(CONCAT(MANUFACTURER,', '), LICENSE_NUMBER),', '),CAPACITY) AS ASSIGNED_BUS"
                        + "                    FROM BUSES.BUSES,"
                        + "                    BUSES.BUSES_SCHEDULES"
                        + "                     WHERE"
                        + "                      BUSES.BUSES.BUS_ID= BUSES.BUSES_SCHEDULES.BUS_ID"
                        + "                    ORDER BY BUSES.BUSES_SCHEDULES.BUS_ID";                        
                PreparedStatement ps = conn.prepareStatement(sql);            

                ResultSet rs = ps.executeQuery();           

                while (rs.next()) {
                    list.add(populateBusesSchedules(rs));
                }

                rs.close();
                ps.close();

                return list;
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
    }
    
    private BusesSchedules populateBusesSchedules(ResultSet rs) throws SQLException {
        
        BusesSchedules busesSchedules = new BusesSchedules();
        busesSchedules.setBusId(rs.getInt("BUS_ID"));
        busesSchedules.setScheduleId(rs.getInt("SCHEDULE_ID"));
        busesSchedules.setRouteId(rs.getInt("ROUTE_ID"));
        busesSchedules.setFromDate(rs.getTimestamp("FROM_DATE"));
        busesSchedules.setToDate(rs.getTimestamp("TO_DATE"));
        busesSchedules.setAssignedBus(rs.getString("ASSIGNED_BUS"));

        return busesSchedules;
    } 
    
 
    
    public void insertBusSchedule(int busId, int routeId, int scheduleId) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.BUSES_SCHEDULES (BUS_ID,"
                    + " ROUTE_ID,"
                    + " SCHEDULE_ID)"
                    + " VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, busId);
            ps.setInt(2, routeId);
            ps.setInt(3, scheduleId);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateBusSchedule(int busId, int routeId, int scheduleId) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.BUSES_SCHEDULES SET"
                    + " BUS_ID=?"
                    + " WHERE ROUTE_ID=?"
                    + " AND"
                    + " SCHEDULE_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, busId);
            ps.setInt(2, routeId);
            ps.setInt(3, scheduleId);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteBusSchedule(int routeId,int scheduleId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM BUSES.BUSES_SCHEDULES"
                    + " WHERE"
                    + " ROUTE_ID=?"
                    + " AND"
                    + " SCHEDULE_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
            ps.setInt(2, scheduleId);
            ps.executeUpdate();

            ps.close();
            
            
            String sql1 = "COMMIT";                               
            PreparedStatement ps1 = conn.prepareStatement(sql1);
           
            
            ps1.executeUpdate();

            ps1.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
     public BusesSchedules getBusesSchedules(int routeId, int scheduleId) throws Exception {
        try {   
            BusesSchedules busesSchedules = null;
            Connection conn = getConnection();
            
            String sql = "SELECT BUSES.BUSES.BUS_ID,FROM_DATE,TO_DATE, CONCAT(CONCAT(CONCAT(CONCAT(MANUFACTURER,', '), LICENSE_NUMBER),', '),CAPACITY) AS ASSIGNED_BUS"
                    + "                    FROM BUSES.BUSES,"
                    + "                    BUSES.BUSES_SCHEDULES"
                    + "                     WHERE"
                    + "                      BUSES.BUSES.BUS_ID= BUSES.BUSES_SCHEDULES.BUS_ID"
                    + "                        AND"
                    + "                        ROUTE_ID=?"
                    + "                        AND"
                    + "                       SCHEDULE_ID=?"
                    + "                    ORDER BY BUSES.BUSES_SCHEDULES.BUS_ID";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
            ps.setInt(2, scheduleId);
            ResultSet rs = ps.executeQuery();           
    
            while (rs.next()) {
                busesSchedules = populateBusesSchedules(rs);
            }

            rs.close();
            ps.close();
            
            return busesSchedules;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
}
