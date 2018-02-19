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
import models.Driver;
import daos.DriversDao;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author MOH
 */
@Named(value = "manageDriversBean")
@ViewScoped
public class ManageDriversBean implements Serializable {

    private Driver selectedDriver;
    private final DriversDao driverDao = new DriversDao();
    private ArrayList<Driver> driversArray;
    String error_message_header;
    String error_message_content;
    @Inject
    private SessionBean sessionBean;

    public ManageDriversBean() {
    }

    @PostConstruct
    public void init() {
        try {
            driversArray = driverDao.buildDriver();
        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(ManageDriversBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Driver selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public ArrayList<Driver> getDriversArray() {
        return driversArray;
    }

    public void setDriversArray(ArrayList<Driver> driversArray) {
        this.driversArray = driversArray;
    }

    public void saveSelectedDriverId() {
        sessionBean.setSelectedDriverId(selectedDriver.getDriverId());
    }

    public void deleteSelectedDriver() {
        boolean flag = driverDao.checkBusesDrivers(selectedDriver.getDriverId());

        try {

            if (flag) {
                  //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_delete_edit_driver').show();");
                
             
            } else {
                driverDao.deleteDriver(selectedDriver.getDriverId());
                sessionBean.navigateManageDrivers();
            }

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();
            if (error_message_content.contains("ORA-02292: integrity constraint (BUSES.BUSES_DRIVERS_FK2) violated - child record found")) {
                 //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_delete_edit_driver').show();");

            }

            Logger.getLogger(ManageDriversBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checklBusesDrivers() {
        boolean flag = driverDao.checkBusesDrivers(selectedDriver.getDriverId());

        try {
            if (flag) {
             //show error popup
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('popup_delete_edit_driver').show();");
            } else {
                sessionBean.navigateAddEditDriver();
            }

        } catch (Exception ex) {
            error_message_header = "Error!";
            error_message_content = ex.getMessage();

            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, error_message_header, error_message_content));
            Logger.getLogger(ManageRouteStopsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

}
