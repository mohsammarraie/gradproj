/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author MOH
 */
public class RouteSchedules implements Serializable {

    private ArrayList<Stops> routeScheduleStops;

    private int routeId;
    private int scheduleId;
    private int routeScheduleActive;

    public ArrayList<Stops> getRouteScheduleStops() {
        return routeScheduleStops;
    }

    public void setRouteScheduleStops(ArrayList<Stops> routeScheduleStops) {
        this.routeScheduleStops = routeScheduleStops;
    }
    
    
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
    
    public int getRouteScheduleActive() {
    return routeScheduleActive;
    }

    public void setRouteScheduleActive(int routeScheduleActive) {
        this.routeScheduleActive = routeScheduleActive;
    }
    

}
