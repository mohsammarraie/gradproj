/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;
import daos.RoutesDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.RoutesInfo;
/**
 *
 * @author MOH
 */
@Named(value = "addEditRouteBean")
@ViewScoped
public class AddEditRouteBean implements Serializable {
    
    private final RoutesDao routesDao = new RoutesDao();
    private int routeID;
    private String sourceAr;
    private String sourceEn;
    private String destinationAr;
    private String destinationEn;

    @Inject
    private SessionBean sessionBean;
    
    public AddEditRouteBean() {        
    }
        
    @PostConstruct
    public void init(){                
        try {
            routeID = sessionBean.getSelectedItemId();
         
            
            if(routeID > 0){
                RoutesInfo routesInfo = routesDao.getRoutesInfo(routeID);
           
                sourceAr = routesInfo.getSourceAr();
                sourceEn = routesInfo.getSourceEn();
                destinationAr = routesInfo.getDestinationAr();
                destinationEn = routesInfo.getDestinationEn();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditRouteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getSourceAr() {
        return sourceAr;
    }

    public void setSourceAr(String sourceAr) {
        this.sourceAr = sourceAr;
    }

    public String getSourceEn() {
        return sourceEn;
    }

    public void setSourceEn(String sourceEn) {
        this.sourceEn = sourceEn;
    }

    public String getDestinationAr() {
        return destinationAr;
    }

    public void setDestinationAr(String destinationAr) {
        this.destinationAr = destinationAr;
    }

    public String getDestinationEn() {
        return destinationEn;
    }

    public void setDestinationEn(String destinationEn) {
        this.destinationEn = destinationEn;
    }
    
        public void saveRoute() {
        try {
        
            RoutesInfo routesInfo = new RoutesInfo();
            routesInfo.setRouteID(routeID);
            routesInfo.setSourceAr(sourceAr);
            routesInfo.setSourceEn(sourceEn);
            routesInfo.setDestinationAr(destinationAr);
            routesInfo.setDestinationEn(destinationEn);
            
            
            if (sessionBean.getSelectedItemId() > 0) {
                routesDao.updateRoute(routesInfo);
            } else {
                routesDao.insertRoute(routesInfo);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditRouteBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_routes");
    }
    
}
