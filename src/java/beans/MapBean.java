package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value="mapBean")
@SessionScoped
    
public class MapBean implements Serializable {
    private String latitude;
    private String longitude;
    
    public void saveCoordinates(){
            latitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lat");
            longitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lng");
            System.out.println("lat: " + latitude);
            System.out.println("long: " + longitude);      
    }

    
}