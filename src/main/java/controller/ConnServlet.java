package controller;

import java.io.IOException;

import java.sql.Connection;

import java.sql.SQLException;

import java.util.logging.Level;

import java.util.logging.Logger;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;

import model.dao.*;


@WebServlet("/ConnServlet")
public class ConnServlet extends HttpServlet {
    private DBConnector db;
    private ItemManager itemManager;
    private BillManager billManager;
    private OrderItemManager orderItemManager;
    private PaymentManager paymentManager;
    private CartItemManager cartItemManager;
    private CartManager cartManager;
    private OrderManager orderManager;
    private UserManager userManager;
    private Connection conn;

    @Override //Create and instance of DBConnector for the deployment session

    public void init() {

        try {

            db = new DBConnector();

        } catch (ClassNotFoundException | SQLException ex) {

            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);

        }      

    }

   

    @Override //Add the DBConnector, DBManager, Connection instances to the session

    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");       

        HttpSession session = request.getSession();

        conn = db.openConnection();       

        try {

            itemManager = new ItemManager(conn);
            billManager = new BillManager(conn);
            orderItemManager = new OrderItemManager(conn);
            paymentManager = new PaymentManager(conn);
            cartItemManager = new CartItemManager(conn);
            cartManager = new CartManager(conn);
            orderManager = new OrderManager(conn);
        } catch (SQLException ex) {

            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);

        }

        //export the DB manager to the view-session (JSPs)
        session.setAttribute("db", db);
        session.setAttribute("itemManager", itemManager);           
        session.setAttribute("billManager", billManager);
        session.setAttribute("orderItemManager", orderItemManager);
        session.setAttribute("orderManager", orderManager);
        session.setAttribute("paymentManager", paymentManager);
        session.setAttribute("cartItemManager", cartItemManager);
        session.setAttribute("cartManager", cartManager);
    }   

     

    @Override //Destroy the servlet and release the resources of the application (terminate also the db connection)

     public void destroy() {

        try {

            db.closeConnection();

        } catch (SQLException ex) {

            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}