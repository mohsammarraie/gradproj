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
import models.Stops;
import daos.StopsDao;
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "manageStopsBean")
@ViewScoped
public class ManageStopsBean implements Serializable {
    
    private Stops selectedStop;
    private final StopsDao stopsDao = new StopsDao();
    private ArrayList<Stops> stopsInfo; 
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageStopsBean(){}
    
       @PostConstruct
        public void init(){
            try {            
                stopsInfo = stopsDao.buildStopsInfo();            
            } catch (Exception ex) {
                Logger.getLogger(ManageStopsBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    public Stops getSelectedStop() {
        return selectedStop;
    }

    public void setSelectedStop(Stops selectedStop) {
        this.selectedStop = selectedStop;
    }

    public ArrayList<Stops> getStopsInfo() {
        return stopsInfo;
    }

    public void setStopsInfo(ArrayList<Stops> stopsInfo) {
        this.stopsInfo = stopsInfo;
    }

    public void saveSelectedItemId(){
        sessionBean.setSelectedItemId(selectedStop.getStopID());
    }
    
    public void deleteSelectedStop(){
        try {
            stopsDao.deleteStop(selectedStop.getStopID());
            sessionBean.navigate("manage_stops");

        } catch (Exception ex) {
         
            Logger.getLogger(ManageStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
