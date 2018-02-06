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
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polygon;
/**
 *
 * @author MOH
 */
@Named(value = "studentMapBean")
@ViewScoped
public class StudentMapBean implements Serializable{
    
    private final StudentTripsDao studentTripsDao = new StudentTripsDao();
    private ArrayList<Stops>studentRouteSchedulesStopsArray;
    private ArrayList<StudentTrips>studentTrackMapArray;
    private MapModel model = new DefaultMapModel();
    private double lat;
    private double lng;

    
    @Inject 
    private SessionBean sessionBean;
    
         @PostConstruct
        public void init(){
            try {  
                
                studentRouteSchedulesStopsArray = studentTripsDao.buildStudentRouteStopsSchedules(sessionBean.getSelectedTripId());
                displayLongLatOnMap();
        
            } catch (Exception ex) {
                Logger.getLogger(StudentMapBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
        
        
    
    public ArrayList<Stops> getStudentRouteSchedulesStopsArray() {
        return studentRouteSchedulesStopsArray;
    }

    public void setStudentRouteSchedulesStopsArray(ArrayList<Stops> studentRouteSchedulesStopsArray) {
        this.studentRouteSchedulesStopsArray = studentRouteSchedulesStopsArray;
    }

    public ArrayList<StudentTrips> getStudentTrackMapArray() {
        return studentTrackMapArray;
    }

    public void setStudentTrackMapArray(ArrayList<StudentTrips> studentTrackMapArray) {
        this.studentTrackMapArray = studentTrackMapArray;
    }
        public MapModel getModel() {
        return this.model;
    }

    
    public void displayLongLatOnMap(){
        try {
            int i;
             lat = 0;
             lng = 0;
            studentTrackMapArray=studentTripsDao.buildStudentTrackMap(sessionBean.getSelectedTripId());
            for(i=0;i<studentTrackMapArray.size();i++){
                lat = Double.parseDouble(studentTrackMapArray.get(i).getLatitude());
                lng = Double.parseDouble(studentTrackMapArray.get(i).getLongtitude());
            }
            
            model.addOverlay(new Marker(new LatLng(lat, lng), ""));
 
        } catch (Exception ex) {
            Logger.getLogger(StudentMapBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
}
