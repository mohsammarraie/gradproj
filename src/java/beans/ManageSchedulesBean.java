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
import models.SchedulesInfo;
import daos.SchedulesDao;
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "manageSchedulesBean")
@ViewScoped
public class ManageSchedulesBean implements Serializable {
    
    private SchedulesInfo selectedSchedule;
    private final SchedulesDao schedulesDao = new SchedulesDao();
    private ArrayList<SchedulesInfo> schedulesInfo; 
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageSchedulesBean(){}
     
        @PostConstruct
      public void init(){
        try {            
            schedulesInfo = schedulesDao.buildSchedulesInfo();            
        } catch (Exception ex) {
            Logger.getLogger(ManageSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SchedulesInfo getSelectedSchedule() {
        return selectedSchedule;
    }

    public void setSelectedSchedule(SchedulesInfo selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    public ArrayList<SchedulesInfo> getSchedulesInfo() {
        return schedulesInfo;
    }


    public void setScheduleInfo(ArrayList<SchedulesInfo> schedulesInfo) {
        this.schedulesInfo = schedulesInfo;
    }


    public void saveSelectedItemId(){
        sessionBean.setSelectedItemId(selectedSchedule.getScheduleID());
    }
    
    public void deleteSelectedSchedule(){
        try {
            schedulesDao.deleteSchedule(selectedSchedule.getScheduleID());
            sessionBean.navigate("manage_schedules");

        } catch (Exception ex) {
            sessionBean.navigate("error_page");
            Logger.getLogger(ManageSchedulesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
