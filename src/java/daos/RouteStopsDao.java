/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.RouteStop;

/**
 *
 * @author MOH
 */
public class RouteStopsDao extends ConnectionDao {

    public ArrayList<RouteStop> buildRouteStops(int routeId) throws Exception {
        ArrayList<RouteStop> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT * FROM BUSES.STOPS JOIN"
                    + " BUSES.ROUTES_STOPS ON BUSES.STOPS.STOP_ID = BUSES.ROUTES_STOPS.STOP_ID"
                    + " JOIN BUSES.ROUTES ON "
                    + " BUSES.ROUTES.ROUTE_ID = BUSES.ROUTES_STOPS.ROUTE_ID"
                    + " WHERE BUSES.ROUTES.ROUTE_ID=? ORDER BY STOP_ORDER";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateRouteStops(rs));

            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    private RouteStop populateRouteStops(ResultSet rs) throws SQLException {
        RouteStop stops = new RouteStop();

        stops.setStopId(rs.getInt("STOP_ID"));
        stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
        stops.setStopNameAr(rs.getString("STOP_NAME_AR"));
        stops.setStopOrder(rs.getInt("STOP_ORDER"));
        stops.setRouteCode(rs.getString("ROUTE_CODE"));
        stops.setSourceEn(rs.getString("SOURCE_EN"));
        stops.setSourceAr(rs.getString("SOURCE_AR"));
        stops.setDestinationEn(rs.getString("DESTINATION_EN"));
        stops.setDestinationAr(rs.getString("DESTINATION_AR"));
        stops.setRouteActive(rs.getInt("ROUTE_ACTIVE"));
        return stops;
    }

    public void insertRouteStop(int stopId, int routeId, int stopOrder) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.ROUTES_STOPS(STOP_ID,"
                    + " ROUTE_ID,"
                    + " STOP_ORDER)"
                    + " VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, stopId);
            ps.setInt(2, routeId);
            ps.setInt(3, stopOrder);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateRouteStop(int routeStopId, int stopId, int routeId, int stopOrder) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.ROUTES_STOPS SET"
                    + " STOP_ID=?,"
                    + " STOP_ORDER=?"
                    + " WHERE STOP_ID=? AND ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, routeStopId);
            ps.setInt(2, stopOrder);
            ps.setInt(3, stopId);
            ps.setInt(4, routeId);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteRouteStop(int stopId, int routeId) throws Exception {
        Connection conn = getConnection();

        try {
            String sql = "DELETE FROM BUSES.ROUTES_STOPS WHERE STOP_ID=? AND ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, stopId);
            ps.setInt(2, routeId);
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public RouteStop getRouteStops(int stopId) throws Exception {
        try {
            RouteStop stops = null;
            Connection conn = getConnection();

            String sql = "SELECT * FROM BUSES.ROUTES_STOPS,"
                    + " BUSES.ROUTES, BUSES.STOPS WHERE ROUTES_STOPS.STOP_ID=? AND"
                    + " ROUTES.ROUTE_ID = ROUTES_STOPS.ROUTE_ID";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, stopId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                stops = populateRouteStops(rs);
                stops.setStopNameEn(rs.getString("STOP_NAME_EN"));
                stops.setStopNameAr(rs.getString("STOP_NAME_AR"));
                stops.setStopOrder(rs.getInt("STOP_ORDER"));
            }

            rs.close();
            ps.close();

            return stops;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
