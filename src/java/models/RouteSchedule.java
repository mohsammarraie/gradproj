/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author MOH
 */
public class RouteSchedule implements Serializable {

    private ArrayList<Stop> routeScheduleStops;

    private int routeId;
    private int scheduleId;
    private int routeScheduleActive;

    public ArrayList<Stop> getRouteScheduleStops() {
        return routeScheduleStops;
    }

    public void setRouteScheduleStops(ArrayList<Stop> routeScheduleStops) {
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
