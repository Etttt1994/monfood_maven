package com.model.order.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.model.order.OrderVO;
import com.model.order.service.OrderService;
import com.model.order.service.impl.OrderServiceImpl;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		JsonObject respObj = new JsonObject();
		
		try {
			OrderVO orderVO = gson.fromJson(request.getReader(), OrderVO.class);
			OrderService service = new OrderServiceImpl();
			final OrderVO result = service.adminFindOrderId(orderVO);
			
			if (result == null) {
				respObj.addProperty("errMsg", "無此訂單");
				response.getWriter().append(gson.toJson(respObj));
				return;
			}
			
			respObj.add("Orders", gson.toJsonTree(result));
			
		} catch (Exception e) {
			e.printStackTrace();
			respObj.addProperty("errMsg", "系統錯誤");
		}
		response.getWriter().append(gson.toJson(respObj));
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
