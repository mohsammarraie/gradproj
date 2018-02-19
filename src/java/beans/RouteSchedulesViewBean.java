/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import daos.ReportsDao;
import daos.RouteSchedulesViewDao;
import java.util.Date;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import models.Report;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "routeSchedulesViewBean")
@ViewScoped
public class RouteSchedulesViewBean implements Serializable {

    private int routeId;
    private int busId;
    private int scheduleId;
    private Date departureTime;
    private Date arrivalTime;
    private String scheduleTime;
    private final ReportsDao reportsDao = new ReportsDao();
    private final RouteSchedulesViewDao routeSchedulesViewDao = new RouteSchedulesViewDao();
    private ArrayList<Report> reportSchedulesArray;
    private ArrayList<Report> allrouteSchedulesBusesArray;
    private ArrayList<Report> allRouteSchedulesArray;

    String error_message_header = "";
    String error_message_content = "";
    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {
            allRouteSchedulesArray = routeSchedulesViewDao.buildRouteSchedulesView();
            allrouteSchedulesBusesArray = routeSchedulesViewDao.buildRouteSchedulesBus();
            addBusInfoToaAllSchedules();
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(StudentTripsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Report> getAllRouteSchedulesArray() {
        return allRouteSchedulesArray;
    }

    public void setAllRouteSchedulesArray(ArrayList<Report> allRouteSchedulesArray) {
        this.allRouteSchedulesArray = allRouteSchedulesArray;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
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

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public ArrayList<Report> getAllrouteSchedulesBusesArray() {
        return allrouteSchedulesBusesArray;
    }

    public void setAllrouteSchedulesBusesArray(ArrayList<Report> allrouteSchedulesBusesArray) {
        this.allrouteSchedulesBusesArray = allrouteSchedulesBusesArray;
    }

    public ArrayList<Report> getReportSchedulesArray() {
        return reportSchedulesArray;
    }

    public void setReportSchedulesArray(ArrayList<Report> reportSchedulesArray) {
        this.reportSchedulesArray = reportSchedulesArray;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void routeSchedulesViewFilter() {

        try {
            allRouteSchedulesArray=routeSchedulesViewDao.buildResultAllRouteSchedulesFilter(scheduleId,
                    routeId, departureTime, arrivalTime);
            addBusInfoToaAllSchedules();
             busFilter();
            
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(ManageReportsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void clearRouteSchedulesViewFilter() {

        try {

            scheduleId = 0;
            routeId = 0;
            busId = 0;
            departureTime = null;
            arrivalTime = null;
            allRouteSchedulesArray=routeSchedulesViewDao.buildResultAllRouteSchedulesFilter(scheduleId,
                    routeId, departureTime, arrivalTime);
            addBusInfoToaAllSchedules();
            setSchedulesTimesOnFilter();
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(ManageReportsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setSchedulesTimesOnFilter() {
        try {
            reportSchedulesArray = reportsDao.buildReportSchedules(routeId);

        } catch (Exception ex) {
            Logger.getLogger(ManageReportsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addBusInfoToaAllSchedules() {
        int i ;
        int j ;
        for (j = 0; j < allRouteSchedulesArray.size(); j++) {

            for (i = 0; i < allrouteSchedulesBusesArray.size(); i++) {
                if (allRouteSchedulesArray.get(j).getRouteId() == allrouteSchedulesBusesArray.get(i).getRouteId()
                        && allRouteSchedulesArray.get(j).getScheduleId() == allrouteSchedulesBusesArray.get(i).getScheduleId()) {
                    allRouteSchedulesArray.get(j).setBusId(allrouteSchedulesBusesArray.get(i).getBusId());
                    allRouteSchedulesArray.get(j).setLicenseNumber(allrouteSchedulesBusesArray.get(i).getLicenseNumber());

                }

            }

        }

    }
     public void busFilter() {
    Iterator<Report> itr = allRouteSchedulesArray.iterator();
       
        if (busId > 0) {
            while (itr.hasNext()) {
                if(itr.next().getBusId() != busId){
                    itr.remove();
                }
            
            }
        }
      
    }

}
