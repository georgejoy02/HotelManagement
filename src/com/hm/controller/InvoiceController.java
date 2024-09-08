package com.hm.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import com.hm.service.InvoiceService;

public class InvoiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InvoiceController() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InvoiceService inovoiceServ = new InvoiceService();

		String action = request.getParameter("action");

		switch (action) {
		case "generateInvoice":
			String customerId = request.getParameter("customerId");
			String reservationId = request.getParameter("reservationId");

			try {
				String filePath = inovoiceServ.generateInvoice(customerId, reservationId);
				if (filePath != null) {
					Path file = Path.of(filePath);
					if (Files.exists(file)) {
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition", "inline; filename=" + file.getFileName());

						try (OutputStream out = response.getOutputStream()) {
							Files.copy(file, out);
							out.flush();
						}
					}else {
						System.out.println("file not found");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");

			}

			break;
		default:
		}

	}

}
