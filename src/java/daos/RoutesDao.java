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
import java.util.HashMap;
import models.Route;

/**
 *
 * @author MOH
 */
public class RoutesDao extends ConnectionDao {

    public ArrayList<Route> buildRoutes() throws Exception {
        ArrayList<Route> list = new ArrayList<>();
        Connection conn = getConnection();

        try {
            String sql = "SELECT ROUTE_ID,ROUTE_CODE,ROUTE_ACTIVE,SOURCE_EN, SOURCE_AR,DESTINATION_EN, DESTINATION_AR,"
                    + " CONCAT(CONCAT(CONCAT(CONCAT(SOURCE_EN,' - '), DESTINATION_EN),' , '),ROUTE_CODE) AS ROUTE_NAME_EN,"
                    + " CONCAT(CONCAT(CONCAT(CONCAT(SOURCE_AR,' - '), DESTINATION_AR),' , '),ROUTE_CODE) AS ROUTE_NAME_AR"
                    + " FROM"
                    + " BUSES.ROUTES ORDER BY ROUTE_ID";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateRoutes(rs));
            }

            rs.close();
            ps.close();

            return list;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }


    private Route populateRoutes(ResultSet rs) throws SQLException {
        Route routes = new Route();

        routes.setRouteId(rs.getInt("ROUTE_ID"));
        routes.setSourceEn(rs.getString("SOURCE_EN"));
        routes.setSourceAr(rs.getString("SOURCE_AR"));
        routes.setDestinationEn(rs.getString("DESTINATION_EN"));
        routes.setDestinationAr(rs.getString("DESTINATION_AR"));
        routes.setRouteCode(rs.getString("ROUTE_CODE"));
        routes.setRouteActive(rs.getInt("ROUTE_ACTIVE"));
        
        String givenString=rs.getString("ROUTE_NAME_EN").toLowerCase();
//        String[] arr = givenString.split(" ");
//        StringBuilder sb = new StringBuilder();
//
//        for(int i = 0; i < arr.length; i++) {
//        sb.append(Character.toUpperCase(arr[i].charAt(0)))
//            .append(arr[i].substring(1)).append(" ");
//    }          
//     givenString=sb.toString().trim();
    
        routes.setRouteNameEn(givenString);
        routes.setRouteNameAr(rs.getString("ROUTE_NAME_AR"));
        
        
        return routes;
    }

    public void insertRoute(Route routes) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "INSERT INTO BUSES.ROUTES (ROUTE_ID,"
                    + " SOURCE_EN,"
                    + " SOURCE_AR,"
                    + " DESTINATION_EN,"
                    + " DESTINATION_AR,"
                    + " ROUTE_CODE,"
                    + " ROUTE_ACTIVE)"
                    + " VALUES ((select max(ROUTE_ID) from ROUTES)+1,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, routes.getSourceEn());
            ps.setString(2, routes.getSourceAr());
            ps.setString(3, routes.getDestinationEn());
            ps.setString(4, routes.getDestinationAr());
            ps.setString(5, routes.getRouteCode());
            ps.setInt(6, routes.getRouteActive());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void updateRoute(Route routes) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE BUSES.ROUTES SET"
                    + " SOURCE_EN=?,"
                    + " SOURCE_AR=?,"
                    + " DESTINATION_EN=?,"
                    + " DESTINATION_AR=?,"
                    + " ROUTE_CODE=?,"
                    + " ROUTE_ACTIVE=?"
                    + " WHERE ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, routes.getSourceEn());
            ps.setString(2, routes.getSourceAr());
            ps.setString(3, routes.getDestinationEn());
            ps.setString(4, routes.getDestinationAr());
            ps.setString(5, routes.getRouteCode());
            ps.setInt(6, routes.getRouteActive());
            ps.setInt(7, routes.getRouteId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void deleteRoute(int routeId) throws Exception {
        Connection conn = getConnection();

        try {
            String sql = "DELETE FROM BUSES.ROUTES WHERE ROUTE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Route getRoutes(int routeId) throws Exception {
        try {
            Route routes = null;
            Connection conn = getConnection();

            String sql = "SELECT * FROM BUSES.ROUTES"
                    + " WHERE ROUTE_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                routes = populateRoutes(rs);
                routes.setSourceEn(rs.getString("SOURCE_EN"));
                routes.setSourceAr(rs.getString("SOURCE_AR"));
                routes.setDestinationEn(rs.getString("DESTINATION_EN"));
                routes.setDestinationAr(rs.getString("DESTINATION_AR"));
                routes.setRouteCode(rs.getString("ROUTE_CODE"));
                routes.setRouteActive(rs.getInt("ROUTE_ACTIVE"));
            }

            rs.close();
            ps.close();

            return routes;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
