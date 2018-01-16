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
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import models.RouteStops;
import org.primefaces.context.RequestContext;
/**
 *
 * @author MOH
 */
@Named(value = "manageRouteStopsBean")
@ViewScoped
public class ManageRouteStopsBean implements Serializable {
 
    private RouteStops selectedRouteStop;
    private final RouteStopsDao routeStopsDao = new RouteStopsDao();
    private ArrayList<RouteStops> routeStopsArray; 
    String error_message_header = "Error!";
    String error_message_content = "Please delete the route schedule assigned to this route stop.";
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageRouteStopsBean(){}
    
       @PostConstruct
        public void init(){
            try {            
                routeStopsArray = routeStopsDao.buildRouteStops(sessionBean.getSelectedRouteId());
//                routeStopsArray = routeStopsDao.buildRouteStops(sessionBean.getSelectedItemId());
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

 
    
    
    public ArrayList<RouteStops> getRouteStopsArray() {
        return routeStopsArray;
    }

    public void setRouteStopsArray(ArrayList<RouteStops> routeStopsArray) {
        this.routeStopsArray = routeStopsArray;
    }
        

    public void saveSelectedRouteStopId(){
        sessionBean.setSelectedRouteStopId(selectedRouteStop.getStopId());
    }
    
    public void deleteSelectedRouteStop(){
        try {
            routeStopsDao.deleteRouteStop(selectedRouteStop.getStopId(), sessionBean.getSelectedRouteId());
            sessionBean.navigate("manage_route_stops");

        } catch (Exception ex) {
            
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));

            Logger.getLogger(ManageRouteStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
