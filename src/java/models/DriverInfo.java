/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author MOH
 */
public class DriverInfo {
    private int driverID;
    private String nameEn;
    private String nameAr;

    public DriverInfo(int driverID, String nameEn, String nameAr) {
        this.driverID = driverID;
        this.nameEn = nameEn;
        this.nameAr = nameAr;
    }
    
    public DriverInfo(){}

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
    
    
}
