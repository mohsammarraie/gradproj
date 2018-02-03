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
    private ArrayList<Stops> stopsArray; 
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageStopsBean(){}
    
       @PostConstruct
        public void init(){
            try {            
                stopsArray = stopsDao.buildStops();            
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

    public ArrayList<Stops> getStopsArray() {
        return stopsArray;
    }

    public void setStopsArray(ArrayList<Stops> stopsArray) {
        this.stopsArray = stopsArray;
    }

    public void saveSelectedStopId(){
        sessionBean.setSelectedStopId(selectedStop.getStopId());
    }
    
    public void deleteSelectedStop(){
        try {
            stopsDao.deleteStop(selectedStop.getStopId());
            sessionBean.navigateManageStops();

        } catch (Exception ex) {
         
            Logger.getLogger(ManageStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
