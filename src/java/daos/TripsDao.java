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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
                    + " ORDER BY DEPARTURE_TIME DESC";
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

            String actualDepartureTimeStatusEn = "";
            String actualDepartureTimeStatusAr = "";

            //actual departure time
            Date date = new Date();
            Timestamp timestampActualDepartureTime = new Timestamp(date.getTime());

            Date departureTimeDate = trip.getDepartureTime();
            Timestamp timestampDepartureTime = new Timestamp(departureTimeDate.getTime());

            Format formatt = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            String timestampActualDepartureTimeToString = formatt.format(timestampActualDepartureTime);
            String timestampDepartureTimeToString = formatt.format(timestampDepartureTime);

            String[] splitActualDepartureTimeToString = timestampActualDepartureTimeToString.split(" ");
            String[] splitDepartureTimeToString = timestampDepartureTimeToString.split(" ");

            SimpleDateFormat df = new SimpleDateFormat("HH:mm");

            Date actualDT = df.parse(splitActualDepartureTimeToString[1]);
            Date departureT = df.parse(splitDepartureTimeToString[1]);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar cal3 = Calendar.getInstance();

            cal1.setTime(actualDT);
            cal2.setTime(departureT);
            cal3.setTime(departureT);

            cal2.add(Calendar.MINUTE, 10);
            cal3.add(Calendar.MINUTE, -10);

            if (cal1.compareTo(cal2) < 0 && cal1.compareTo(cal3) > 0) {
                actualDepartureTimeStatusEn = "On Time";
                actualDepartureTimeStatusAr = "على الموعد";
            } else if (cal1.compareTo(cal2) > 0) {
                actualDepartureTimeStatusEn = "Late";
                actualDepartureTimeStatusAr = "متأخر";
            } else if (cal1.compareTo(cal3) < 0) {
                actualDepartureTimeStatusEn = "Early";
                actualDepartureTimeStatusAr = "مبكر";
            }

            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.TRIPS";
            PreparedStatement preparedStm = conn.prepareStatement(query);
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

            String sql = "INSERT INTO BUSES.TRIPS "
                    + " (TRIP_ID, BUS_ID, SCHEDULE_ID, ROUTE_ID, DRIVER_ID, STATUS_EN, STATUS_AR, ACTUAL_DEPARTURE_TIME, DEPARTURE_TIME_STATUS_EN, DEPARTURE_TIME_STATUS_AR)"
                    + " VALUES("
                    + "(select " + countOrMax + "(TRIP_ID) from BUSES.TRIPS)+1,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, trip.getBusId());
            ps.setInt(2, trip.getScheduleId());
            ps.setInt(3, trip.getRouteId());
            ps.setInt(4, trip.getDriverId());
            ps.setString(5, "Ongoing");
            ps.setString(6, "في الطريق ");
            ps.setTimestamp(7, timestampActualDepartureTime);
            ps.setString(8, actualDepartureTimeStatusEn);
            ps.setString(9, actualDepartureTimeStatusAr);
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

    public void updateTripEnd(Trip trip) {
        try {
            String actualArrivalTimeStatusEn = "";
            String actualArrivalTimeStatusAr = "";

            //actual departure time
            Date date = new Date();
            Timestamp timestampActualArrivalTime = new Timestamp(date.getTime());

            Date arrivalTimeDate = trip.getArrivalTime();
            Timestamp timestampArrivalTime = new Timestamp(arrivalTimeDate.getTime());

            Format formatt = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            String timestampActualArrivalTimeToString = formatt.format(timestampActualArrivalTime);
            String timestampArrivalTimeToString = formatt.format(timestampArrivalTime);

            String[] splitActualArrivalTimeToString = timestampActualArrivalTimeToString.split(" ");
            String[] splitArrivalTimeToString = timestampArrivalTimeToString.split(" ");

            SimpleDateFormat df = new SimpleDateFormat("HH:mm");

            Date actualAT = df.parse(splitActualArrivalTimeToString[1]);
            Date arrivalT = df.parse(splitArrivalTimeToString[1]);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar cal3 = Calendar.getInstance();

            cal1.setTime(actualAT);
            cal2.setTime(arrivalT);
            cal3.setTime(arrivalT);

            cal2.add(Calendar.MINUTE, 10);
            cal3.add(Calendar.MINUTE, -10);

            if (cal1.compareTo(cal2) < 0 && cal1.compareTo(cal3) > 0) {
                actualArrivalTimeStatusEn = "On Time";
                actualArrivalTimeStatusAr = "على الموعد";
            } else if (cal1.compareTo(cal2) > 0) {
                actualArrivalTimeStatusEn = "Late";
                actualArrivalTimeStatusAr = "متأخر";
            } else if (cal1.compareTo(cal3) < 0) {
                actualArrivalTimeStatusEn = "Early";
                actualArrivalTimeStatusAr = "مبكر";
            }

            Connection conn = getConnection();
            String sql = "UPDATE BUSES.TRIPS"
                    + " SET STATUS_EN=?,"
                    + " STATUS_AR=?,"
                    + " ACTUAL_ARRIVAL_TIME=?,"
                    + " ARRIVAL_TIME_STATUS_EN=?,"
                    + " ARRIVAL_TIME_STATUS_AR=?"
                    + " WHERE"
                    + " TRIP_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Arrived");
            ps.setString(2, "وصلت");
            ps.setTimestamp(3, timestampActualArrivalTime);
            ps.setString(4, actualArrivalTimeStatusEn);
            ps.setString(5, actualArrivalTimeStatusAr);
            ps.setInt(6, tripId);
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

            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.TRIPS_COORDINATES"
                    + " WHERE"
                    + " TRIP_ID=?";
            PreparedStatement preparedStm = conn.prepareStatement(query);
            preparedStm.setInt(1, tripId);
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

            String sql = "INSERT INTO BUSES.TRIPS_COORDINATES("
                    + " TRIP_COORDINATES_ID,"
                    + " TRIP_ID,"
                    + " LATITUDE,"
                    + " LONGTITUDE,"
                    + " TIME_TAG)"
                    + " VALUES((select " + countOrMax + "(TRIP_COORDINATES_ID) from BUSES.TRIPS_COORDINATES)+1,?,?,?,?)";
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
