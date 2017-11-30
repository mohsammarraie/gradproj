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
    private ArrayList<RouteSchedules> routeSchedulesInfo; 
    
     @Inject 
    private SessionBean sessionBean;

    public ManageRouteSchedulesBean() {
    }
     
      @PostConstruct
        public void init(){
            try {            
                routeSchedulesInfo = routeSchedulesDao.buildRouteSchedulesInfo(sessionBean.getSelectedRouteID());
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

    public ArrayList<RouteSchedules> getRouteSchedulesInfo() {
        return routeSchedulesInfo;
    }

    public void setRouteSchedulesInfo(ArrayList<RouteSchedules> routeSchedulesInfo) {
        this.routeSchedulesInfo = routeSchedulesInfo;
    }
        
        public void saveSelectedScheduleID(){
        sessionBean.setSelectedScheduleID(selectedRouteSchedule.getStopID());
    }
      
      public void deleteSelectedRouteSchedule(){
        try {
            routeSchedulesDao.deleteRouteSchedule(selectedRouteSchedule.getScheduleID(), sessionBean.getSelectedRouteID());
            sessionBean.navigate("manage_route_schedules");

        } catch (Exception ex) {
         
            Logger.getLogger(ManageRouteSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
