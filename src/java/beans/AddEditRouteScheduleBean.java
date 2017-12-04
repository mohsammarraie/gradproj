/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.RouteSchedulesDao;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import models.RouteSchedules;
import models.RouteStops;
import org.primefaces.context.RequestContext;
/**
 *
 * @author MOH
 */
@Named(value = "addEditRouteScheduleBean")
@ViewScoped
public class AddEditRouteScheduleBean implements Serializable{
     
    private ArrayList<RouteSchedules> routeSchedulesArray;
    
    private int scheduleId;
    private int stopId;
    private int routeId;
    private String stopNameAr;
    private String stopNameEn;
    private int stopOrder;
    private Timestamp time;
    private final RouteSchedulesDao routeSchedulesDao = new RouteSchedulesDao();
    RouteSchedules routeSchedules = new RouteSchedules();
    String error_message_header = "";
    String error_message_content = "";

    @Inject
    private SessionBean sessionBean;
    
    public AddEditRouteScheduleBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            scheduleId = sessionBean.getSelectedScheduleId();
            routeSchedulesArray = routeSchedulesDao.buildRouteSchedules(scheduleId); 
            if(scheduleId > 0){
                routeSchedules = routeSchedulesDao.getRouteSchedules(routeId);
                stopId = routeSchedules.getStopId();
                stopNameAr = routeSchedules.getStopNameAr();
                stopNameEn = routeSchedules.getStopNameEn();
                stopOrder = routeSchedules.getStopOrder();
                routeId = routeSchedules.getRouteId();
                time =routeSchedules.getTime();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<RouteSchedules> getRouteSchedulesArray() {
        return routeSchedulesArray;
    }

    public void setRouteSchedulesArray(ArrayList<RouteSchedules> routeSchedulesArray) {
        this.routeSchedulesArray = routeSchedulesArray;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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
    

        public void saveRouteSchedule() {
        try {
        
        
            routeSchedules.setScheduleId(scheduleId);
        
      
  
            if (sessionBean.getSelectedRouteStopId() > 0) {
                routeSchedulesDao.updateRouteSchedule(time, stopId, sessionBean.getSelectedRouteId()); 
                
            } else {
                routeSchedulesDao.insertRouteSchedule(stopId, sessionBean.getSelectedRouteId(),scheduleId,time);
            }
            sessionBean.navigate("manage_route_schedules");
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
