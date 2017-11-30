/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Stops;
import daos.RouteStopsDao;
import daos.StopsDao;
import java.util.ArrayList;
import models.RouteStops;
/**
 *
 * @author MOH
 */
@Named(value = "addEditRouteStopBean")
@ViewScoped
public class AddEditRouteStopBean implements Serializable{
    
    private ArrayList<RouteStops> routeStopsInfos;
    private ArrayList<Stops> stopsInfos;
    private final RouteStopsDao routeStopsDao = new RouteStopsDao();
    private final StopsDao stopsDao = new StopsDao();
    private int routeStopID;
    private int stopID;
    private String stopNameAr;
    private String stopNameEn;
    private int stopOrder;
    RouteStops routeStopsInfo = new RouteStops();
   

    @Inject
    private SessionBean sessionBean;
    
    public AddEditRouteStopBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            routeStopID = sessionBean.getSelectedRouteStopID();
            routeStopsInfos = routeStopsDao.buildRouteStopsInfo(routeStopID);
            stopsInfos =  stopsDao.buildStopsInfo();
            if(routeStopID > 0){
                 routeStopsInfo = routeStopsDao.getRouteStopsInfo(routeStopID);
                 stopID = routeStopsInfo.getStopID();
                stopNameAr = routeStopsInfo.getStopNameAr();
                stopNameEn = routeStopsInfo.getStopNameEn();
                stopOrder = routeStopsInfo.getStopOrder();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Stops> getStopsInfos() {
        return stopsInfos;
    }

    public void setStopsInfos(ArrayList<Stops> stopsInfos) {
        this.stopsInfos = stopsInfos;
    }

    
    public int getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }
    
    public ArrayList<RouteStops> getRouteStopsInfos() {
        return routeStopsInfos;
    }

    public int getRouteStopID() {
        return routeStopID;
    }

    public void setRouteStopID(int routeStopID) {
        this.routeStopID = routeStopID;
    }
    
    
    
    public int getStopID() {
        return stopID;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
    }

    public String getStopNameAr() {
        return stopNameAr;
    }

    public void setStopNameAr(String stopNameAr) {
        this.stopNameAr = stopNameAr;
    }

    public String getStopNameEn() {
        return stopNameEn;
    }

    public void setStopNameEN(String stopNameEn) {
        this.stopNameEn = stopNameEn;
    }
    

        public void saveRouteStop() {
        try {
        
        
            routeStopsInfo.setStopID(routeStopID);
        
      
  
            if (sessionBean.getSelectedRouteStopID() > 0) {
                routeStopsDao.updateRouteStop(routeStopID, stopID, sessionBean.getSelectedRouteID(), stopOrder);
            } else {
                routeStopsDao.insertRouteStop(routeStopID, sessionBean.getSelectedRouteID(), stopOrder);
            }
        } catch (Exception ex) {
            sessionBean.navigate("route_stop_error");
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_route_stops");
    }
    
}
