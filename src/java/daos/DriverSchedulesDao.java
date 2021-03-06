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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.DriverSchedule;

/**
 *
 * @author MOH
 */
public class DriverSchedulesDao extends ConnectionDao {
     Date date = new Date();
    public ArrayList<DriverSchedule> buildDriverSchedules(int driverId) throws Exception {
        ArrayList<DriverSchedule> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
//             String sql = "select * from BUSES.DRIVERS_SCHEDULES_VIEW where to_char(DEPARTURE_TIME, 'HH24:MI:SS') >  to_char(SYSDATE - 0.0208333333333333, 'HH24:MI:SS')"
//                    + " AND"
//                    + " DRIVER_ID=?"
//                    + " ORDER BY DEPARTURE_TIME";
             
            String sql = "select * from BUSES.DRIVERS_SCHEDULES_VIEW"
                    + " WHERE"
                    + " DRIVER_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, driverId);
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

        try {
   
            //actual departure time
           
            Timestamp timestampActualDepartureTime = new Timestamp(date.getTime());
            
            
            Timestamp timestampDepartureTime = rs.getTimestamp("DEPARTURE_TIME");

            Format formatt = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            String timestampActualDepartureTimeToString = formatt.format(timestampActualDepartureTime);
            String timestampDepartureTimeToString = formatt.format(timestampDepartureTime);

            String[] splitActualDepartureTimeToString = timestampActualDepartureTimeToString.split(" ");
            String[] splitDepartureTimeToString = timestampDepartureTimeToString.split(" ");

            SimpleDateFormat df = new SimpleDateFormat("HH:mm");

            Date actualDT = df.parse(splitActualDepartureTimeToString[1]);
            Date departureT = df.parse(splitDepartureTimeToString[1]);

            Calendar actualTime = Calendar.getInstance();
            Calendar calDT= Calendar.getInstance();

            actualTime.setTime(actualDT);
            calDT.setTime(departureT);
            
            long aT= actualTime.getTimeInMillis();
            long dt= calDT.getTimeInMillis();
            long difference = aT-dt;
            driverSchedule.setDepartureTimeDifference(difference);
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
            
           
        } catch (ParseException ex) {
            Logger.getLogger(DriverSchedulesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return driverSchedule;
    }

}
