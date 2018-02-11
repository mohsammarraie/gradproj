package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import daos.TripsDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;

@Named(value = "driverMapBean")
@SessionScoped

public class DriverMapBean implements Serializable {

    private String latitude;
    private String longitude;
    TripsDao tripsDao = new TripsDao();
    
    public void saveCoordinates() {
        latitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lat");
        longitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lng");

        try {
            tripsDao.insertTripCoordinates(latitude, longitude);
        } catch (Exception ex) {
         
            Logger.getLogger(DriverMapBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("lat: " + latitude);
        System.out.println("long: " + longitude);
    }

}
