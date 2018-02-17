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

        // captalize first letter of each word
        String givenString = rs.getString("ROUTE_NAME_EN").toLowerCase();

        String words[] = givenString.split("\\s");
        String capitalizeWord = "";
        String first = "";
        String afterfirst = "";

        for (String w : words) {
            if (w.length() > 0) {
                first = w.substring(0, 1);
                afterfirst = w.substring(1);
            }

            capitalizeWord += first.toUpperCase() + afterfirst + " ";
        }
        String routeNameEn = capitalizeWord.trim();
        // captalize first letter of each word

        routes.setRouteNameEn(routeNameEn);
        routes.setRouteNameAr(rs.getString("ROUTE_NAME_AR"));

        return routes;
    }

    public void insertRoute(Route routes) throws Exception {
        try {
            Connection conn = getConnection();

            String query = "SELECT count (*) as ROW_COUNTER FROM BUSES.ROUTES";
            PreparedStatement preparedStm = conn.prepareStatement(query);
            ResultSet resultSet = preparedStm.executeQuery();

            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt("ROW_COUNTER");
            }
            String countOrMax = "";
            if (count > 0) {
                countOrMax = "max";
            } else {
                countOrMax = "count";
            }

            String sql = "INSERT INTO BUSES.ROUTES (ROUTE_ID,"
                    + " SOURCE_EN,"
                    + " SOURCE_AR,"
                    + " DESTINATION_EN,"
                    + " DESTINATION_AR,"
                    + " ROUTE_CODE,"
                    + " ROUTE_ACTIVE)"
                    + " VALUES ((select " + countOrMax + "(ROUTE_ID) from ROUTES)+1,?,?,?,?,?,?)";
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

            String sql = "SELECT ROUTE_ID,ROUTE_CODE,ROUTE_ACTIVE,SOURCE_EN, SOURCE_AR,DESTINATION_EN, DESTINATION_AR,"
                    + " CONCAT(CONCAT(CONCAT(CONCAT(SOURCE_EN,' - '), DESTINATION_EN),' , '),ROUTE_CODE) AS ROUTE_NAME_EN,"
                    + " CONCAT(CONCAT(CONCAT(CONCAT(SOURCE_AR,' - '), DESTINATION_AR),' , '),ROUTE_CODE) AS ROUTE_NAME_AR"
                    + " FROM BUSES.ROUTES"
                    + " WHERE ROUTE_ID=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, routeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                routes = populateRoutes(rs);
            
            }

            rs.close();
            ps.close();

            return routes;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
