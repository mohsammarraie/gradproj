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
public class Report {
    private String studentId;
    private int tripId;
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
    private Date actualDepartureTime;
    private Date actualArrivalTime;
    private String licenseNumber;
    private String driverNameEn;
    private String driverNameAr;
    private String statusEn;
    private String statusAr;
    private int capacity;
    private String firstNameEn;
    private String lastNameEn;
    private String firstNameAr;
    private String lastNameAr;
    private String nationalId;
    private String departureTimeStatusEn;
    private String departureTimeStatusAr;
    private String arrivalTimeStatusAr;
    private String arrivalTimeStatusEn;
    private String scheduleTime;
    private int avgRating;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }
    
    
    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
    
    
    
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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

    public Date getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(Date actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public Date getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(Date actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getDriverNameEn() {
        return driverNameEn;
    }

    public void setDriverNameEn(String driverNameEn) {
        this.driverNameEn = driverNameEn;
    }

    public String getDriverNameAr() {
        return driverNameAr;
    }

    public void setDriverNameAr(String driverNameAr) {
        this.driverNameAr = driverNameAr;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusAr() {
        return statusAr;
    }

    public void setStatusAr(String statusAr) {
        this.statusAr = statusAr;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public String getFirstNameAr() {
        return firstNameAr;
    }

    public void setFirstNameAr(String firstNameAr) {
        this.firstNameAr = firstNameAr;
    }

    public String getLastNameAr() {
        return lastNameAr;
    }

    public void setLastNameAr(String lastNameAr) {
        this.lastNameAr = lastNameAr;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getDepartureTimeStatusEn() {
        return departureTimeStatusEn;
    }

    public void setDepartureTimeStatusEn(String departureTimeStatusEn) {
        this.departureTimeStatusEn = departureTimeStatusEn;
    }

    public String getDepartureTimeStatusAr() {
        return departureTimeStatusAr;
    }

    public void setDepartureTimeStatusAr(String departureTimeStatusAr) {
        this.departureTimeStatusAr = departureTimeStatusAr;
    }

    public String getArrivalTimeStatusAr() {
        return arrivalTimeStatusAr;
    }

    public void setArrivalTimeStatusAr(String arrivalTimeStatusAr) {
        this.arrivalTimeStatusAr = arrivalTimeStatusAr;
    }

    public String getArrivalTimeStatusEn() {
        return arrivalTimeStatusEn;
    }

    public void setArrivalTimeStatusEn(String arrivalTimeStatusEn) {
        this.arrivalTimeStatusEn = arrivalTimeStatusEn;
    }

}
