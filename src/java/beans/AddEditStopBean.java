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
import models.StopsInfo;
import daos.StopsDao;
/**
 *
 * @author MOH
 */
@Named(value = "addEditStopBean")
@ViewScoped
public class AddEditStopBean implements Serializable {
    
     private final StopsDao stopsDao = new StopsDao();
    private int stopID;
    private String stopNameAr;
    private String stopNameEn;


    @Inject
    private SessionBean sessionBean;
    
    public AddEditStopBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            stopID = sessionBean.getSelectedItemId();
         
            
            if(stopID > 0){
                StopsInfo stopsInfo = stopsDao.getStopsInfo(stopID);
           
                stopNameAr = stopsInfo.getStopNameAr();
                stopNameEn = stopsInfo.getStopNameEn();
                
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getStopID() {
        return stopID;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
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
        
            StopsInfo stopsInfo = new StopsInfo();
            stopsInfo.setStopID(stopID);
            stopsInfo.setStopNameAr(stopNameAr);
            stopsInfo.setStopNameEn(stopNameEn);
      
  
            if (sessionBean.getSelectedItemId() > 0) {
                stopsDao.updateStop(stopsInfo);
            } else {
                stopsDao.insertStop(stopsInfo);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditStopBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_stops");
    }
    
}
