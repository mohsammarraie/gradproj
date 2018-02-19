/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesSchedulesDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusSchedule;
import models.BusScheduleConflict;
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
     private Date departureTime;
    private Date arrivalTime;
    private String sourceAr;
    private String sourceEn;
    private String destinationAr;
    private String destinationEn;
    private String routeCode;
    private String licenseNumber;

    
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getSourceAr() {
        return sourceAr;
    }

    public void setSourceAr(String sourceAr) {
        this.sourceAr = sourceAr;
    }

    public String getSourceEn() {
        return sourceEn;
    }

    public void setSourceEn(String sourceEn) {
        this.sourceEn = sourceEn;
    }

    public String getDestinationAr() {
        return destinationAr;
    }

    public void setDestinationAr(String destinationAr) {
        this.destinationAr = destinationAr;
    }

    public String getDestinationEn() {
        return destinationEn;
    }

    public void setDestinationEn(String destinationEn) {
        this.destinationEn = destinationEn;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
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
            String errorMessageArray[]= error_message_content.split("");
           routeId= Integer.parseInt(errorMessageArray[11]);
           scheduleId=Integer.parseInt(errorMessageArray[12]);
            try {
                BusScheduleConflict busScheduleConflict = busesSchedulesDao.getBusesSchedulesConflicts(routeId, scheduleId, busId);
                    arrivalTime=busScheduleConflict.getArrivalTime();
                    departureTime=busScheduleConflict.getDepartureTime();
                    destinationAr=busScheduleConflict.getDestinationAr();
                    destinationEn=busScheduleConflict.getDestinationEn();
                    sourceAr=busScheduleConflict.getSourceAr();
                    sourceEn=busScheduleConflict.getSourceEn();
                    routeCode=busScheduleConflict.getRouteCode();
                    licenseNumber=busScheduleConflict.getLicenseNumber();
            
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_bus_schedule_conflict').show();");
                
               
            } catch (Exception ex1) {
                Logger.getLogger(AssignBusesToSchedulesBean.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(AssignBusesToSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
