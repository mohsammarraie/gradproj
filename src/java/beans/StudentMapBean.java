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
import models.Stops;
import models.StudentTrips;
/**
 *
 * @author MOH
 */
@Named(value = "studentMapBean")
@ViewScoped
public class StudentMapBean implements Serializable{
    
    private final StudentTripsDao studentTripsDao = new StudentTripsDao();
    private ArrayList<Stops>studentRouteSchedulesStopsArray;
    Stops stop = new Stops();
    
    @Inject 
    private SessionBean sessionBean;
    
         @PostConstruct
        public void init(){
            try {  
              
                
                studentRouteSchedulesStopsArray = studentTripsDao.buildStudentRouteStopsSchedules(sessionBean.getSelectedTripId());
        
            } catch (Exception ex) {
                Logger.getLogger(StudentTripsBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public ArrayList<Stops> getStudentRouteSchedulesStopsArray() {
        return studentRouteSchedulesStopsArray;
    }

    public void setStudentRouteSchedulesStopsArray(ArrayList<Stops> studentRouteSchedulesStopsArray) {
        this.studentRouteSchedulesStopsArray = studentRouteSchedulesStopsArray;
    }

    
    
    
}
