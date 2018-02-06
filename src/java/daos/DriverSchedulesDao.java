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
import models.DriverSchedule;

/**
 *
 * @author MOH
 */
public class DriverSchedulesDao extends ConnectionDao {

    public ArrayList<DriverSchedule> buildDriverSchedules(int driverId) throws Exception {
        ArrayList<DriverSchedule> list = new ArrayList<>();
        Connection conn = getConnection();

        try {

            String sql = "SELECT * FROM DRIVERS_SCHEDULES_VIEW"
                    + " WHERE"
                    + " DRIVER_ID=?"
                    + " ORDER BY DEPARTURE_TIME";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateDriverSchedules(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private DriverSchedule populateDriverSchedules(ResultSet rs) throws SQLException {

        DriverSchedule driverSchedule = new DriverSchedule();

        driverSchedule.setDepartureTime(rs.getTimestamp("DEPARTURE_TIME"));
        driverSchedule.setArrivalTime(rs.getTimestamp("ARRIVAL_TIME"));
        driverSchedule.setBusId(rs.getInt("BUS_ID"));
        driverSchedule.setSourceEn(rs.getString("SOURCE_EN"));
        driverSchedule.setSourceAr(rs.getString("SOURCE_AR"));
        driverSchedule.setDestinationEn(rs.getString("DESTINATION_EN"));
        driverSchedule.setDestinationAr(rs.getString("DESTINATION_AR"));
        driverSchedule.setScheduleId(rs.getInt("SCHEDULE_ID"));
        driverSchedule.setRouteCode(rs.getString("ROUTE_CODE"));
        driverSchedule.setDriverId(rs.getInt("DRIVER_ID"));
        driverSchedule.setRouteId(rs.getInt("ROUTE_ID"));

        //implemented from concat routeId, scheduleId, busId to be used as a unique value (to make a unique row)
        String aa = "" + driverSchedule.getRouteId() + driverSchedule.getScheduleId() + driverSchedule.getBusId();
        driverSchedule.setDriverRouteScheduleId(Integer.parseInt(aa));

        return driverSchedule;
    }

}
