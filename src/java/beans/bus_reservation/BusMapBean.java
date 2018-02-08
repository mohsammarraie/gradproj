package beans.bus_reservation;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polygon;

@Named("busmapBean")
@ViewScoped
public class BusMapBean implements Serializable {        
    private double newLat = 31.777067;
    private double newLng = 35.802555;
    private double lat = 31.777067;
    private double lng = 35.802555;
    private LatLng coord1 = new LatLng(lat, lng);
    private MapModel model = new DefaultMapModel();

    public BusMapBean() {        
        model.addOverlay(new Marker(new LatLng(lat, lng), "GJU"));
    }

    public MapModel getModel() {
        return this.model;
    }

    public double getNewLat() {
        return newLat;
    }

    public void setNewLat(double newLat) {
        this.newLat = newLat;
    }

    public double getNewLng() {
        return newLng;
    }

    public void setNewLng(double newLng) {
        this.newLng = newLng;
    }        

    public void update() {
        newLat = lat + 0.01;
        newLng = lng + 0.01;
        model.addOverlay(new Marker(new LatLng(lat, lng), "GJU"));

        LatLng coord2 = new LatLng(newLat, newLng);
        Polygon polygon = new Polygon();
        polygon.getPaths().add(coord1);
        polygon.getPaths().add(coord2);
        polygon.setStrokeColor("#FF9900");
        polygon.setFillColor("#FF9900");
        polygon.setStrokeOpacity(0.7);
        polygon.setFillOpacity(0.7);
        model.addOverlay(polygon);
    }
    
    public void updateMarker() {        
        model.addOverlay(new Marker(new LatLng(newLat, newLng), "me"));
        
        LatLng coord2 = new LatLng(newLat, newLng);
        Polygon polygon = new Polygon();
        polygon.getPaths().add(coord1);
        polygon.getPaths().add(coord2);
        polygon.setStrokeColor("#FF9900");
        polygon.setFillColor("#FF9900");
        polygon.setStrokeOpacity(0.7);
        polygon.setFillOpacity(0.7);
        model.addOverlay(polygon);
    }
}
