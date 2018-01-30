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
    
    private RouteSchedules selectedSchedule;
    private final RouteSchedulesDao routeSchedulesDao = new RouteSchedulesDao();
    private ArrayList<RouteSchedules> routeSchedulesArray; 
    private ArrayList<Stops> routeScheduleStop = new ArrayList<>();
    RouteSchedules routeSchedules = new RouteSchedules();
    Stops stop = new Stops();
     @Inject 
    private SessionBean sessionBean;

    public ManageRouteSchedulesBean() {
    }
     
      @PostConstruct
        public void init(){
            try {            
                routeSchedulesArray = routeSchedulesDao.buildRouteSchedules(sessionBean.getSelectedRouteId()); // table array (mother array)
                routeScheduleStop = routeSchedulesDao.buildRouteScheduleStops(sessionBean.getSelectedRouteId()); // array contains time
            } catch (Exception ex) {
                Logger.getLogger(ManageRouteSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public ArrayList<Stops> getRouteScheduleStop() {
        return routeScheduleStop;
    }

    public void setRouteScheduleStop(ArrayList<Stops> routeScheduleStop) {
        this.routeScheduleStop = routeScheduleStop;
    }

    
    public RouteSchedules getSelectedSchedule() {
        return selectedSchedule;
    }

    public void setSelectedSchedule(RouteSchedules selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    public ArrayList<RouteSchedules> getRouteSchedulesArray() {
        return routeSchedulesArray;
    }

    public void setRouteSchedulesArray(ArrayList<RouteSchedules> routeSchedulesArray) {
        this.routeSchedulesArray = routeSchedulesArray;
    }
        
        public void saveSelectedScheduleID(){
        sessionBean.setSelectedScheduleId(selectedSchedule.getScheduleId());
    }
      
      public void deleteSelectedRouteSchedule(){
        try {
            routeSchedulesDao.deleteRouteScheduleStops(selectedSchedule.getScheduleId(), sessionBean.getSelectedRouteId());
            routeSchedulesDao.deleteRouteSchedules(selectedSchedule.getScheduleId(), sessionBean.getSelectedRouteId());
            sessionBean.navigate("manage_route_schedules");

        } catch (Exception ex) {
         
            Logger.getLogger(ManageRouteSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
