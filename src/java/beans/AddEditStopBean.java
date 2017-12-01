/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Stops;
import daos.StopsDao;
/**
 *
 * @author MOH
 */
@Named(value = "addEditStopBean")
@ViewScoped
public class AddEditStopBean implements Serializable {
    
     private final StopsDao stopsDao = new StopsDao();
    private int stopId;
    private String stopNameAr;
    private String stopNameEn;


    @Inject
    private SessionBean sessionBean;
    
    public AddEditStopBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            stopId = sessionBean.getSelectedStopId();
         
            
            if(stopId > 0){
                Stops stopsArray = stopsDao.getStops(stopId);
           
                stopNameAr = stopsArray.getStopNameAr();
                stopNameEn = stopsArray.getStopNameEn();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getStopNameAr() {
        return stopNameAr;
    }

    public void setStopNameAr(String stopNameAr) {
        this.stopNameAr = stopNameAr;
    }

    public String getStopNameEn() {
        return stopNameEn;
    }

    public void setStopNameEn(String stopNameEn) {
        this.stopNameEn = stopNameEn;
    }
        

        public void saveStop() {
        try {
        
            Stops stops = new Stops();
            stops.setStopId(stopId);
            stops.setStopNameAr(stopNameAr);
            stops.setStopNameEn(stopNameEn);
      
  
            if (sessionBean.getSelectedStopId() > 0) {
                stopsDao.updateStop(stops);
            } else {
                stopsDao.insertStop(stops);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_stops");
    }
    
}
