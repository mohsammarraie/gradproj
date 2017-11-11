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
import models.StopsInfo;
import daos.RouteStopsDao;
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "manageRouteStopsBean")
@ViewScoped
public class ManageRouteStopsBean implements Serializable {
 
    private StopsInfo selectedRouteStop;
    private final RouteStopsDao routeStopsDao = new RouteStopsDao();
    private ArrayList<StopsInfo> routeStopsInfo; 
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageRouteStopsBean(){}
    
       @PostConstruct
        public void init(){
            try {            
                routeStopsInfo = routeStopsDao.buildRouteStopsInfo(sessionBean.getSelectedItemId());            
            } catch (Exception ex) {
                Logger.getLogger(ManageRouteStopsBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public StopsInfo getSelectedRouteStop() {
        return selectedRouteStop;
    }

    public void setSelectedRouteStop(StopsInfo selectedRouteStop) {
        this.selectedRouteStop = selectedRouteStop;
    }

    public ArrayList<StopsInfo> getRouteStopsInfo() {
        return routeStopsInfo;
    }

    public void setRouteStopsInfo(ArrayList<StopsInfo> routeStopsInfo) {
        this.routeStopsInfo = routeStopsInfo;
    }
        

    public void saveSelectedItemId(){
        sessionBean.setSelectedItemId(selectedRouteStop.getStopID());
    }
    
    public void deleteSelectedRouteStop(){
        try {
            routeStopsDao.deleteRouteStop(selectedRouteStop.getStopID(), sessionBean.getSelectedItemId());
            sessionBean.navigate("manage_route_stops");

        } catch (Exception ex) {
         
            Logger.getLogger(ManageRouteStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
