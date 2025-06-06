package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.DeliveryManager;
import model.Delivery;
import model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/delivery")
public class DeliveryServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws ServletException, IOException {
    HttpSession session = request.getSession();
    DeliveryManager deliveryManager = (DeliveryManager) session.getAttribute(
      "deliveryManager"
    );

    String action = request.getParameter("action");

    if (action == null) {
      action = "list";
    }

    switch (action) {
      case "create-form":
        showCreateForm(request, response);
        break;
      case "view-single":
        viewSingleDelivery(request, response, deliveryManager);
        break;
      case "update-form":
        showUpdateForm(request, response, deliveryManager);
        break;
      case "list":
      default:
        listDeliveries(request, response, deliveryManager);
        break;
    }
  }

  @Override
  protected void doPost(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws ServletException, IOException {
    HttpSession session = request.getSession();
    DeliveryManager deliveryManager = (DeliveryManager) session.getAttribute(
      "deliveryManager"
    );

    String action = request.getParameter("action");

    if (action == null) {
      action = "list";
    }

    switch (action) {
      case "create":
        createDelivery(request, response, session, deliveryManager);
        break;
      case "update":
        updateDelivery(request, response, session, deliveryManager);
        break;
      case "delete":
        deleteDelivery(request, response, session, deliveryManager);
        break;
      case "search":
        searchDeliveries(request, response, deliveryManager);
        break;
      default:
        listDeliveries(request, response, deliveryManager);
        break;
    }
  }

  private void showCreateForm(
    HttpServletRequest request,
    HttpServletResponse response
  )
    throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher(
      "delivery_form.jsp"
    );
    dispatcher.forward(request, response);
  }

  private void viewSingleDelivery(
    HttpServletRequest request,
    HttpServletResponse response,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      String deliveryId = request.getParameter("deliveryId");
      Delivery delivery = deliveryManager.findDelivery(deliveryId);

      if (delivery != null) {
        request.setAttribute("delivery", delivery);
        RequestDispatcher dispatcher = request.getRequestDispatcher(
          "delivery_details.jsp"
        );
        dispatcher.forward(request, response);
      } else {
        request.setAttribute(
          "errorMessage",
          "Delivery not found with ID: " + deliveryId
        );
        listDeliveries(request, response, deliveryManager);
      }
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error retrieving delivery: " + e.getMessage()
      );
      listDeliveries(request, response, deliveryManager);
    }
  }

  private void showUpdateForm(
    HttpServletRequest request,
    HttpServletResponse response,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      String deliveryId = request.getParameter("deliveryId");
      Delivery delivery = deliveryManager.findDelivery(deliveryId);

      if (delivery != null) {
        request.setAttribute("delivery", delivery);
        RequestDispatcher dispatcher = request.getRequestDispatcher(
          "delivery_update.jsp"
        );
        dispatcher.forward(request, response);
      } else {
        request.setAttribute(
          "errorMessage",
          "Delivery not found with ID: " + deliveryId
        );
        listDeliveries(request, response, deliveryManager);
      }
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error retrieving delivery: " + e.getMessage()
      );
      listDeliveries(request, response, deliveryManager);
    }
  }

  private void listDeliveries(
    HttpServletRequest request,
    HttpServletResponse response,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      HttpSession session = request.getSession();
      User user = (User) session.getAttribute("user");
      List<Delivery> deliveries;

      if (user != null) {
        deliveries = deliveryManager.fetchDeliveriesByUserId(user.getId());
      } else {
        // fallback or empty list if no user is logged in
        deliveries = new ArrayList<>();
      }

      request.setAttribute("deliveries", deliveries);
      RequestDispatcher dispatcher = request.getRequestDispatcher(
        "delivery_list.jsp"
      );
      dispatcher.forward(request, response);
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error retrieving deliveries: " + e.getMessage()
      );
      RequestDispatcher dispatcher = request.getRequestDispatcher(
        "delivery_list.jsp"
      );
      dispatcher.forward(request, response);
    }
  }


  private void createDelivery(
    HttpServletRequest request,
    HttpServletResponse response,
    HttpSession session,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      // Generate a unique delivery ID
      String deliveryId =
        "DEL" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

      String orderId = request.getParameter("orderId");
      String deliveringDateStr = request.getParameter("deliveringDate");
      String status = request.getParameter("status");
      String deliveringAddress = request.getParameter("deliveringAddress");
      String nameOnDelivery = request.getParameter("nameOnDelivery");
      String trackingNumber = request.getParameter("trackingNumber");

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
      Date deliveringDate = sdf.parse(deliveringDateStr);

      deliveryManager.addDelivery(
        deliveryId,
        orderId,
        deliveringDate,
        status,
        deliveringAddress,
        nameOnDelivery,
        trackingNumber
      );

      session.setAttribute("successMessage", "Delivery created successfully!");
      response.sendRedirect("delivery?action=list");
    } catch (ParseException e) {
      request.setAttribute(
        "errorMessage",
        "Invalid date format: " + e.getMessage()
      );
      showCreateForm(request, response);
    } catch (SQLException e) {
      request.setAttribute(
        "errorMessage",
        "Error creating delivery: " + e.getMessage()
      );
      showCreateForm(request, response);
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error creating delivery: " + e.getMessage()
      );
      showCreateForm(request, response);
    }
  }

  private void updateDelivery(
    HttpServletRequest request,
    HttpServletResponse response,
    HttpSession session,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      String deliveryId = request.getParameter("deliveryId");
      String status = request.getParameter("status");
      String deliveringAddress = request.getParameter("deliveringAddress");
      String nameOnDelivery = request.getParameter("nameOnDelivery");

      // Check if delivery can be modified (only if current status is PENDING)
      String currentStatus = deliveryManager.getDeliveryStatus(deliveryId);
      if (currentStatus != null && !currentStatus.equalsIgnoreCase("PENDING")) {
        session.setAttribute("errorMessage", 
          "Cannot modify delivery details. Current status: " + currentStatus + 
          ". Only pending deliveries can be modified.");
        response.sendRedirect("delivery?action=list");
        return;
      }

      // Get existing delivery data to preserve other fields
      Delivery existingDelivery = deliveryManager.findDelivery(deliveryId);
      if (existingDelivery == null) {
        session.setAttribute("errorMessage", "Delivery not found.");
        response.sendRedirect("delivery?action=list");
        return;
      }

      deliveryManager.updateDeliveryLimited(
        deliveryId,
        status,
        deliveringAddress,
        nameOnDelivery
      );

      session.setAttribute("successMessage", "Delivery updated successfully!");
      response.sendRedirect("delivery?action=list");
    } catch (SQLException e) {
      request.setAttribute(
        "errorMessage",
        "Error updating delivery: " + e.getMessage()
      );
      response.sendRedirect("delivery?action=list");
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error updating delivery: " + e.getMessage()
      );
      response.sendRedirect("delivery?action=list");
    }
  }

  private void deleteDelivery(
    HttpServletRequest request,
    HttpServletResponse response,
    HttpSession session,
    DeliveryManager deliveryManager
  )
    throws ServletException, IOException {
    try {
      String deliveryId = request.getParameter("deliveryId");

      deliveryManager.deleteDelivery(deliveryId);

      session.setAttribute("successMessage", "Delivery deleted successfully!");

      response.sendRedirect("delivery?action=list");
    } catch (SQLException e) {
      request.setAttribute(
        "errorMessage",
        "Error deleting delivery: " + e.getMessage()
      );
      listDeliveries(request, response, deliveryManager);
    } catch (Exception e) {
      request.setAttribute(
        "errorMessage",
        "Error deleting delivery: " + e.getMessage()
      );
      listDeliveries(request, response, deliveryManager);
    }
  }

  // FIXED: Improved search functionality with user filtering
private void searchDeliveries(
  HttpServletRequest request,
  HttpServletResponse response,
  DeliveryManager deliveryManager
)
  throws ServletException, IOException {
  String searchTerm = request.getParameter("searchTerm");
  String searchDateStr = request.getParameter("searchDate");
  List<Delivery> deliveries = new ArrayList<>();
  String errorMessage = null;

  try {
    // Get the logged-in user
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    
    if (user == null) {
      errorMessage = "You must be logged in to search deliveries.";
      deliveries = new ArrayList<>();
    } else {
      // Clean up the inputs
      boolean hasSearchTerm = searchTerm != null && !searchTerm.trim().isEmpty();
      boolean hasSearchDate = searchDateStr != null && !searchDateStr.trim().isEmpty();
      
      LocalDate searchDate = null;
      if (hasSearchDate) {
        try {
          searchDate = LocalDate.parse(searchDateStr);
        } catch (DateTimeParseException e) {
          errorMessage = "Invalid date format. Please use YYYY-MM-DD format.";
        }
      }

      // Only proceed if no date parsing error occurred
      if (errorMessage == null) {
        String userId = user.getId();
        
        if (hasSearchTerm && hasSearchDate) {
          // Both search term and date provided - AND condition
          deliveries = deliveryManager.searchDeliveriesByUser(searchTerm.trim(), searchDate, userId);
        } else if (hasSearchTerm) {
          // Only search term provided
          deliveries = deliveryManager.searchDeliveriesByIDAndUser(searchTerm.trim(), userId);
        } else if (hasSearchDate) {
          // Only date provided
          deliveries = deliveryManager.searchDeliveriesByDateAndUser(searchDate, userId);
        } else {
          // No search criteria provided, fetch user's deliveries
          deliveries = deliveryManager.fetchDeliveriesByUserId(userId);
        }
      }
    }
  } catch (SQLException e) {
    errorMessage = "Database error occurred while searching: " + e.getMessage();
    System.err.println("SQL Error in searchDeliveries: " + e.getMessage());
    e.printStackTrace();
  } catch (Exception e) {
    errorMessage = "An unexpected error occurred while searching: " + e.getMessage();
    System.err.println("General Error in searchDeliveries: " + e.getMessage());
    e.printStackTrace();
  } finally {
    // Set attributes for the JSP
    request.setAttribute("deliveries", deliveries);
    request.setAttribute("searchTerm", searchTerm);
    request.setAttribute("searchDate", searchDateStr);
    
    if (errorMessage != null) {
      request.setAttribute("errorMessage", errorMessage);
    }
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("delivery_list.jsp");
    dispatcher.forward(request, response);
  }
}
}