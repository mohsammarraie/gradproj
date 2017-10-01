/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.DriverDao;
import daos.BusesDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.BusesInfo;
import models.DriverInfo;
/**
 *
 * @author MOH
 */
@Named(value = "addEditBusesBean")
@ViewScoped
public class AddEditBusesBean implements Serializable{
    private ArrayList<DriverInfo> driverInfos;
    private final DriverDao driverDao = new DriverDao();
    private final BusesDao busesDao = new BusesDao();
    private int busID;
    private int driverID;
//    private String nameEn;
//    private String nameAr;
    private String departureTime;
    private String arrivalTime;
    private String routeEn;
    private String routeAr;
    private String licensePlate;
    private int capacity;

        @Inject
    private SessionBean sessionBean;
    
    public AddEditBusesBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            busID = sessionBean.getSelectedItemId();
            driverInfos = driverDao.buildDriverInfo();
            
            if(busID > 0){
                BusesInfo busesInfo = busesDao.getBusesInfo(busID);
                departureTime = busesInfo.getDepartureTime();
                arrivalTime = busesInfo.getArrivalTime();
                routeEn = busesInfo.getRouteEn();
                routeAr = busesInfo.getRouteAr();
                licensePlate = busesInfo.getLicensePlate();
                capacity = busesInfo.getCapacity();
                driverID = busesInfo.getDriverInfo().getDriverID();
//                nameEn = busesInfo.getDriverInfo().getNameEn();
//                nameAr = busesInfo.getDriverInfo().getNameAr();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<DriverInfo> getDriverInfos() {
        return driverInfos;
    }

//    public String getNameEn() {
//        return nameEn;
//    }
//
//    public void setNameEn(String nameEn) {
//        this.nameEn = nameEn;
//    }
//
//    public String getNameAr() {
//        return nameAr;
//    }
//
//    public void setNameAr(String nameAr) {
//        this.nameAr = nameAr;
//    }
    
    
    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getRouteEn() {
        return routeEn;
    }

    public void setRouteEn(String routeEn) {
        this.routeEn = routeEn;
    }

    public String getRouteAr() {
        return routeAr;
    }

    public void setRouteAr(String routeAr) {
        this.routeAr = routeAr;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
        public void saveBus() {
        try {
            BusesInfo busesInfo = new BusesInfo();
//            DriverInfo driverInfo = new DriverInfo();
            DriverInfo driverInfoID = driverInfos.get(driverID - 1);
            busesInfo.setBusID(busID);
            busesInfo.setDriverInfo(driverInfoID);
            busesInfo.setDepartureTime(departureTime);
            busesInfo.setArrivalTime(arrivalTime);
            busesInfo.setRouteEn(routeEn);
            busesInfo.setRouteAr(routeAr);
            busesInfo.setLicensePlate(licensePlate);
            busesInfo.setCapacity(capacity);
//            driverInfo.setNameEn(nameEn);
//            driverInfo.setNameAr(nameAr);
            if (sessionBean.getSelectedItemId() > 0) {
                busesDao.updateBus(busesInfo);
            } else {
                busesDao.insertBus(busesInfo);
//                driverDao.insertDriver(driverInfo);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditBusesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_buses");
    }
    
}
