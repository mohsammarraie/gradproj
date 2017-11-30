/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Stops;
import daos.RouteStopsDao;
import javax.inject.Named;
import models.RouteStops;
/**
 *
 * @author MOH
 */
@Named(value = "manageRouteStopsBean")
@ViewScoped
public class ManageRouteStopsBean implements Serializable {
 
    private RouteStops selectedRouteStop;
    private final RouteStopsDao routeStopsDao = new RouteStopsDao();
    private ArrayList<RouteStops> routeStopsInfo; 
   
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageRouteStopsBean(){}
    
       @PostConstruct
        public void init(){
            try {            
                routeStopsInfo = routeStopsDao.buildRouteStopsInfo(sessionBean.getSelectedRouteID());
//                routeStopsInfo = routeStopsDao.buildRouteStopsInfo(sessionBean.getSelectedItemId());
            } catch (Exception ex) {
                Logger.getLogger(ManageRouteStopsBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    public RouteStops getSelectedRouteStop() {
        return selectedRouteStop;
    }

    public void setSelectedRouteStop(RouteStops selectedRouteStop) {
        this.selectedRouteStop = selectedRouteStop;
    }

 
    
    
    public ArrayList<RouteStops> getRouteStopsInfo() {
        return routeStopsInfo;
    }

    public void setRouteStopsInfo(ArrayList<RouteStops> routeStopsInfo) {
        this.routeStopsInfo = routeStopsInfo;
    }
        

    public void saveSelectedRouteStopId(){
        sessionBean.setSelectedRouteStopID(selectedRouteStop.getStopID());
    }
    
    public void deleteSelectedRouteStop(){
        try {
            routeStopsDao.deleteRouteStop(selectedRouteStop.getStopID(), sessionBean.getSelectedRouteID());
            sessionBean.navigate("manage_route_stops");

        } catch (Exception ex) {
         
            Logger.getLogger(ManageRouteStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
