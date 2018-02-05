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
import daos.DriverSchedulesDao;
import daos.TripsDao;
import models.Stops;
import models.Trips;

/**
 *
 * @author MOH
 */
@Named(value = "tripsBean")
@ViewScoped
public class TripsBean implements Serializable {

//    private final DriverSchedulesDao driverSchedulesDao = new DriverSchedulesDao();
    
    private final TripsDao tripsDao = new TripsDao();
    private ArrayList<Stops> driverRouteSchedulesStopsArray;
    Trips trip;
    
//    private ArrayList<DriverSchedules> driverSchedulesArray;
   
    
    @Inject
    private SessionBean sessionBean;

    @PostConstruct
    public void init() {
        try {
            driverRouteSchedulesStopsArray=tripsDao.buildDriverRouteStopsSchedules(sessionBean.getSelectedRouteId(), sessionBean.getSelectedScheduleId());
//            driverSchedulesArray = driverSchedulesDao.buildDriverSchedules();
                
         

        } catch (Exception ex) {
            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Stops> getDriverRouteSchedulesStopsArray() {
        return driverRouteSchedulesStopsArray;
    }

    public void setDriverRouteSchedulesStopsArray(ArrayList<Stops> driverRouteSchedulesStopsArray) {
        this.driverRouteSchedulesStopsArray = driverRouteSchedulesStopsArray;
    }
    
    public void cancelTrip(){
        try{
            tripsDao.updateTripCancel();
        
         } catch (Exception ex) {
            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        sessionBean.navigateDriverSchedules();
    }
    
     public void endTrip(){
        try{
            tripsDao.updateTripEnd();
        
         } catch (Exception ex) {
            Logger.getLogger(DriverSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        sessionBean.navigateDriverSchedules();
    }
            
}

