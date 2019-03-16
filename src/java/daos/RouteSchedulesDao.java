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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.RouteSchedule;
import models.Stop;

/**
 *
 * @author MOH
 */
public class RouteSchedulesDao extends ConnectionDao {

    public ArrayList<RouteSchedule> buildRouteSchedules(int routeId) throws Exception {
        ArrayList<RouteSchedule> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM"
                    + " BUSES.ROUTES_SCHEDULES"
                    + " WHERE"
                    + " BUSES.ROUTES_SCHEDULES.ROUTE_ID=? ORDER BY SCHEDULE_ID";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateRouteSchedules(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Stop populateRouteScheduleStop(ResultSet rs) throws SQLException {
        Stop stops = new Stop();
        stops.setTime(rs.getTimestamp("TIME"));
        stops.setStopId(rs.getInt("STOP_ID"));
        stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stops.setStopNameAr(rs.getString("STOP_NAME_AR"));
        stops.setScheduleId(rs.getInt("SCHEDULE_ID"));
        return stops;
    }

    private RouteSchedule populateRouteSchedules(ResultSet rs) throws SQLException {

        RouteSchedule routeSchedules = new RouteSchedule();
        routeSchedules.setRouteId(rs.getInt("ROUTE_ID"));
        routeSchedules.setRouteScheduleActive(rs.getInt("ROUTE_SCHEDULE_ACTIVE"));
        routeSchedules.setScheduleId(rs.getInt("SCHEDULE_ID"));
        return routeSchedules;
    }

    public void deleteRouteSchedules(int scheduleId, int routeId) throws Exception {
        Connection conn = getConnection();

        try {
            String sql = "DELETE FROM BUSES.ROUTES_SCHEDULES WHERE ROUTE_ID=? AND SCHEDULE_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, routeId);
            ps.setInt(2, scheduleId);
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteRouteScheduleStops(int scheduleId, int routeId) throws Exception {
        Connection conn = getConnection();

        try {
            String sql = "DELETE FROM BUSES.ROUTES_STOPS_SCHEDULES WHERE ROUTE_ID=? AND SCHEDULE_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, routeId);
            ps.setInt(2, scheduleId);
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Stop> buildRouteScheduleStops(int routeId) throws Exception {
        ArrayList<Stop> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT STOPS.STOP_ID, STOP_NAME_AR, STOP_NAME_EN, TIME, SCHEDULE_ID"
                    + " FROM BUSES.STOPS JOIN BUSES.ROUTES_STOPS_SCHEDULES ON"
                    + " BUSES.STOPS.STOP_ID = BUSES.ROUTES_STOPS_SCHEDULES.STOP_ID"
                    + " WHERE ROUTE_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateRouteScheduleStop(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void insertRouteSchedule(int routeId) throws Exception {
        try {
            Connection conn = getConnection();

            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.ROUTES_SCHEDULES"
                    + " WHERE"
                    + " ROUTE_ID=?";
            PreparedStatement preparedStm = conn.prepareStatement(query);
            preparedStm.setInt(1, routeId);
            ResultSet resultSet = preparedStm.executeQuery();

            int count = 0;

            while (resultSet.next()) {
                count = resultSet.getInt("ROW_COUNTER");
            }
            String countOrMax = "";
            if (count > 0) {
                countOrMax = "max";
            } else {
                countOrMax = "count";
            }

            String sql2 = "INSERT INTO BUSES.ROUTES_SCHEDULES(SCHEDULE_ID, ROUTE_ID, ROUTE_SCHEDULE_ACTIVE)"
                    + " VALUES ("
                    + "(select " + countOrMax + "(SCHEDULE_ID) from BUSES.ROUTES_SCHEDULES WHERE ROUTE_ID = ?)+1,?,1)";
            PreparedStatement ps2 = conn.prepareStatement(sql2);

            ps2.setInt(1, routeId);
            ps2.setInt(2, routeId);
            // ps2.setInt(2, routeSchedules.getRouteScheduleActive()); //must set it just like routestops active check box

            ps2.executeUpdate();
            ps2.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void insertRouteStopSchedule(int stopId, int routeId, Date time) throws Exception {
        try {
            Connection conn = getConnection();

            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.ROUTES_STOPS_SCHEDULES"
                    + " WHERE"
                    + " ROUTE_ID=?";
            PreparedStatement preparedStm = conn.prepareStatement(query);
            preparedStm.setInt(1, routeId);
            ResultSet resultSet = preparedStm.executeQuery();

            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt("ROW_COUNTER");
            }
            String countOrMax = "";
            if (count > 0) {
                countOrMax = "max";
            } else {
                countOrMax = "count";
            }

            String sql = "INSERT INTO BUSES.ROUTES_STOPS_SCHEDULES(SCHEDULE_ID, ROUTE_ID, TIME, STOP_ID)"
                    + " VALUES ((select " + countOrMax + "(SCHEDULE_ID) from BUSES.ROUTES_SCHEDULES WHERE ROUTE_ID = ?),?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            Timestamp timeStamp = new Timestamp(time.getTime());

            ps.setInt(1, routeId);
            ps.setInt(2, routeId);
            ps.setTimestamp(3, timeStamp);
            ps.setInt(4, stopId);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateRouteSchedule(int stopId, int routeId, int scheduleId, Date time) throws Exception {
        try {

            Connection conn = getConnection();

            String sql = "UPDATE BUSES.ROUTES_STOPS_SCHEDULES SET"
                    + " TIME=?"
                    + " WHERE"
                    + " STOP_ID=?"
                    + " AND"
                    + " ROUTE_ID=?"
                    + " AND"
                    + " SCHEDULE_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            Timestamp timeStamp = new Timestamp(time.getTime());
            ps.setTimestamp(1, timeStamp);
            ps.setInt(2, stopId);
            ps.setInt(3, routeId);
            ps.setInt(4, scheduleId);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public RouteSchedule getRouteSchedules(int routeId) throws Exception {
        try {
            RouteSchedule routeSchedules = null;
            Connection conn = getConnection();

            String sql = "SELECT ROUTES_SCHEDULES.*,ROUTES_STOPS_SCHEDULES.*,ROUTES_STOPS.*, STOP_NAME_EN,STOP_NAME_AR FROM"
                    + " BUSES.ROUTES,"
                    + " BUSES.STOPS,"
                    + " BUSES.ROUTES_STOPS,"
                    + " BUSES.ROUTES_SCHEDULES,"
                    + " BUSES.ROUTES_STOPS_SCHEDULES"
                    + " WHERE"
                    + " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTES_STOPS.ROUTE_ID"
                    + " AND"
                    + " BUSES.STOPS.STOP_ID = BUSES.ROUTES_STOPS.STOP_ID"
                    + " AND"
                    + " BUSES.ROUTES_STOPS_SCHEDULES.STOP_ID = BUSES.ROUTES_STOPS.STOP_ID"
                    + " AND"
                    + " BUSES.ROUTES_STOPS_SCHEDULES.ROUTE_SCHEDULE_ID =BUSES.ROUTES_SCHEDULES.ROUTE_SCHEDULE_ID"
                    + " AND"
                    + " BUSES.ROUTES.ROUTE_ID=?"
                    + " ORDER BY STOP_ORDER";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                routeSchedules = populateRouteSchedules(rs);

            }

            rs.close();
            ps.close();

            return routeSchedules;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
       public boolean checkBusesSchedules(int routeId, int scheduleId){
         boolean flag = false;
        try {

            int i=0;
            Connection conn = getConnection();
            
            String sql = "SELECT count(*) as ROW_COUNTER FROM BUSES.BUSES_SCHEDULES"
                    + " WHERE"
                    + " ROUTE_ID=?"
                    + " AND"
                    + " SCHEDULE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
             ps.setInt(2, scheduleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = rs.getInt("ROW_COUNTER");
            }
            rs.close();
            ps.close();

            if (i > 0)
                flag = true;
            else 
                flag= false;
          
        } catch (Exception ex) {
            Logger.getLogger(RouteStopsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
           return flag; 
    }
    
         public boolean checkRouteStopsSchedules(int routeId){
         boolean flag = false;
        try {

            int i=0;
            Connection conn = getConnection();
            
            String sql = "SELECT count(*) as ROW_COUNTER FROM BUSES.ROUTES_STOPS"
                    + " WHERE"
                    + " ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = rs.getInt("ROW_COUNTER");
            }
            rs.close();
            ps.close();

            if (i > 0)
                flag = true;
            else 
                flag= false;
          
        } catch (Exception ex) {
            Logger.getLogger(RouteStopsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
           return flag; 
    }
}
