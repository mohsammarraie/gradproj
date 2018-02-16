/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.ReportStatusDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import daos.ReportsDao;
import java.util.Date;
import javax.faces.application.FacesMessage;
import models.Report;
import models.ReportStatus;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "manageReportsBean")
@ViewScoped
public class ManageReportsBean implements Serializable {

    private int routeId;
    private int driverId;
    private int busId;
    private int scheduleId;
    private String statusEn;
    private String statusAr;
    private int avgRating;
    private Date departureTime;
    private Date arrivalTime;
    private String scheduleTime;
    private String departureTimeStatusEn;
    private String departureTimeStatusAr;
    private String arrivalTimeStatusEn;
    private String arrivalTimeStatusAr;
    private ArrayList<ReportStatus> statusArray;
    private final ReportsDao reportsDao = new ReportsDao();
    private ArrayList<Report> reportSchedulesArray;
    private ArrayList<Report> reportAvgRatingArray;

    private Report selectedReport;
    private ArrayList<Report> resultReportsArray;
    private final ReportStatusDao reportStatusDao = new ReportStatusDao();
    
    String error_message_header = "";
    String error_message_content = "";
    
    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {
            reportAvgRatingArray=reportsDao.buildReportAvgRating();
            resultReportsArray = reportsDao.buildResultReports(avgRating,scheduleId,routeId, busId, driverId, departureTime, arrivalTime,statusEn,statusAr,departureTimeStatusEn,departureTimeStatusAr,arrivalTimeStatusEn,arrivalTimeStatusAr);

            statusArray = reportStatusDao.buildReportStatus();

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(StudentTripsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Report> getReportAvgRatingArray() {
        return reportAvgRatingArray;
    }

    public void setReportAvgRatingArray(ArrayList<Report> reportAvgRatingArray) {
        this.reportAvgRatingArray = reportAvgRatingArray;
    }
    
    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
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
    
    public String getDepartureTimeStatusEn() {
        return departureTimeStatusEn;
    }

    public void setDepartureTimeStatusEn(String departureTimeStatusEn) {
        this.departureTimeStatusEn = departureTimeStatusEn;
    }

    public String getDepartureTimeStatusAr() {
        return departureTimeStatusAr;
    }

    public void setDepartureTimeStatusAr(String departureTimeStatusAr) {
        this.departureTimeStatusAr = departureTimeStatusAr;
    }

    public String getArrivalTimeStatusEn() {
        return arrivalTimeStatusEn;
    }

    public void setArrivalTimeStatusEn(String arrivalTimeStatusEn) {
        this.arrivalTimeStatusEn = arrivalTimeStatusEn;
    }

    public String getArrivalTimeStatusAr() {
        return arrivalTimeStatusAr;
    }

    public void setArrivalTimeStatusAr(String arrivalTimeStatusAr) {
        this.arrivalTimeStatusAr = arrivalTimeStatusAr;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusAr() {
        return statusAr;
    }

    public void setStatusAr(String statusAr) {
        this.statusAr = statusAr;
    }

    public ArrayList<ReportStatus> getStatusArray() {
        return statusArray;
    }

    public void setStatusArray(ArrayList<ReportStatus> statusArray) {
        this.statusArray = statusArray;
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

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public ArrayList<Report> getResultReportsArray() {
        return resultReportsArray;
    }

    public void setResultReportsArray(ArrayList<Report> resultReportsArray) {
        this.resultReportsArray = resultReportsArray;
    }

    public ArrayList<Report> getReportSchedulesArray() {
        return reportSchedulesArray;
    }

    public void setReportSchedulesArray(ArrayList<Report> reportSchedulesArray) {
        this.reportSchedulesArray = reportSchedulesArray;
    }

    public Report getSelectedReport() {
        return selectedReport;
    }

    public void setSelectedReport(Report selectedReport) {
        this.selectedReport = selectedReport;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void saveSelectedTripId() {
        sessionBean.setSelectedTripId(selectedReport.getTripId());

    }

    public void reportsFilter() {

        try {
            resultReportsArray = reportsDao.buildResultReports(avgRating,scheduleId,routeId, busId, driverId, departureTime, arrivalTime,
                    statusEn,statusAr,departureTimeStatusEn,departureTimeStatusAr,arrivalTimeStatusEn,arrivalTimeStatusAr);

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageReportsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
       public void clearReportsFilter() {

        try {
            avgRating=0;
            scheduleId=0;
            routeId=0; 
            busId=0;
            driverId=0;
            departureTime=null; 
            arrivalTime=null;
            statusEn=null;
            statusAr=null;
            departureTimeStatusEn=null;departureTimeStatusAr=null;
            arrivalTimeStatusEn=null;
            arrivalTimeStatusAr=null;
            
            resultReportsArray = reportsDao.buildResultReports(avgRating,scheduleId,routeId, busId, driverId, departureTime, arrivalTime,
                    statusEn,statusAr,departureTimeStatusEn,departureTimeStatusAr,arrivalTimeStatusEn,arrivalTimeStatusAr);

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageReportsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
       public void setSchedulesTimesOnFilter(){
        try {
            reportSchedulesArray = reportsDao.buildReportSchedules(routeId);
            
            
        } catch (Exception ex) {
            Logger.getLogger(ManageReportsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

       
       }
       // to display avg rating column in reports table.
    public String displayAvgRatingOnReport(int arrayTripId, int x) {
        int i;
        String displayedAvgRatings=null;
        boolean flag = false;
        for (i = 0; i < reportAvgRatingArray.size(); i++) {
            if (arrayTripId == reportAvgRatingArray.get(i).getTripId()) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        if (flag) {
         
            displayedAvgRatings= Integer.toString(reportAvgRatingArray.get(i).getAvgRating());
            return displayedAvgRatings;
            
        } else {
            if (x == 1) {
                return "Not Available";
            } else {
                return "غير متاح";
            }

        }

    }

}
