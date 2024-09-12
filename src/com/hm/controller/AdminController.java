package com.hm.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.hm.model.Room;
import com.hm.model.BookingDetails;

import java.io.IOException;
import java.util.List;

import com.hm.service.AdminService;
import com.hm.service.BookingFetchService;
import com.hm.service.RoomService;

public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestDispatcher rd;

	public AdminController() {
		super();
	}

	private AdminService admServ;

	@Override
	public void init() throws ServletException {
		super.init();

		admServ = new AdminService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		switch (action) {
		case "logoutAdmin":
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			response.sendRedirect("admin/adminLogin.jsp");
			break;

		default:
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPostTestAdmin");
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		switch (action) {
		case "loginAdmin":
			String adminId = request.getParameter("adminId");
			String password = request.getParameter("password");
			System.out.println("logintest");

			boolean validUser = admServ.loginAdmin(adminId, password);
			if (validUser) {
				System.out.println("loginned user");
				HttpSession session = request.getSession();
				session.setAttribute("user", adminId);
				response.sendRedirect("admin/adminDashboard.jsp");
			} else {
				request.setAttribute("errorMessage", "<font color=\"red\">Invalid username or password.</font>");
				request.getRequestDispatcher("admin/adminLogin.jsp").forward(request, response);
			}

			break;

		case "addRoom":
			String roomNumber = request.getParameter("roomNumber");
			String floor = request.getParameter("floor");
			String roomType = request.getParameter("roomType");
			double price = Double.parseDouble(request.getParameter("price"));

			Room room = new Room();
			room.setRoomNumber(roomNumber);
			room.setFloor(floor);
			room.setPrice(price);
			room.setRoomType(roomType);

			RoomService rmserv = new RoomService();

			int roomId = rmserv.addRoom(room);

			if (roomId != -1) {
				request.setAttribute("status",
						"<font color=\"green\">Room Id: " + roomId + " successfully Added.</font>");
			} else {
				request.setAttribute("status", "<font color=\"red\">Room not added!!.</font>");
			}
			rd = request.getRequestDispatcher("admin/adminDashboard.jsp");
			rd.forward(request, response);

			break;
		case "fetchBookings":
			String customerId = request.getParameter("customerId");

			BookingFetchService bfs = new BookingFetchService();

			List<BookingDetails> bookings = bfs.getBookings(customerId);
			if (bookings.size() > 0) {
				request.setAttribute("reservations", bookings);
			}
			rd = request.getRequestDispatcher("admin/adminBilling.jsp");
			rd.forward(request, response);

			break;

		default:
		}
	}

}
