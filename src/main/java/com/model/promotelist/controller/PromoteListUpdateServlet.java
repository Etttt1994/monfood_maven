package com.model.promotelist.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.model.promotelist.PromoteListVO;
import com.model.promotelist.service.PromoteListService;
import com.model.promotelist.service.Impl.PromoteListServiceImpl;

@WebServlet("/PromoteListUpdateServlet")
public class PromoteListUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();
		JsonObject respObj = new JsonObject();
		System.out.println("??????");
		
		PromoteListVO promoteListVO = gson.fromJson(req.getReader(), PromoteListVO.class);
		System.out.println(promoteListVO);
		PromoteListServiceImpl promoteListserviceImpl = new PromoteListServiceImpl();
		PromoteListVO result = promoteListserviceImpl.adminUpdatePromoteList(promoteListVO);
		resp.getWriter().append(gson.toJson(respObj));
		

	}

}

