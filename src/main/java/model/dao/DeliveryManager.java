package model.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Delivery;

public class DeliveryManager {

  private Connection conn;

  public DeliveryManager(Connection conn) throws SQLException {
    this.conn = conn;
  }

  // Add a delivery
  public void addDelivery(
    String deliveryId,
    String orderId,
    Date deliveringDate,
    String status,
    String deliveringAddress,
    String nameOnDelivery,
    String trackingNumber
  )
    throws SQLException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateStr = sdf.format(deliveringDate);

    String safeQuery =
      "INSERT INTO Delivery (deliveryId, orderId, deliveringDate, status, deliveringAddress, nameOnDelivery, trackingNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(safeQuery)) {
      pstmt.setString(1, deliveryId);
      pstmt.setString(2, orderId);
      pstmt.setString(3, dateStr);
      pstmt.setString(4, status);
      pstmt.setString(5, deliveringAddress);
      pstmt.setString(6, nameOnDelivery);
      pstmt.setString(7, trackingNumber);
      pstmt.executeUpdate();
    }
  }

  // Find a delivery by ID
  public Delivery findDelivery(String deliveryId) throws SQLException {
    String query = "SELECT * FROM Delivery WHERE deliveryId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setString(1, deliveryId);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        return new Delivery(
          deliveryId,
          orderId,
          deliveringDate,
          status,
          deliveringAddress,
          nameOnDelivery,
          trackingNumber
        );
      }
      return null;
    }
  }

  // NEW: Get delivery status by ID
  public String getDeliveryStatus(String deliveryId) throws SQLException {
    String sql = "SELECT status FROM Delivery WHERE deliveryId = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, deliveryId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return rs.getString("status");
        }
      }
    }
    return null; // Delivery not found
  }

  // NEW: Check if delivery can be modified (only if PENDING)
  public boolean canModifyDelivery(String deliveryId) throws SQLException {
    String currentStatus = getDeliveryStatus(deliveryId);
    return currentStatus != null && currentStatus.equalsIgnoreCase("PENDING");
  }

  // Update a delivery record
  public void updateDelivery(
    String deliveryId,
    String orderId,
    Date deliveringDate,
    String status,
    String deliveringAddress,
    String nameOnDelivery,
    String trackingNumber
  )
    throws SQLException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateStr = sdf.format(deliveringDate);

    String safeQuery =
      "UPDATE Delivery SET orderId=?, deliveringDate=?, status=?, deliveringAddress=?, nameOnDelivery=?, trackingNumber=? WHERE deliveryId=?";
    try (PreparedStatement pstmt = conn.prepareStatement(safeQuery)) {
      pstmt.setString(1, orderId);
      pstmt.setString(2, dateStr);
      pstmt.setString(3, status);
      pstmt.setString(4, deliveringAddress);
      pstmt.setString(5, nameOnDelivery);
      pstmt.setString(6, trackingNumber);
      pstmt.setString(7, deliveryId);
      pstmt.executeUpdate();
    }
  }

  // Delete a delivery
  public void deleteDelivery(String deliveryId) throws SQLException {
    String query = "DELETE FROM Delivery WHERE deliveryId = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setString(1, deliveryId);
      pstmt.executeUpdate();
    }
  }

  // Fetch all deliveries for a specific user
  public List<Delivery> fetchDeliveriesByUserId(String userId) throws SQLException {
      List<Delivery> deliveries = new ArrayList<>();
      String query = "SELECT d.* FROM Delivery d JOIN Orders o ON d.orderId = o.orderId WHERE o.userId = ? ORDER BY d.deliveringDate DESC";
      try (PreparedStatement pstmt = conn.prepareStatement(query)) {
          pstmt.setString(1, userId);
          try (ResultSet rs = pstmt.executeQuery()) {
              while (rs.next()) {
                  String deliveryId = rs.getString("deliveryId");
                  String orderId = rs.getString("orderId");
                  Date deliveringDate = rs.getTimestamp("deliveringDate");
                  String status = rs.getString("status");
                  String deliveringAddress = rs.getString("deliveringAddress");
                  String nameOnDelivery = rs.getString("nameOnDelivery");
                  String trackingNumber = rs.getString("trackingNumber");

                  deliveries.add(new Delivery(deliveryId, orderId, deliveringDate, status, deliveringAddress, nameOnDelivery, trackingNumber)
                  );
              }
          }
      }
      return deliveries;
  }

  // Fetch all deliveries
  public List<Delivery> fetchAllDeliveries() throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String query = "SELECT * FROM Delivery ORDER BY deliveringDate DESC";
    try (
      PreparedStatement pstmt = conn.prepareStatement(query);
      ResultSet rs = pstmt.executeQuery()
    ) {
      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }
    return deliveries;
  }

  // Search by Order ID or Tracking Number (substring match)
  public List<Delivery> searchDeliveriesByID(String searchTerm)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String sql =
      "SELECT * FROM Delivery WHERE orderId LIKE ? OR trackingNumber LIKE ? ORDER BY deliveringDate DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + searchTerm + "%");
      pstmt.setString(2, "%" + searchTerm + "%");
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }

    return deliveries;
  }

  // Search by Date - Fixed for SQLite compatibility
  public List<Delivery> searchDeliveriesByDate(LocalDate searchDate)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    // Use SQLite's date() function to extract date part from datetime
    String sql = "SELECT * FROM Delivery WHERE date(deliveringDate) = ? ORDER BY deliveringDate DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      // Convert LocalDate to string in format SQLite expects
      pstmt.setString(1, searchDate.toString()); // This gives yyyy-MM-dd format
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }

    return deliveries;
  }

  // Search by both ID (substring) and Date (AND condition) - Fixed
  public List<Delivery> searchDeliveries(String searchTerm, LocalDate searchDate)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    // Fixed SQL query for SQLite
    String sql =
      "SELECT * FROM Delivery WHERE (orderId LIKE ? OR trackingNumber LIKE ?) AND date(deliveringDate) = ? ORDER BY deliveringDate DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + searchTerm + "%");
      pstmt.setString(2, "%" + searchTerm + "%");
      pstmt.setString(3, searchDate.toString()); // Use string format for SQLite

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }

    return deliveries;
  }

  // NEW: Search by date range functionality
  public List<Delivery> searchDeliveriesByDateRange(LocalDate startDate, LocalDate endDate)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String sql = "SELECT * FROM Delivery WHERE date(deliveringDate) BETWEEN ? AND ? ORDER BY deliveringDate DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, startDate.toString());
      pstmt.setString(2, endDate.toString());
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }

    return deliveries;
  }

  // NEW: Search with both term and date range
  public List<Delivery> searchDeliveriesWithDateRange(String searchTerm, LocalDate startDate, LocalDate endDate)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String sql =
      "SELECT * FROM Delivery WHERE (orderId LIKE ? OR trackingNumber LIKE ?) AND date(deliveringDate) BETWEEN ? AND ? ORDER BY deliveringDate DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + searchTerm + "%");
      pstmt.setString(2, "%" + searchTerm + "%");
      pstmt.setString(3, startDate.toString());
      pstmt.setString(4, endDate.toString());

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }

    return deliveries;
  }

  // Search by Order ID or Tracking Number for specific user
  public List<Delivery> searchDeliveriesByIDAndUser(String searchTerm, String userId)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String sql =
      "SELECT d.* FROM Delivery d JOIN Orders o ON d.orderId = o.orderId " +
      "WHERE (d.orderId LIKE ? OR d.trackingNumber LIKE ?) AND o.userId = ? " +
      "ORDER BY d.deliveringDate DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + searchTerm + "%");
      pstmt.setString(2, "%" + searchTerm + "%");
      pstmt.setString(3, userId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }

    return deliveries;
  }

  // Search by Date for specific user
  public List<Delivery> searchDeliveriesByDateAndUser(LocalDate searchDate, String userId)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String sql = 
      "SELECT d.* FROM Delivery d JOIN Orders o ON d.orderId = o.orderId " +
      "WHERE date(d.deliveringDate) = ? AND o.userId = ? " +
      "ORDER BY d.deliveringDate DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, searchDate.toString());
      pstmt.setString(2, userId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }

    return deliveries;
  }

  // Search by both ID and Date for specific user
  public List<Delivery> searchDeliveriesByUser(String searchTerm, LocalDate searchDate, String userId)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String sql =
      "SELECT d.* FROM Delivery d JOIN Orders o ON d.orderId = o.orderId " +
      "WHERE (d.orderId LIKE ? OR d.trackingNumber LIKE ?) AND date(d.deliveringDate) = ? AND o.userId = ? " +
      "ORDER BY d.deliveringDate DESC";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + searchTerm + "%");
      pstmt.setString(2, "%" + searchTerm + "%");
      pstmt.setString(3, searchDate.toString());
      pstmt.setString(4, userId);

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        String deliveryId = rs.getString("deliveryId");
        String orderId = rs.getString("orderId");
        Date deliveringDate = rs.getTimestamp("deliveringDate");
        String status = rs.getString("status");
        String deliveringAddress = rs.getString("deliveringAddress");
        String nameOnDelivery = rs.getString("nameOnDelivery");
        String trackingNumber = rs.getString("trackingNumber");

        deliveries.add(
          new Delivery(
            deliveryId,
            orderId,
            deliveringDate,
            status,
            deliveringAddress,
            nameOnDelivery,
            trackingNumber
          )
        );
      }
    }

    return deliveries;
  }
}