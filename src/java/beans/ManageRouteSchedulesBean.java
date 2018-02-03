/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesSchedulesDao;
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
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import models.BusesSchedules;
import models.RouteSchedules;
import models.RouteStops;
import org.primefaces.context.RequestContext;

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
    private ArrayList<BusesSchedules> busesSchedulesArray;
    private int routeId;
    
    private final BusesSchedulesDao busesSchedulesDao = new BusesSchedulesDao();
    RouteSchedules routeSchedules = new RouteSchedules();
    Stops stop = new Stops();
    
    String error_message_header = "";
    String error_message_content = "";
    
     @Inject 
    private SessionBean sessionBean;

    public ManageRouteSchedulesBean() {
    }
     
      @PostConstruct
        public void init(){
            try {  
                routeId = sessionBean.getSelectedRouteId();
                routeSchedulesArray = routeSchedulesDao.buildRouteSchedules(routeId); // table array (mother array)
                routeScheduleStop = routeSchedulesDao.buildRouteScheduleStops(routeId); // array contains time
                busesSchedulesArray = busesSchedulesDao.buildBusesSchedules();//to check remove bus button
            } catch (Exception ex) {
                Logger.getLogger(ManageRouteSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public ArrayList<BusesSchedules> getBusesSchedulesArray() {
        return busesSchedulesArray;
    }

    public void setBusesSchedulesArray(ArrayList<BusesSchedules> busesSchedulesArray) {
        this.busesSchedulesArray = busesSchedulesArray;
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
            routeSchedulesDao.deleteRouteScheduleStops(selectedSchedule.getScheduleId(), routeId);
            routeSchedulesDao.deleteRouteSchedules(selectedSchedule.getScheduleId(), routeId);
            sessionBean.navigateManageRouteSchedules();

        } catch (Exception ex) {
         
            Logger.getLogger(ManageRouteSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
       public void deleteBusSchdedule() {
        try {
            busesSchedulesDao.deleteBusSchedule(routeId, selectedSchedule.getScheduleId());
             sessionBean.navigateManageRouteSchedules();
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(AssignBusesToSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       //check if there is an assigned driver. if found then disable delete button on assign_bus_to_bus.xhtml
         public boolean checkRemoveBusButton() {
        int i;
        boolean flag = false;
        for (i = 0; i < busesSchedulesArray.size(); i++) {
            if (selectedSchedule.getScheduleId() == busesSchedulesArray.get(i).getScheduleId() && routeId ==busesSchedulesArray.get(i).getRouteId()) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }
}
