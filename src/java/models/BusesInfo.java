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
    private int chasisNumber;
    private String licenseNumber;
    private int capacity;

    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
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

    
}
