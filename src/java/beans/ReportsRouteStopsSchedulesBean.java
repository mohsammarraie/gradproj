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
import daos.ReportsRouteStopsSchedulesDao;
import java.util.Date;
import javax.faces.application.FacesMessage;
import models.Report;
import models.ReportStatus;
import models.Stop;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "reportsRouteStopsSchedulesBean")
@ViewScoped
public class ReportsRouteStopsSchedulesBean implements Serializable{
    
    private ArrayList<Stop> reportsRouteStopsSchedulesArray;
    private int tripId;
    private final ReportsRouteStopsSchedulesDao reportsRouteStopsSchedulesDao = new ReportsRouteStopsSchedulesDao();
    
    String error_message_header = "";
    String error_message_content = "";
    
      @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {
            tripId=sessionBean.getSelectedTripId();
            reportsRouteStopsSchedulesArray = reportsRouteStopsSchedulesDao.buildReportsRouteStopsSchedules(tripId);


        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ReportsRouteStopsSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Stop> getReportsRouteStopsSchedulesArray() {
        return reportsRouteStopsSchedulesArray;
    }

    public void setReportsRouteStopsSchedulesArray(ArrayList<Stop> reportsRouteStopsSchedulesArray) {
        this.reportsRouteStopsSchedulesArray = reportsRouteStopsSchedulesArray;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    
    
}
