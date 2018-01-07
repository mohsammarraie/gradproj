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
//    private int routeScheduleId;
//    private Timestamp scheduleTime;
//    private int stopId;
//    private int stopOrder;
//    private String stopNameEn;
//    private String stopNameAr;
//    private String sourceAr;
//    private String sourceEn;
//    private String destinationAr;
//    private String destinationEn;
//    private String routeCode;
//    private int routeActive;
//    private Date addTime;

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
    
    
    
//        public int getRouteScheduleId() {
//        return routeScheduleId;
//    }
//
//    public void setRouteScheduleId(int routeScheduleId) {
//        this.routeScheduleId = routeScheduleId;
//    }

//    public Date getAddTime() {
//        return addTime;
//    }
//
//    public void setAddTime(Date addTime) {
//        this.addTime = addTime;
//    }
//    public int getStopId() {
//        return stopId;
//    }
//
//    public void setStopId(int stopId) {
//        this.stopId = stopId;
//    }



//    public Timestamp getScheduleTime() {
//        return scheduleTime;
//    }
//
//    public void setScheduleTime(Timestamp scheduleTime) {
//        this.scheduleTime = scheduleTime;
//    }



//    public int getStopOrder() {
//        return stopOrder;
//    }
//
//    public void setStopOrder(int stopOrder) {
//        this.stopOrder = stopOrder;
//    }
//
//    public String getStopNameEn() {
//        return stopNameEn;
//    }
//
//    public void setStopNameEn(String stopNameEn) {
//        this.stopNameEn = stopNameEn;
//    }
//
//    public String getStopNameAr() {
//        return stopNameAr;
//    }

//    public void setStopNameAr(String stopNameAr) {
//        this.stopNameAr = stopNameAr;
//    }
//
//    public String getSourceAr() {
//        return sourceAr;
//    }
//
//    public void setSourceAr(String sourceAr) {
//        this.sourceAr = sourceAr;
//    }
//
//    public String getSourceEn() {
//        return sourceEn;
//    }
//
//    public void setSourceEn(String sourceEn) {
//        this.sourceEn = sourceEn;
//    }
//
//    public String getDestinationAr() {
//        return destinationAr;
//    }
//
//    public void setDestinationAr(String destinationAr) {
//        this.destinationAr = destinationAr;
//    }

//    public String getDestinationEn() {
//        return destinationEn;
//    }
//
//    public void setDestinationEn(String destinationEn) {
//        this.destinationEn = destinationEn;
//    }
//
//    public String getRouteCode() {
//        return routeCode;
//    }
//
//    public void setRouteCode(String routeCode) {
//        this.routeCode = routeCode;
//    }
//
//    public int getRouteActive() {
//        return routeActive;
//    }
//
//    public void setRouteActive(int routeActive) {
//        this.routeActive = routeActive;
//    }




    

}
