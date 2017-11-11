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
import models.RoutesInfo;
import daos.RoutesDao;
import javax.inject.Named;
/**
 *
 * @author MOH
 */
@Named(value = "manageRoutesBean")
@ViewScoped
public class ManageRoutesBean implements Serializable {
    
    private RoutesInfo selectedRoute;
    private final RoutesDao routesDao = new RoutesDao();
    private ArrayList<RoutesInfo> routesInfo; 
    
    @Inject 
    private SessionBean sessionBean;
     
    public ManageRoutesBean(){}
     
        @PostConstruct
      public void init(){
        try {            
            routesInfo = routesDao.buildRoutesInfo();            
        } catch (Exception ex) {
            Logger.getLogger(ManageRoutesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RoutesInfo getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(RoutesInfo selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public ArrayList<RoutesInfo> getRoutesInfo() {
        return routesInfo;
    }

    public void setRoutesInfo(ArrayList<RoutesInfo> routesInfo) {
        this.routesInfo = routesInfo;
    }


    public void saveSelectedItemId(){
        sessionBean.setSelectedItemId(selectedRoute.getRouteID());
        sessionBean.setSelectedRouteID(selectedRoute.getRouteID());
    }
    
    public void deleteSelectedRoute(){
        try {
            routesDao.deleteRoute(selectedRoute.getRouteID());
            sessionBean.navigate("manage_routes");

        } catch (Exception ex) {
            Logger.getLogger(ManageRoutesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
