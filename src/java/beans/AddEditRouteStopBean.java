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
import javax.faces.application.FacesMessage;
import models.RouteStops;
import org.primefaces.context.RequestContext;
/**
 *
 * @author MOH
 */
@Named(value = "addEditRouteStopBean")
@ViewScoped
public class AddEditRouteStopBean implements Serializable{
    
    private ArrayList<RouteStops> routeStopsArray;
    private ArrayList<Stops> stopsArray;
    private final RouteStopsDao routeStopsDao = new RouteStopsDao();
    private final StopsDao stopsDao = new StopsDao();
    private int routeStopId;
    private int stopId;
    private String stopNameAr;
    private String stopNameEn;
    private int stopOrder;
    RouteStops routeStops = new RouteStops();
    String error_message_header = "";
    String error_message_content = "";

    @Inject
    private SessionBean sessionBean;
    
    public AddEditRouteStopBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            routeStopId = sessionBean.getSelectedRouteStopId();
            routeStopsArray = routeStopsDao.buildRouteStops(routeStopId);
            stopsArray =  stopsDao.buildStops();
            if(routeStopId > 0){
                routeStops = routeStopsDao.getRouteStops(routeStopId);
                stopId = routeStops.getStopId();
                stopNameAr = routeStops.getStopNameAr();
                stopNameEn = routeStops.getStopNameEn();
                stopOrder = routeStops.getStopOrder();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Stops> getStopsArray() {
        return stopsArray;
    }

    public void setStopsArray(ArrayList<Stops> stopsArray) {
        this.stopsArray = stopsArray;
    }

    
    public int getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }
    
    public ArrayList<RouteStops> getRouteStopsArray() {
        return routeStopsArray;
    }

    public int getRouteStopId() {
        return routeStopId;
    }

    public void setRouteStopId(int routeStopId) {
        this.routeStopId = routeStopId;
    }
    
    
    
    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
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
        
        
            routeStops.setStopId(routeStopId);
        
      
  
            if (sessionBean.getSelectedRouteStopId() > 0) {
                routeStopsDao.updateRouteStop(routeStopId, stopId, sessionBean.getSelectedRouteId(), stopOrder);
            } else {
                routeStopsDao.insertRouteStop(routeStopId, sessionBean.getSelectedRouteId(), stopOrder);
            }
            sessionBean.navigate("manage_route_stops");
        } catch (Exception ex) {
            
//            sessionBean.navigate("route_stop_error");
//                error_message_header= "خطأ";
//                error_message_content= "لايمكنك تعيين نفس نقطة الوقوف لنفس الطريق مرتين"; 

            error_message_header = "Error!";
            error_message_content = "You can't assign the same stop to the same route twice";
            
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
}
