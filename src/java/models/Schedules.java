/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author MOH
 */
public class Schedules implements Serializable {
    private int scheduleID;
    private Timestamp departureTime;
    private Timestamp arrivalTime;

//    public String formatTimeStamp(){
//        Timestamp stamp = departureTime;
//        Date date = new Date(stamp.getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        String formattedDate = sdf.format(date);
//        return formattedDate;
//    }
    
    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    
}
