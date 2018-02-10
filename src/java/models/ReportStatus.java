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
public class ReportStatus {
    private int statusId;
    private String departureTimeStatusEn;
    private String departureTimeStatusAr;
    private String arrivalTimeStatusAr;
    private String arrivalTimeStatusEn;
    private String statusEn;
    private String statusAr;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
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

}
