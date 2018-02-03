///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
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
import java.util.Date;
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
//    private String stopNameAr;
//    private String stopNameEn;
//    private int stopOrder;
    
    private ArrayList<RouteSchedules> routeSchedulesArray;
    private ArrayList<Stops> routeScheduleStop = new ArrayList<>();
    private ArrayList<Stops> addedStops = new ArrayList<>();
    private ArrayList<RouteStops> routeStopsArray; 
    
    private final RouteSchedulesDao routeSchedulesDao = new RouteSchedulesDao();
    private final RouteStopsDao routeStopsDao = new RouteStopsDao();
    
    private int scheduleId;
    private int stopId;
    private int routeId;
    private Date time;
    
    
    
    String error_message_header = "";
    String error_message_content = "";
    
    RouteSchedules routeSchedules = new RouteSchedules();
    RouteStops stops = new RouteStops();
    
    @Inject
    private SessionBean sessionBean;
    
    public AddEditRouteScheduleBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
          
            routeId = sessionBean.getSelectedRouteId();
            routeSchedulesArray = routeSchedulesDao.buildRouteSchedules(routeId); //mother array (used here to set a flag)
            routeStopsArray = routeStopsDao.buildRouteStops(sessionBean.getSelectedRouteId()); // to display/iterate stops
            
            
            stopId = stops.getStopId();
            //to do here, stop id problem.
            if(scheduleId > 0){
                
               scheduleId = sessionBean.getSelectedScheduleId();
               routeScheduleStop = routeSchedulesDao.buildRouteScheduleStops(sessionBean.getSelectedRouteId());
              
               
            //routeSchedules = routeSchedulesDao.getRouteSchedules(routeId);
//            stopNameAr = routeSchedules.getStopNameAr();
//              stopNameEn = routeSchedules.getStopNameEn();
//              addTimeArray =routeSchedules.getScheduleTime();
//                
           }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<RouteStops> getRouteStopsArray() {
        return routeStopsArray;
    }

    public void setRouteStopsArray(ArrayList<RouteStops> routeStopsArray) {
        this.routeStopsArray = routeStopsArray;
    }
    
    
    public ArrayList<Stops> getAddedStops() {
        return addedStops;
    }

    public void setAddedStops(ArrayList<Stops> addedStops) {
        this.addedStops = addedStops;
    }
    
    public ArrayList<Stops> getRouteScheduleStop() {
        return routeScheduleStop;
    }

    public void setRouteScheduleStop(ArrayList<Stops> routeScheduleStop) {
        this.routeScheduleStop = routeScheduleStop;
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

    

    public void saveRouteSchedule() {
        try {
            int flag = 0;
            int i;    
            for (i=0; i<routeStopsArray.size()-1;i++){
                
                if(routeStopsArray.get(i).getTime().after(routeStopsArray.get(i+1).getTime()) || routeStopsArray.get(i).getTime().equals(routeStopsArray.get(i+1).getTime()) ){
                     flag = 1;
                     break;
                }
                else if(i == routeStopsArray.size() - 1) {
                    flag = 0;
                
                }
              
            }
           if(flag == 0){ 
                 if (sessionBean.getSelectedScheduleId() > 0) {
                        for (RouteStops r : routeStopsArray) {
                            routeSchedulesDao.updateRouteSchedule(r.getStopId(), sessionBean.getSelectedRouteId(), sessionBean.getSelectedScheduleId(), r.getTime());
                        }
                    } else {

                        routeSchedulesDao.insertRouteSchedule(sessionBean.getSelectedRouteId());

                        for (RouteStops r : routeStopsArray) {
                            routeSchedulesDao.insertRouteStopSchedule(r.getStopId(), sessionBean.getSelectedRouteId(), r.getTime());
                        }
                         
                    }
                 sessionBean.navigateManageRouteSchedules();
           }
            else {
                 error_message_header = "Error!";
                 error_message_content = "Please enter times in the right order (from top to bottom) ";
                 RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));

             }
            
            
            
            
//            int i;
//            for (i = 0; i < routeSchedulesArray.size(); i++) {
//                routeSchedules.setScheduleId(scheduleId);
//            routeSchedules.setStopId(stopId);
//                stopId = routeSchedulesArray.get(i).getStopId();
//                time = routeSchedulesArray.get(i).getAddTime();
//                routeSchedules.setScheduleTime(new Timestamp(addTime.getTime()));

//             addTimeArray.add(addTime);
                //           routeSchedulesDao.updateRouteSchedule(routeSchedules, stopId, sessionBean.getSelectedRouteId(),sessionBean.getSelectedRouteScheduleId()); 
                //           new Timestamp(routeSchedulesArray.get(scheduleId).getScheduleTime().getTime())
                
            

        } catch (Exception ex) {

            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
