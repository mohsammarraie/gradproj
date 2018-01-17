/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author MOH
 */
public class BusesDrivers implements Serializable{
    
    private int busId;
    private int chasisNumber;
    private String licenseNumber;
    private int capacity;
    private int model;
    private String manufacturer;
    
    private int driverId;
    private String driverNameEn;
    private String driverNameAr;
    private String phoneNumber;
    private String nationalId;

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
    
    
    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getChasisNumber() {
        return chasisNumber;
    }

    public void setChasisNumber(int chasisNumber) {
        this.chasisNumber = chasisNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
    
    
    
    
}
