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
import models.DriverSchedules;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import daos.StudentTripsDao;
import models.StudentTrips;
/**
 *
 * @author MOH
 */
@Named(value = "studentTripsBean")
@ViewScoped
public class StudentTripsBean implements Serializable{
    
    private final StudentTripsDao studentTripsDao = new StudentTripsDao();
    private ArrayList<StudentTrips> studentTripsArray; 
    private StudentTrips selectedTrip;
    
     @Inject 
    private SessionBean sessionBean;
     
        @PostConstruct
        public void init(){
            try {  
              
                
                studentTripsArray = studentTripsDao.buildStudentTrips();
        
            } catch (Exception ex) {
                Logger.getLogger(StudentTripsBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    public ArrayList<StudentTrips> getStudentTripsArray() {
        return studentTripsArray;
    }

    public void setStudentTripsArray(ArrayList<StudentTrips> studentTripsArray) {
        this.studentTripsArray = studentTripsArray;
    }

    public StudentTrips getSelectedTrip() {
        return selectedTrip;
    }

    public void setSelectedTrip(StudentTrips selectedTrip) {
        this.selectedTrip = selectedTrip;
    }
    public void saveSelectedTripId(){
        sessionBean.setSelectedTripId(selectedTrip.getTripId());

    }
   
    
}
