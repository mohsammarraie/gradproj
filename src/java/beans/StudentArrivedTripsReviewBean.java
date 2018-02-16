/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.ReportsDao;
import daos.StudentTripReviewDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import daos.StudentTripsDao;
import javax.faces.application.FacesMessage;
import models.Report;
import models.StudentTrip;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "studentArrivedTripsReviewBean")
@ViewScoped
public class StudentArrivedTripsReviewBean implements Serializable {

    private final StudentTripsDao studentTripsDao = new StudentTripsDao();
    private final StudentTripReviewDao studentTripReviewDao = new StudentTripReviewDao();
    private String studentId;
    private final ReportsDao reportsDao = new ReportsDao();
    private ArrayList<Report> reportAvgRatingArray;

    private ArrayList<StudentTrip> studentArrivedTripsArray;
    private StudentTrip selectedTrip;
    String error_message_header = "";
    String error_message_content = "";
    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {
            studentId = sessionBean.getStudentUserId();
            reportAvgRatingArray = reportsDao.buildReportAvgRating();

            studentArrivedTripsArray = studentTripsDao.buildStudentArrivedTrips();

        } catch (Exception ex) {
            Logger.getLogger(StudentTripsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Report> getReportAvgRatingArray() {
        return reportAvgRatingArray;
    }

    public void setReportAvgRatingArray(ArrayList<Report> reportAvgRatingArray) {
        this.reportAvgRatingArray = reportAvgRatingArray;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public ArrayList<StudentTrip> getStudentArrivedTripsArray() {
        return studentArrivedTripsArray;
    }

    public void setStudentArrivedTripsArray(ArrayList<StudentTrip> studentArrivedTripsArray) {
        this.studentArrivedTripsArray = studentArrivedTripsArray;
    }

    public StudentTrip getSelectedTrip() {
        return selectedTrip;
    }

    public void setSelectedTrip(StudentTrip selectedTrip) {
        this.selectedTrip = selectedTrip;
    }

    public void saveSelectedTripId() {
        sessionBean.setSelectedTripId(selectedTrip.getTripId());

    }

    public void checkTripReview() {
        try {
            boolean falg = studentTripReviewDao.checkStudentReview(selectedTrip.getTripId(), studentId);
            if (falg) {
                error_message_header = "Error!";
                error_message_content = "You cannot review the same trip twice.";

                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            } else {
                sessionBean.navigateStudentReview();
            }

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));

        }
    }

    public String displayReviewStatusOnStudentReview(int arrayTripId, int x) {
        int i;
        String displayedReviewStatus = null;
        boolean flag = false;
        for (i = 0; i < reportAvgRatingArray.size(); i++) {
            if (arrayTripId == reportAvgRatingArray.get(i).getTripId() && studentId.equals(reportAvgRatingArray.get(i).getStudentId())) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        if (flag) {
              if (x == 1) {
                displayedReviewStatus= "Reviewed";
            } else {
                displayedReviewStatus= "مقيمة";
            }
            return displayedReviewStatus;

        } else {
            if (x == 1) {
                return "Not Reviewed";
            } else {
                return "غير مقيمة";
            }

        }

    }

}
