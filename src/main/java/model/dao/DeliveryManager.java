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

  // Fetch all deliveries
  public List<Delivery> fetchAllDeliveries() throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String query = "SELECT * FROM Delivery";
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
      "SELECT * FROM Delivery WHERE orderId LIKE ? OR trackingNumber LIKE ?";

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

  // Search by Date
  public List<Delivery> searchDeliveriesByDate(LocalDate searchDate)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String sql = "SELECT * FROM Delivery WHERE DATE(deliveringDate) = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setDate(1, java.sql.Date.valueOf(searchDate));
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

  // Search by both ID (substring) and Date (AND condition)
  public List<Delivery> searchDeliveries(String searchTerm, LocalDate searchDate)
    throws SQLException {
    List<Delivery> deliveries = new ArrayList<>();
    String sql =
      "SELECT * FROM Delivery WHERE (orderId LIKE ? OR trackingNumber LIKE ?) AND DATE(deliveringDate) = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, "%" + searchTerm + "%");
      pstmt.setString(2, "%" + searchTerm + "%");
      pstmt.setDate(3, java.sql.Date.valueOf(searchDate));

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
