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
import models.Report;

/**
 *
 * @author MOH
 */
@Named(value = "manageReportsBean")
@ViewScoped
public class ManageReportsBean implements Serializable {

    private final ReportsDao reportsDao = new ReportsDao();
    private ArrayList<Report> reportsArray;
    private Report selectedReport;

    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {

            reportsArray = reportsDao.buildReports();

        } catch (Exception ex) {
            Logger.getLogger(StudentTripsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Report> getReportsArray() {
        return reportsArray;
    }

    public void setReportsArray(ArrayList<Report> reportsArray) {
        this.reportsArray = reportsArray;
    }

    public Report getSelectedReport() {
        return selectedReport;
    }

    public void setSelectedReport(Report selectedReport) {
        this.selectedReport = selectedReport;
    }

    public void saveSelectedTripId() {
        sessionBean.setSelectedTripId(selectedReport.getTripId());

    }

}
