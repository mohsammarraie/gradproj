/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesSchedulesDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusSchedule;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "assignBusToScheduleBean")
@ViewScoped
public class AssignBusesToSchedulesBean implements Serializable {

    private ArrayList<BusSchedule> busesSchedulesArray;
    private ArrayList<BusSchedule> availableBusesArray;
    private int busId;
    private int routeId;
    private int scheduleId;
    private String assignedBus;
    BusSchedule busesSchedules = new BusSchedule();
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
            int y = 0;
            busesSchedulesArray = busesSchedulesDao.buildBusesSchedules();
            availableBusesArray = busesSchedulesDao.buildAvailableBuses();
            routeId = sessionBean.getSelectedRouteId();
            scheduleId = sessionBean.getSelectedScheduleId();

            displayBusesSchedules(routeId, scheduleId, y);

            if (scheduleId > 0) {
                busesSchedules = busesSchedulesDao.getBusesSchedules(routeId, scheduleId);

                if (busesSchedules != null) {
                    busId = busesSchedules.getBusId();
                    assignedBus = busesSchedules.getAssignedBus();

                }
            }

        } catch (Exception ex) {

            Logger.getLogger(AssignBusesToSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<BusSchedule> getAvailableBusesArray() {
        return availableBusesArray;
    }

    public void setAvailableBusesArray(ArrayList<BusSchedule> availableBusesArray) {
        this.availableBusesArray = availableBusesArray;
    }

    public ArrayList<BusSchedule> getBusesSchedulesArray() {
        return busesSchedulesArray;
    }

    public void setBusesSchedulesArray(ArrayList<BusSchedule> busesSchedulesArray) {
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

    public boolean setFlag() {
        int i;
        boolean flag = false;
        for (i = 0; i < busesSchedulesArray.size(); i++) {
            if (scheduleId == busesSchedulesArray.get(i).getScheduleId() && routeId == busesSchedulesArray.get(i).getRouteId()) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    // to display bus model and license number in manage buses table.
    public String displayBusesSchedules(int busesArrayrouteId, int busesArrayscheduleId, int x) {
        int i;
        boolean flag = false;
        for (i = 0; i < busesSchedulesArray.size(); i++) {
            if (busesArrayscheduleId == busesSchedulesArray.get(i).getScheduleId() && busesArrayrouteId == busesSchedulesArray.get(i).getRouteId()) {
                flag = true;
                busId = sessionBean.getSelectedBusId();
                break;
            } else {
                flag = false;
            }
        }
        if (flag) {
            sessionBean.setSelectedBusId(busesSchedulesArray.get(i).getBusId());
            return busesSchedulesArray.get(i).getAssignedBus();

        } else {
            sessionBean.setSelectedBusId(0);
            if (x == 1) {
                return "Not Assigned";
            } else {
                return "غير معين";
            }

        }

    }

    public void saveBusSchedule() {
        try {

            if (setFlag()) {
                busesSchedulesDao.updateBusSchedule(busId, routeId, scheduleId);
            } else {
                busesSchedulesDao.insertBusSchedule(busId, routeId, scheduleId);
            }

            sessionBean.navigateManageRouteSchedules();
        } catch (Exception ex) {

            error_message_header = "Error!";
            error_message_content = ex.getMessage();

              if(error_message_content.contains("ORA-20100: CONFLICT.")){
                error_message_content="The chosen bus is already assigned to other schedules that conflict in time with this schedule.";
            
            }
          

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(AssignBusesToSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
