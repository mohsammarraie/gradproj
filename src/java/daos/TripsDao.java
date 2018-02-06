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
import models.Stop;
import models.Trip;

/**
 *
 * @author MOH
 */
public class TripsDao extends ConnectionDao {

    public static int tripId;

    public ArrayList<Stop> buildDriverRouteStopsSchedules(int routeId, int scheduleId) throws Exception {
        ArrayList<Stop> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT STOP_NAME_EN,STOP_NAME_AR,BUSES.STOPS.STOP_ID,TIME "
                    + " FROM"
                    + " BUSES.ROUTES_STOPS_SCHEDULES JOIN BUSES.STOPS ON"
                    + " BUSES.ROUTES_STOPS_SCHEDULES.STOP_ID = BUSES.STOPS.STOP_ID"
                    + " WHERE"
                    + " BUSES.ROUTES_STOPS_SCHEDULES.ROUTE_ID=?"
                    + " AND"
                    + " BUSES.ROUTES_STOPS_SCHEDULES.SCHEDULE_ID=?"
                    + " ORDER BY TIME";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
            ps.setInt(2, scheduleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateDriverRouteScheduleStops(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Stop populateDriverRouteScheduleStops(ResultSet rs) throws SQLException {
        Stop stops = new Stop();
        stops.setTime(rs.getTimestamp("TIME"));
        stops.setStopId(rs.getInt("STOP_ID"));
        stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stops.setStopNameAr(rs.getString("STOP_NAME_AR"));
        return stops;
    }

    public ArrayList<Trip> buildPastTrips(int driverId) throws Exception {
        ArrayList<Trip> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM PAST_TRIPS_VIEW"
                    + " WHERE DRIVER_ID =?"
                    + " ORDER BY DEPARTURE_TIME";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populatePastTrips(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Trip populatePastTrips(ResultSet rs) throws SQLException {
        Trip trips = new Trip();
        trips.setActualArrivalTime(rs.getTimestamp("ACTUAL_ARRIVAL_TIME"));
        trips.setActualDepartureTime(rs.getTimestamp("ACTUAL_DEPARTURE_TIME"));
        trips.setDepartureTime(rs.getTimestamp("DEPARTURE_TIME"));
        trips.setArrivalTime(rs.getTimestamp("ARRIVAL_TIME"));
        trips.setRouteCode(rs.getString("ROUTE_CODE"));
        trips.setSourceAr(rs.getString("SOURCE_AR"));
        trips.setSourceEn(rs.getString("SOURCE_EN"));
        trips.setDestinationEn(rs.getString("DESTINATION_EN"));
        trips.setDestinationAr(rs.getString("DESTINATION_AR"));
        trips.setStatusEn(rs.getString("STATUS_EN"));
        trips.setStatusAr(rs.getString("STATUS_AR"));
        return trips;
    }

    public void insertTrip(Trip trip) throws Exception {
        try {
            Connection conn = getConnection();
            Date date = new Date();
            Timestamp timeStamp = new Timestamp(date.getTime());

            String sql = "INSERT INTO BUSES.TRIPS "
                    + " (TRIP_ID, BUS_ID, SCHEDULE_ID, ROUTE_ID, DRIVER_ID, STATUS_EN, STATUS_AR, ACTUAL_DEPARTURE_TIME)"
                    + " VALUES("
                    + "(select max(TRIP_ID) from BUSES.TRIPS)+1,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, trip.getBusId());
            ps.setInt(2, trip.getScheduleId());
            ps.setInt(3, trip.getRouteId());
            ps.setInt(4, trip.getDriverId());
            ps.setString(5, "Ongoing");
            ps.setString(6, "في الطريق ");
            ps.setTimestamp(7, timeStamp);

            ps.executeUpdate();
            ps.close();

            Connection conn2 = getConnection();

            String sql2 = "SELECT max(TRIP_ID) as TRIP_ID FROM BUSES.TRIPS"
                    + " WHERE"
                    + " ROUTE_ID=?"
                    + " AND"
                    + " SCHEDULE_ID=?"
                    + " AND"
                    + " BUS_ID=?";
            PreparedStatement ps2 = conn2.prepareStatement(sql2);
            ps2.setInt(1, trip.getRouteId());
            ps2.setInt(3, trip.getBusId());
            ps2.setInt(2, trip.getScheduleId());

            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                tripId = rs.getInt("TRIP_ID");
            }
            rs.close();
            ps2.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateTripCancel() {
        try {

            Connection conn = getConnection();
            String sql = "UPDATE BUSES.TRIPS"
                    + " SET STATUS_EN=?,"
                    + " STATUS_AR=?"
                    + " WHERE"
                    + " TRIP_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Canceled");
            ps.setString(2, "ملغية");
            ps.setInt(3, tripId);
            ps.executeUpdate();
            ps.close();

        } catch (Exception ex) {
            Logger.getLogger(TripsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateTripEnd() {
        try {
            Date date = new Date();
            Timestamp timeStamp = new Timestamp(date.getTime());
            Connection conn = getConnection();
            String sql = "UPDATE BUSES.TRIPS"
                    + " SET STATUS_EN=?,"
                    + " STATUS_AR=?,"
                    + " ACTUAL_ARRIVAL_TIME=?"
                    + " WHERE"
                    + " TRIP_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Arrived");
            ps.setString(2, "وصلت");
            ps.setTimestamp(3, timeStamp);
            ps.setInt(4, tripId);
            ps.executeUpdate();
            ps.close();

        } catch (Exception ex) {
            Logger.getLogger(TripsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertTripCoordinates(String lat, String lng) {
        try {
            Date date = new Date();
            Timestamp timeStamp = new Timestamp(date.getTime());
            Connection conn = getConnection();
            String sql = "INSERT INTO BUSES.TRIPS_COORDINATES("
                    + " TRIP_COORDINATES_ID,"
                    + " TRIP_ID,"
                    + " LATITUDE,"
                    + " LONGTITUDE,"
                    + " TIME_TAG)"
                    + " VALUES((select count(TRIP_COORDINATES_ID) from BUSES.TRIPS_COORDINATES)+1,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tripId);
            ps.setString(2, lat);
            ps.setString(3, lng);
            ps.setTimestamp(4, timeStamp);

            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            Logger.getLogger(TripsDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
