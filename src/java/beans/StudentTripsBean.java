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
import daos.StudentTripsDao;
import models.StudentTrip;

/**
 *
 * @author MOH
 */
@Named(value = "studentTripsBean")
@ViewScoped
public class StudentTripsBean implements Serializable {

private final StudentTripsDao studentTripsDao = new StudentTripsDao();
private ArrayList<StudentTrip> studentTripsArray;
private StudentTrip selectedTrip;

@Inject
private SessionBean sessionBean;

@PostConstruct
public void init() {
    try {
        studentTripsArray = studentTripsDao.buildStudentTrips();

    } catch (Exception ex) {
        Logger.getLogger(StudentTripsBean.class.getName()).log(Level.SEVERE, null, ex);
    }
}

 

    public ArrayList<StudentTrip> getStudentTripsArray() {
        return studentTripsArray;
    }

    public void setStudentTripsArray(ArrayList<StudentTrip> studentTripsArray) {
        this.studentTripsArray = studentTripsArray;
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

}
