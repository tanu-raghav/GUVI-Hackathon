package com.eventmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestManager {
    public static void submitRequest(SpecialRequest request) {
        // Get the database connection
        Connection conn = DatabaseConnection.getConnection();

        try {
            // Insert a new request into the SpecialRequest table
            String query = "INSERT INTO SpecialRequest (requestDescription, eventId) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, request.getRequestDescription());
            stmt.setInt(2, request.getEventId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            DatabaseConnection.closeConnection(conn);
        }
    }

    public static List<SpecialRequest> getRequestsByEventId(int eventId) {
        List<SpecialRequest> requests = new ArrayList<>();

        // Get the database connection
        Connection conn = DatabaseConnection.getConnection();

        try {
            // Retrieve special requests from the SpecialRequest table based on the eventId
            String query = "SELECT * FROM SpecialRequest WHERE eventId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SpecialRequest request = new SpecialRequest();
                request.setRequestId(rs.getInt("requestId"));
                request.setRequestDescription(rs.getString("requestDescription"));
                request.setEventId(rs.getInt("eventId"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            DatabaseConnection.closeConnection(conn);
        }

        return requests;
    }

    public static void updateRequest(SpecialRequest request) {
        // Get the database connection
        Connection conn = DatabaseConnection.getConnection();

        try {
            // Update the request in the SpecialRequest table
            String query = "UPDATE specialrequest SET requestDescription = ? WHERE requestId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, request.getRequestDescription());
            stmt.setInt(2, request.getRequestId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            DatabaseConnection.closeConnection(conn);
        }
    }

    public static void deleteRequest(int requestId) {
        // Get the database connection
        Connection conn = DatabaseConnection.getConnection();

        try {
            // Delete the request from the SpecialRequest table
            String query = "DELETE FROM specialrequest WHERE requestId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            DatabaseConnection.closeConnection(conn);
        }
    }
    public List<SpecialRequest> getPendingRequests() {
    List<SpecialRequest> pending = new ArrayList<>();
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT * FROM special_requests WHERE status = 'PENDING'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SpecialRequest req = new SpecialRequest();
            req.setId(rs.getInt("id"));
            req.setEventId(rs.getInt("event_id"));
            req.setUserId(rs.getInt("user_id"));
            req.setMessage(rs.getString("message"));
            req.setStatus(rs.getString("status"));
            pending.add(req);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return pending;
}

    public void updateRequestStatus(int requestId, String newStatus) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE special_requests SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, requestId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
