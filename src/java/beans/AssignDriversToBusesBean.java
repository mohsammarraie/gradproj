/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.BusesDriversDao;
import daos.DriversDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusesDrivers;
import models.Drivers;
import org.primefaces.context.RequestContext;
/**
 *
 * @author MOH
 */
@Named(value = "assignDriverToBusBean")
@ViewScoped
public class AssignDriversToBusesBean  implements Serializable{
    
    private ArrayList<BusesDrivers> busesDriversArray;
    private ArrayList<BusesDrivers> availableDriversArray;
    private int busId;
    private int driverId;
    private String firstNameEn;
    private String firstNameAr;
    private String lastNameEn;
    private String lastNameAr;  
    private String driverNameEn;
    private String driverNameAr;

    String error_message_header = "";
    String error_message_content = "";
    
    BusesDriversDao busesDriversDao = new BusesDriversDao();
    
    @Inject
    private SessionBean sessionBean;
    
    public AssignDriversToBusesBean(){        
    }
        
    @PostConstruct
    public void init(){                
        try {
            busId = sessionBean.getSelectedBusId();
            busesDriversArray =busesDriversDao.buildBusesDrivers();
            availableDriversArray = busesDriversDao.buildAvailableBusesDrivers();
            
            if(busId > 0){
              BusesDrivers busesDrivers = busesDriversDao.getBusesDrivers();
              //driverId = busesDrivers.getDriverId();
              driverNameEn = busesDrivers.getDriverNameEn();
              driverNameAr = busesDrivers.getDriverNameAr();
              //busesDriversArray =busesDriversDao.buildBusesDrivers();  
            }
        } catch (Exception ex) {
            Logger.getLogger(AssignDriversToBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<BusesDrivers> getAvailableDriversArray() {
        return availableDriversArray;
    }

    public void setAvailableDriversArray(ArrayList<BusesDrivers> availableDriversArray) {
        this.availableDriversArray = availableDriversArray;
    }
    
    
    public ArrayList<BusesDrivers> getBusesDriversArray() {
        return busesDriversArray;
    }

    public void setBusesDriversArray(ArrayList<BusesDrivers> busesDriversArray) {
        this.busesDriversArray = busesDriversArray;
    }

    public String getDriverNameEn() {
        return driverNameEn;
    }

    public void setDriverNameEn(String driverNameEn) {
        this.driverNameEn = driverNameEn;
    }

    public String getDriverNameAr() {
        return driverNameAr;
    }

    public void setDriverNameAr(String driverNameAr) {
        this.driverNameAr = driverNameAr;
    }
    
    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public String getFirstNameAr() {
        return firstNameAr;
    }

    public void setFirstNameAr(String firstNameAr) {
        this.firstNameAr = firstNameAr;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public String getLastNameAr() {
        return lastNameAr;
    }

    public void setLastNameAr(String lastNameAr) {
        this.lastNameAr = lastNameAr;
    }
    
    //check if there is an assigned driver. if found then disable delete button on assign_driver_to_bus.xhtml
    public boolean checkRemoveDriverButton() {
        int i;
        boolean flag = false;
        for (i = 0; i < busesDriversArray.size(); i++) {
            if (busId == busesDriversArray.get(i).getBusId()) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    // to display driver name and national id in manage buses table.
    public String displayDriversOnBuses(int busesArrayBusId, int x) {
        int i;
        boolean flag = false;
        for (i = 0; i < busesDriversArray.size(); i++) {
            if (busesArrayBusId == busesDriversArray.get(i).getBusId()) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        if (flag) {
            if (x == 1) {
                return busesDriversArray.get(i).getDriverNameEn();
            } else {
                return busesDriversArray.get(i).getDriverNameAr();
            }

        } else {
            if (x == 1) {
                return "Not Assigned";
            } else {
                return "غير معين";
            }

        }

    }
    
     public void deleteBusDrivers() {
        try {
            busesDriversDao.deleteBusDriver(busId);
            sessionBean.navigate("manage_buses");
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(ManageBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void saveBusDriver() {
        try {
            int i;
            int flag=0;
            for(i=0;i<busesDriversArray.size();i++){
                if (busId == busesDriversArray.get(i).getBusId()){
                    flag=1;
                    break;
                }
                else{
                flag=0;
                }
            }
            if (flag==1) {
                busesDriversDao.updateBusDriver(busId, driverId);
            } else {
                busesDriversDao.insertBusDriver(busId, driverId);
            }

              
//             

            sessionBean.navigate("manage_buses");
        } catch (Exception ex) {
            

            error_message_header = "Error!";
            error_message_content = ex.getMessage();
            
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, error_message_header, error_message_content));
            Logger.getLogger(AssignDriversToBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
}
