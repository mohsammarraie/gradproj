/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author MOH
 */
public class DriverSchedules {
    private int driverRouteScheduleId;
    private int scheduleId;
    private int busId;
    private int driverId;
    private int routeId;
    private String sourceAr;
    private String sourceEn;
    private String destinationAr;
    private String destinationEn;
    private String routeCode;
    private Date departureTime;
    private Date arrivalTime;
    
    
    
    public int getDriverRouteScheduleId() {
        return driverRouteScheduleId;
    }

    public void setDriverRouteScheduleId(int driverRouteScheduleId) {
        this.driverRouteScheduleId = driverRouteScheduleId;
    }
    
    
    
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getSourceAr() {
        return sourceAr;
    }

    public void setSourceAr(String sourceAr) {
        this.sourceAr = sourceAr;
    }

    public String getSourceEn() {
        return sourceEn;
    }

    public void setSourceEn(String sourceEn) {
        this.sourceEn = sourceEn;
    }

    public String getDestinationAr() {
        return destinationAr;
    }

    public void setDestinationAr(String destinationAr) {
        this.destinationAr = destinationAr;
    }

    public String getDestinationEn() {
        return destinationEn;
    }

    public void setDestinationEn(String destinationEn) {
        this.destinationEn = destinationEn;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    
    
}
