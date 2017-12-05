/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.RouteSchedulesDao;
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
import models.RouteSchedules;
import models.RouteStops;

/**
 *
 * @author MOH
 */
@Named(value = "manageRouteSchedulesBean")
@ViewScoped
public class ManageRouteSchedulesBean implements Serializable{
    
    private RouteSchedules selectedRouteSchedule;
    private final RouteSchedulesDao routeSchedulesDao = new RouteSchedulesDao();
    private ArrayList<RouteSchedules> routeSchedulesArray; 
    
     @Inject 
    private SessionBean sessionBean;

    public ManageRouteSchedulesBean() {
    }
     
      @PostConstruct
        public void init(){
            try {            
                routeSchedulesArray = routeSchedulesDao.buildRouteSchedules(sessionBean.getSelectedRouteId());
            } catch (Exception ex) {
                Logger.getLogger(ManageRouteSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public RouteSchedules getSelectedRouteSchedule() {
        return selectedRouteSchedule;
    }

    public void setSelectedRouteSchedule(RouteSchedules selectedRouteSchedule) {
        this.selectedRouteSchedule = selectedRouteSchedule;
    }

    public ArrayList<RouteSchedules> getRouteSchedulesArray() {
        return routeSchedulesArray;
    }

    public void setRouteSchedulesArray(ArrayList<RouteSchedules> routeSchedulesArray) {
        this.routeSchedulesArray = routeSchedulesArray;
    }
        
        public void saveSelectedScheduleID(){
        sessionBean.setSelectedScheduleId(selectedRouteSchedule.getScheduleId());
    }
      
      public void deleteSelectedRouteSchedule(){
        try {
            routeSchedulesDao.deleteRouteScheduleTime(selectedRouteSchedule.getScheduleId(), sessionBean.getSelectedRouteId());
            sessionBean.navigate("manage_route_schedules");

        } catch (Exception ex) {
         
            Logger.getLogger(ManageRouteSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
