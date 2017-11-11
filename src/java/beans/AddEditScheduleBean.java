/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.SchedulesDao;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.SchedulesInfo;
/**
 *
 * @author MOH
 */
@Named(value = "addEditScheduleBean")
@ViewScoped
public class AddEditScheduleBean implements Serializable {
    
    private final SchedulesDao schedulesDao = new SchedulesDao();

    private int scheduleID;
    private Date departureTime;
    private Date arrivalTime;

        @Inject
    private SessionBean sessionBean;
    
    public AddEditScheduleBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            scheduleID = sessionBean.getSelectedItemId();
         
            
            if(scheduleID > 0){
                SchedulesInfo schedulesInfo = schedulesDao.getSchedulesInfo(scheduleID);
           
                departureTime = schedulesInfo.getDepartureTime();
                arrivalTime = schedulesInfo.getArrivalTime();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
            public void saveSchedule() {
        try {
        
            SchedulesInfo schedulesInfo = new SchedulesInfo();
            schedulesInfo.setScheduleID(scheduleID);
            schedulesInfo.setDepartureTime(new Timestamp(departureTime.getTime()));
            schedulesInfo.setArrivalTime(new Timestamp(arrivalTime.getTime()));
            
            if (sessionBean.getSelectedItemId() > 0) {
                schedulesDao.updateSchedule(schedulesInfo);
            } else {
                schedulesDao.insertSchedule(schedulesInfo);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_schedules");
    }
    
}
