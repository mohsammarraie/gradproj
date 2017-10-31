/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author MOH
 */
public class BusesInfo implements Serializable {
    
    private int busID;
    private int driverID;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private String routeEn;
    private String routeAr;
    private String licensePlate;
    private int capacity;
    private DriverInfo driverInfo;
 
//    public BusesInfo(){}
//    /**
//     *
//     * @param busID
//     * @param driverID
//     * @param departureTime
//     * @param arrivalTime
//     * @param routeEn
//     * @param routeAr
//     * @param licensePlate
//     * @param capacity
//     * @param driverInfo
//     */
//    public BusesInfo(int busID, int driverID, String departureTime, String arrivalTime, String routeEn, String routeAr, String licensePlate, int capacity, DriverInfo driverInfo) {
//        this.busID = busID;
//        this.driverID = driverID;
//        this.departureTime = departureTime;
//        this.arrivalTime = arrivalTime;
//        this.routeEn = routeEn;
//        this.routeAr = routeAr;
//        this.licensePlate = licensePlate;
//        this.capacity = capacity;
//        this.driverInfo = driverInfo;
//        
//    }

  
    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
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

    public String getRouteEn() {
        return routeEn;
    }

    public void setRouteEn(String routeEn) {
        this.routeEn = routeEn;
    }

    public String getRouteAr() {
        return routeAr;
    }

    public void setRouteAr(String routeAr) {
        this.routeAr = routeAr;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }
    
    
}
