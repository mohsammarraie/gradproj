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
import models.StopsInfo;
import daos.RouteStopsDao;
import daos.StopsDao;
import java.util.ArrayList;
/**
 *
 * @author MOH
 */
@Named(value = "addEditRouteStopBean")
@ViewScoped
public class AddEditRouteStopBean implements Serializable{
    
    private ArrayList<StopsInfo> stopsInfos;
    private final RouteStopsDao routeStopsDao = new RouteStopsDao();
    private final StopsDao stopsDao = new StopsDao();
    private int routeStopID;
    private int stopID;
    private String stopNameAr;
    private String stopNameEn;
    StopsInfo stopsInfo = new StopsInfo();
   

    @Inject
    private SessionBean sessionBean;
    
    public AddEditRouteStopBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            routeStopID = sessionBean.getSelectedItemId();
            stopsInfos = stopsDao.buildStopsInfo();
            
            if(routeStopID > 0){
                 stopsInfo = routeStopsDao.getRouteStopsInfo(routeStopID);
                 stopID = stopsInfo.getStopID();
                stopNameAr = stopsInfo.getStopNameAr();
                stopNameEn = stopsInfo.getStopNameEn();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<StopsInfo> getStopsInfos() {
        return stopsInfos;
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
        
        
            stopsInfo.setStopID(routeStopID);
        
      
  
            if (sessionBean.getSelectedItemId() > 0) {
                routeStopsDao.updateRouteStop(routeStopID, stopID, sessionBean.getSelectedRouteID());
            } else {
                routeStopsDao.insertRouteStop(stopsInfo);
            }
        } catch (Exception ex) {
            sessionBean.navigate("route_stop_error");
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_routes");
    }
    
}
