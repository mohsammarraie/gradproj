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
import models.SchedulesInfo;
/**
 *
 * @author MOH
 */
public class SchedulesDao extends ConnectionDao  {
       public ArrayList<SchedulesInfo> buildSchedulesInfo() throws Exception {
        ArrayList<SchedulesInfo> list = new ArrayList<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM SCHEDULES_INFO ORDER BY SCHEDULE_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                list.add(populateSchedulesInfo(rs));
            }
            
            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public HashMap<Integer, SchedulesInfo> buildSchedulesInfoMap() throws Exception {
        HashMap<Integer, SchedulesInfo> map = new HashMap<>();
        Connection conn = getConnection();
        
        try {            
            String sql = "SELECT * FROM SCHEDULES_INFO ORDER BY SCHEDULE_ID";                        
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                SchedulesInfo schedulesInfo = populateSchedulesInfo(rs);
                map.put(schedulesInfo.getScheduleID(), schedulesInfo);
            }
            
            rs.close();
            ps.close();

            return map;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private SchedulesInfo populateSchedulesInfo(ResultSet rs) throws SQLException {
        SchedulesInfo schedulesInfo = new SchedulesInfo();
        
        schedulesInfo.setScheduleID(rs.getInt("SCHEDULE_ID"));
        schedulesInfo.setDepartureTime(rs.getTimestamp("DEPARTURE_TIME"));                    
        schedulesInfo.setArrivalTime(rs.getTimestamp("ARRIVAL_TIME"));                     
        
        return schedulesInfo;
    } 
    public void insertSchedule(SchedulesInfo schedulesInfo) throws Exception {                
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO SCHEDULES_INFO (SCHEDULE_ID,"
                    + " DEPARTURE_TIME,"
                    + " ARRIVAL_TIME)"
                    + " VALUES ((select max(SCHEDULE_ID) from SCHEDULES_INFO)+1,?,?)";
             PreparedStatement ps = conn.prepareStatement(sql); 
            
            ps.setTimestamp(1, schedulesInfo.getDepartureTime());
            ps.setTimestamp(2, schedulesInfo.getArrivalTime());
             
             ps.executeUpdate();
             ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public void updateSchedule(SchedulesInfo schedulesInfo) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE SCHEDULES_INFO SET"
                    + " DEPARTURE_TIME=?,"
                    + " ARRIVAL_TIME=?"
                    + " WHERE SCHEDULE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setTimestamp(1, schedulesInfo.getDepartureTime());
            ps.setTimestamp(2, schedulesInfo.getArrivalTime());      
            ps.setInt(3, schedulesInfo.getScheduleID());

            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteSchedule(int scheduleID) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM SCHEDULES_INFO WHERE SCHEDULE_ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, scheduleID);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
        public SchedulesInfo getSchedulesInfo(int scheduleID) throws Exception {
        try {   
            SchedulesInfo schedulesInfo = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM SCHEDULES_INFO"
                    + " WHERE SCHEDULE_ID=?";   
            
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, scheduleID);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                schedulesInfo = populateSchedulesInfo(rs);
                ps.setTimestamp(1, schedulesInfo.getDepartureTime());
                ps.setTimestamp(2, schedulesInfo.getArrivalTime());
            }

            rs.close();
            ps.close();
            
            return schedulesInfo;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    
}
