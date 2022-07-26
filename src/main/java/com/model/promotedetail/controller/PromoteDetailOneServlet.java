package com.model.promotedetail.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.model.promotedetail.PromoteDetailVO;
import com.model.promotedetail.service.PromoteDetailService;
import com.model.promotedetail.service.Impl.PromoteDetailServiceImpl;

@WebServlet("/PromoteDetailOneServlet")
public class PromoteDetailOneServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		JsonObject respObj = new JsonObject();
		
		try {
			
			PromoteDetailVO promoteDetailVO = gson.fromJson(request.getReader(), PromoteDetailVO.class);
			PromoteDetailService promoteListservice = new PromoteDetailServiceImpl();
			
			final PromoteDetailVO result = promoteListservice.FindPromoteDetailOne(promoteDetailVO);
			
			if (result == null) {
				respObj.addProperty("errMsg", "無此筆明細");
				response.getWriter().append(gson.toJson(respObj));
				return;
			}
			
			// add the result into json format
			respObj.add("PromoteDetails", gson.toJsonTree(result));
			
		} catch (Exception e) {
			e.printStackTrace();
			respObj.addProperty("errMsg", "findOneServlet系統錯誤");
		}
		
		// return data
		response.getWriter().append(gson.toJson(respObj));
	}
}