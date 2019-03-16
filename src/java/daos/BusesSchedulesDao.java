/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.BusSchedule;
import models.BusScheduleConflict;

/**
 *
 * @author MOH
 */
public class BusesSchedulesDao extends ConnectionDao {

    public ArrayList<BusSchedule> buildAvailableBuses() throws Exception {
        ArrayList<BusSchedule> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT BUS_ID, CONCAT(CONCAT(CONCAT(CONCAT(MANUFACTURER,', '), LICENSE_NUMBER),', '),CAPACITY) AS ASSIGNED_BUS"
                    + " FROM BUSES.BUSES"
                    + " ORDER BY BUS_ID";
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

    private BusSchedule populateAvailableBuses(ResultSet rs) throws SQLException {

        BusSchedule busesSchedules = new BusSchedule();
        busesSchedules.setBusId(rs.getInt("BUS_ID"));
        busesSchedules.setAssignedBus(rs.getString("ASSIGNED_BUS"));

        return busesSchedules;
    }

    public ArrayList<BusSchedule> buildBusesSchedules() throws Exception {
        ArrayList<BusSchedule> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT ROUTE_ID,SCHEDULE_ID,BUSES.BUSES_SCHEDULES.BUS_ID,FROM_DATE,TO_DATE, CONCAT(CONCAT(CONCAT(CONCAT(MANUFACTURER,', '), LICENSE_NUMBER),', '),CAPACITY) AS ASSIGNED_BUS"
                    + " FROM BUSES.BUSES,"
                    + " BUSES.BUSES_SCHEDULES"
                    + " WHERE"
                    + " BUSES.BUSES.BUS_ID= BUSES.BUSES_SCHEDULES.BUS_ID"
                    + " ORDER BY BUSES.BUSES_SCHEDULES.BUS_ID";
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

    private BusSchedule populateBusesSchedules(ResultSet rs) throws SQLException {

        BusSchedule busesSchedules = new BusSchedule();
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

            CallableStatement cs = conn.prepareCall("{call BUSES.INSERT_WITH_CONFLICT_CHECK(?,?,?) }");
            cs.setInt(1, busId);
            cs.setInt(2, routeId);
            cs.setInt(3, scheduleId);
            cs.execute();
            cs.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateBusSchedule(int busId, int routeId, int scheduleId) throws Exception {
        try {


            Connection conn = getConnection();
            
            CallableStatement cs = conn.prepareCall("{call BUSES.UPDATE_WITH_CONFLICT_CHECK(?,?,?) }");
            cs.setInt(1, busId);
            cs.setInt(2, routeId);
            cs.setInt(3, scheduleId);
            cs.execute();
            cs.close();


        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteBusSchedule(int routeId, int scheduleId) throws Exception {
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

    public BusSchedule getBusesSchedules(int routeId, int scheduleId) throws Exception {
        try {
            BusSchedule busesSchedules = null;
            Connection conn = getConnection();

            String sql = "SELECT ROUTE_ID,SCHEDULE_ID,BUSES.BUSES.BUS_ID,FROM_DATE,TO_DATE, CONCAT(CONCAT(CONCAT(CONCAT(MANUFACTURER,', '), LICENSE_NUMBER),', '),CAPACITY) AS ASSIGNED_BUS"
                    + " FROM BUSES.BUSES,"
                    + " BUSES.BUSES_SCHEDULES"
                    + " WHERE"
                    + " BUSES.BUSES.BUS_ID= BUSES.BUSES_SCHEDULES.BUS_ID"
                    + " AND"
                    + " ROUTE_ID=?"
                    + " AND"
                    + " SCHEDULE_ID=?"
                    + " ORDER BY BUSES.BUSES_SCHEDULES.BUS_ID";
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
    
    public BusScheduleConflict getBusesSchedulesConflicts(int routeId, int scheduleId, int busId) throws Exception {
        try {
            BusScheduleConflict busScheduleConflict = null;
            Connection conn = getConnection();

            String sql = "SELECT *"
                    + " FROM"
                    + " BUSES.ALL_SCHEDULES_BUSES_VIEW"
                    + " WHERE"
                    + " ROUTE_ID=?"
                    + " AND"
                    + " BUS_ID=?"
                    + " AND"
                    + " SCHEDULE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
             ps.setInt(2, busId);
            ps.setInt(3, scheduleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                busScheduleConflict = populateBusesSchedulesConflicts(rs);
            }

            rs.close();
            ps.close();

            return busScheduleConflict;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
      private BusScheduleConflict populateBusesSchedulesConflicts(ResultSet rs) throws SQLException {

        BusScheduleConflict busesSchedulesConflicts = new BusScheduleConflict();
        busesSchedulesConflicts.setBusId(rs.getInt("BUS_ID"));
        busesSchedulesConflicts.setScheduleId(rs.getInt("SCHEDULE_ID"));
        busesSchedulesConflicts.setRouteId(rs.getInt("ROUTE_ID"));
        busesSchedulesConflicts.setDepartureTime(rs.getTimestamp("DEPARTURE_TIME"));
        busesSchedulesConflicts.setArrivalTime(rs.getTimestamp("ARRIVAL_TIME"));
        busesSchedulesConflicts.setSourceEn(rs.getString("SOURCE_EN"));
        busesSchedulesConflicts.setSourceAr(rs.getString("SOURCE_AR"));
        busesSchedulesConflicts.setDestinationEn(rs.getString("DESTINATION_EN"));
        busesSchedulesConflicts.setDestinationAr(rs.getString("DESTINATION_AR"));
        busesSchedulesConflicts.setRouteCode(rs.getString("ROUTE_CODE"));
        busesSchedulesConflicts.setLicenseNumber(rs.getString("LICENSE_NUMBER"));


        return busesSchedulesConflicts;
    }
    

}
