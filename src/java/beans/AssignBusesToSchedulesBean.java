/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.BusesDao;
import daos.BusesSchedulesDao;
import daos.RouteSchedulesDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusesSchedules;
import models.Buses;
import org.primefaces.context.RequestContext;
/**
 *
 * @author MOH
 */
@Named(value = "assignBusToScheduleBean")
@ViewScoped
public class AssignBusesToSchedulesBean implements Serializable{
    
    private ArrayList<BusesSchedules> busesSchedulesArray;
    private ArrayList<BusesSchedules> availableBusesArray;
    private int busId;
    private int routeId;
    private int scheduleId;
    private String assignedBus;
    BusesSchedules busesSchedules= new BusesSchedules();
    private final BusesSchedulesDao busesSchedulesDao = new BusesSchedulesDao();
    
    String error_message_header = "";
    String error_message_content = "";
    
     @Inject
    private SessionBean sessionBean;

    public AssignBusesToSchedulesBean() {
    }
     
       @PostConstruct
    public void init() {
        try {
            busesSchedulesArray = busesSchedulesDao.buildBusesSchedules();
            availableBusesArray = busesSchedulesDao.buildAvailableBuses();
            routeId = sessionBean.getSelectedRouteId();
            scheduleId= sessionBean.getSelectedScheduleId();
           
            
            if(scheduleId>0){
                busesSchedules =busesSchedulesDao.getBusesSchedules(routeId, scheduleId);
                busId=busesSchedules.getBusId();
                assignedBus = busesSchedules.getAssignedBus();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(AssignBusesToSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<BusesSchedules> getAvailableBusesArray() {
        return availableBusesArray;
    }

    public void setAvailableBusesArray(ArrayList<BusesSchedules> availableBusesArray) {
        this.availableBusesArray = availableBusesArray;
    }
    

    public ArrayList<BusesSchedules> getBusesSchedulesArray() {
        return busesSchedulesArray;
    }

    public void setBusesSchedulesArray(ArrayList<BusesSchedules> busesSchedulesArray) {
        this.busesSchedulesArray = busesSchedulesArray;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
    

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getAssignedBus() {
        return assignedBus;
    }

    public void setAssignedBus(String assignedBus) {
        this.assignedBus = assignedBus;
    }
    
    
    
     //check if there is an assigned driver. if found then disable delete button on assign_bus_to_bus.xhtml
    public boolean checkRemoveBusButton() {
        int i;
        boolean flag = false;
        for (i = 0; i < busesSchedulesArray.size(); i++) {
            if (scheduleId == busesSchedulesArray.get(i).getScheduleId() && routeId ==busesSchedulesArray.get(i).getRouteId()) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    // to display driver name and national id in manage buses table.
    public String displayBusesSchedules (int busesArrayrouteId, int busesArrayscheduleId, int x) {
        int i;
        boolean flag = false;
        for (i = 0; i < busesSchedulesArray.size(); i++) {
            if (busesArrayscheduleId == busesSchedulesArray.get(i).getScheduleId() && busesArrayrouteId==busesSchedulesArray.get(i).getRouteId()) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        if (flag) {
            
                return busesSchedulesArray.get(i).getAssignedBus();
           
        } else {
            if (x == 1) {
                return "Not Assigned";
            } else {
                return "غير معين";
            }

        }

    }
    
     
     public void deleteBusSchdedule() {
        try {
            busesSchedulesDao.deleteBusSchedule(routeId, scheduleId);
            sessionBean.navigate("manage_route_schedules");
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(AssignBusesToSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void saveBusSchedule() {
        try {
    
            if (checkRemoveBusButton()) {
                busesSchedulesDao.updateBusSchedule(busId, routeId, scheduleId);
            } else {
                busesSchedulesDao.insertBusSchedule(busId, routeId, scheduleId);
            }

              
//             

            sessionBean.navigate("manage_route_schedules");
        } catch (Exception ex) {
            

            error_message_header = "Error!";
            error_message_content = ex.getMessage();
            
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(AssignBusesToSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    
}
