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
public class RoutesInfo {
    
    private int routeID;
    private String sourceAr;
    private String sourceEn;
    private String destinationAr;
    private String destinationEn;
    
    public int getRouteID() {
        return routeID;
    }

    public String getSourceAr() {
        return sourceAr;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
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
    

    
    
}
