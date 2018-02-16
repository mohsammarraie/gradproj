/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

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
import models.BusDriver;
import models.StudentTrip;
import models.StudentTripReview;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "studentReviewBean")
@ViewScoped
public class StudentReviewBean implements Serializable {

    private String studentTextReview;
    private int rating1;
    private int rating2;
    private int rating3;
    private int tripId;
    private String studentId;
    StudentTripReview studentTripReview = new StudentTripReview();
    private final StudentTripReviewDao studentTripReviewDao = new StudentTripReviewDao();
    private ArrayList<StudentTripReview> studentTripReviewsArray;
    String error_message_header = "";
    String error_message_content = "";
    
    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {
            tripId = sessionBean.getSelectedTripId();
            studentId = "20122502006";
            studentTripReviewsArray=studentTripReviewDao.buildStudentTripReviews(tripId);
            

        } catch (Exception ex) {

            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<StudentTripReview> getStudentTripReviewsArray() {
        return studentTripReviewsArray;
    }

    public void setStudentTripReviewsArray(ArrayList<StudentTripReview> studentTripReviewsArray) {
        this.studentTripReviewsArray = studentTripReviewsArray;
    }
    
    public int getTripId() {
        return tripId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getStudentTextReview() {
        return studentTextReview;
    }

    public void setStudentTextReview(String studentTextReview) {
        this.studentTextReview = studentTextReview;
    }

    public int getRating1() {
        return rating1;
    }

    public void setRating1(int rating1) {
        this.rating1 = rating1;
    }

    public int getRating2() {
        return rating2;
    }

    public void setRating2(int rating2) {
        this.rating2 = rating2;
    }

    public int getRating3() {
        return rating3;
    }

    public void setRating3(int rating3) {
        this.rating3 = rating3;
    }

    public void submitStudentTripReview() {
        try {

            studentTripReview.setReviewText(studentTextReview);
            studentTripReview.setRating1(rating1);
            studentTripReview.setRating2(rating2);
            studentTripReview.setRating3(rating3);
            studentTripReview.setTripId(tripId);
            studentTripReview.setStudentId(studentId);

            studentTripReviewDao.insertStudentTripReview(studentTripReview);
            sessionBean.navigateReviewPastTrips();
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();
             if (error_message_content.contains("ORA-00001: unique constraint (BUSES.REVIEWS_PK) violated")) {
                error_message_content = "You cannot review the same trip twice.";

            }

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(StudentReviewBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
