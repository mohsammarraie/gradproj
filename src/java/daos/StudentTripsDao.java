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
import models.Stop;
import models.StudentTrip;

/**
 *
 * @author MOH
 */
public class StudentTripsDao extends ConnectionDao {

    public ArrayList<StudentTrip> buildStudentTrips() throws Exception {
        ArrayList<StudentTrip> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM STUDENT_CURRENT_TRIPS_VIEW"
                    + " WHERE"
                    + " STATUS_EN=?"
                    + " ORDER BY DEPARTURE_TIME";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Ongoing");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateStudentTrips(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private StudentTrip populateStudentTrips(ResultSet rs) throws SQLException {

        StudentTrip studentTrips = new StudentTrip();

        studentTrips.setDepartureTime(rs.getTimestamp("DEPARTURE_TIME"));
        studentTrips.setArrivalTime(rs.getTimestamp("ARRIVAL_TIME"));
        studentTrips.setActualDepartureTime(rs.getTimestamp("ACTUAL_DEPARTURE_TIME"));
        studentTrips.setActualArrivalTime(rs.getTimestamp("ACTUAL_ARRIVAL_TIME"));
        studentTrips.setBusId(rs.getInt("BUS_ID"));
        studentTrips.setTripId(rs.getInt("TRIP_ID"));
        studentTrips.setSourceEn(rs.getString("SOURCE_EN"));
        studentTrips.setSourceAr(rs.getString("SOURCE_AR"));
        studentTrips.setDestinationEn(rs.getString("DESTINATION_EN"));
        studentTrips.setDestinationAr(rs.getString("DESTINATION_AR"));
        studentTrips.setScheduleId(rs.getInt("SCHEDULE_ID"));
        studentTrips.setRouteCode(rs.getString("ROUTE_CODE"));
        studentTrips.setDriverId(rs.getInt("DRIVER_ID"));
        studentTrips.setRouteId(rs.getInt("ROUTE_ID"));
        studentTrips.setDriverNameEn(rs.getString("DRIVER_NAME_EN"));
        studentTrips.setDriverNameAr(rs.getString("DRIVER_NAME_AR"));
        studentTrips.setLicenseNumber(rs.getString("LICENSE_NUMBER"));
        studentTrips.setStatusEn(rs.getString("STATUS_EN"));
        studentTrips.setStatusAr(rs.getString("STATUS_AR"));

        return studentTrips;
    }

    public ArrayList<Stop> buildStudentRouteStopsSchedules(int tripId) throws Exception {
        ArrayList<Stop> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT STOP_NAME_EN,STOP_NAME_AR,BUSES.STOPS.STOP_ID,TIME  FROM"
                    + " BUSES.ROUTES_STOPS_SCHEDULES,"
                    + " BUSES.STOPS ,"
                    + " TRIPS"
                    + " WHERE"
                    + " BUSES.ROUTES_STOPS_SCHEDULES.STOP_ID = BUSES.STOPS.STOP_ID"
                    + " AND"
                    + " BUSES.ROUTES_STOPS_SCHEDULES.ROUTE_ID=TRIPS.ROUTE_ID"
                    + " AND"
                    + " BUSES.ROUTES_STOPS_SCHEDULES.SCHEDULE_ID=TRIPS.SCHEDULE_ID "
                    + " AND TRIP_ID = ?"
                    + " ORDER BY TIME";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tripId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateStudentsRouteScheduleStops(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private Stop populateStudentsRouteScheduleStops(ResultSet rs) throws SQLException {
        Stop stops = new Stop();
        stops.setTime(rs.getTimestamp("TIME"));
        stops.setStopId(rs.getInt("STOP_ID"));
        stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stops.setStopNameAr(rs.getString("STOP_NAME_AR"));
        return stops;
    }

    public ArrayList<StudentTrip> buildStudentTrackMap(int tripId) throws Exception {
        ArrayList<StudentTrip> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM"
                    + " BUSES.TRIPS_COORDINATES"
                    + " WHERE TRIP_ID=?"
                    + " AND"
                    + " TIME_TAG=(SELECT max(TIME_TAG)"
                    + " FROM BUSES.TRIPS_COORDINATES"
                    + " WHERE TRIP_ID=?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tripId);
            ps.setInt(2, tripId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateStudentTrackMap(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private StudentTrip populateStudentTrackMap(ResultSet rs) throws SQLException {
        StudentTrip studentTrips = new StudentTrip();

        studentTrips.setLongtitude(rs.getString("LONGTITUDE"));
        studentTrips.setLatitude(rs.getString("LATITUDE"));
        studentTrips.setTimeTag(rs.getTimestamp("TIME_TAG"));
        studentTrips.setTripCoordinatesId(rs.getInt("TRIP_COORDINATES_ID"));
        studentTrips.setTripId(rs.getInt("TRIP_ID"));
        return studentTrips;

    }

}
