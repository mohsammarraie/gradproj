/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import daos.BusesDriversDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusDriver;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "assignDriverToBusBean")
@ViewScoped
public class AssignDriversToBusesBean implements Serializable {

    private ArrayList<BusDriver> busesDriversArray;
    private ArrayList<BusDriver> availableDriversArray;
    private int busId;
    private int driverId;
    private String firstNameEn;
    private String firstNameAr;
    private String lastNameEn;
    private String lastNameAr;
    private String driverNameEn;
    private String driverNameAr;

    String error_message_header = "";
    String errorMessageContent = "";

    BusesDriversDao busesDriversDao = new BusesDriversDao();

    @Inject
    private SessionBean sessionBean;
    private LangBean langBean;

    public AssignDriversToBusesBean() {
    }

    @PostConstruct
    public void init() {
        try {
            busId = sessionBean.getSelectedBusId();
            busesDriversArray = busesDriversDao.buildBusesDrivers();
            availableDriversArray = busesDriversDao.buildAvailableBusesDrivers();
            driverId = 0;

            if (busId > 0) {
                BusDriver busesDrivers = busesDriversDao.getBusesDrivers(busId);
                if (busesDrivers != null) {
                    driverId = busesDrivers.getDriverId();
                    driverNameEn = busesDrivers.getDriverNameEn();
                    driverNameAr = busesDrivers.getDriverNameAr();
                }
             
                //busesDriversArray =busesDriversDao.buildBusesDrivers();  
            }
        } catch (Exception ex) {
        
            Logger.getLogger(AssignDriversToBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getErrorMessageContent() {
        return errorMessageContent;
    }

    public void setErrorMessageContent(String errorMessageContent) {
        this.errorMessageContent = errorMessageContent;
    }
    
    public ArrayList<BusDriver> getAvailableDriversArray() {
        return availableDriversArray;
    }

    public void setAvailableDriversArray(ArrayList<BusDriver> availableDriversArray) {
        this.availableDriversArray = availableDriversArray;
    }

    public ArrayList<BusDriver> getBusesDriversArray() {
        return busesDriversArray;
    }

    public void setBusesDriversArray(ArrayList<BusDriver> busesDriversArray) {
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

    //cuurently not used
    public boolean setFlag() {
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

    public void saveBusDriver() {
        try {
            int i;
            int flag = 0;
            for (i = 0; i < busesDriversArray.size(); i++) {
                if (busId == busesDriversArray.get(i).getBusId()) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag == 1) {
                busesDriversDao.updateBusDriver(busId, driverId);
            } else {
                busesDriversDao.insertBusDriver(busId, driverId);
            }

            sessionBean.navigateManageBuses();
        } catch (Exception ex) {

            error_message_header = "Error!";
            errorMessageContent = ex.getMessage();
            
            if(errorMessageContent.contains("ORA-00001: unique constraint (BUSES.BUSES_DRIVERS_UK1) violated")){
                //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_assign_driver_bus').show();");
            
            }
            else{
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, errorMessageContent));
            }
            Logger.getLogger(AssignDriversToBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
