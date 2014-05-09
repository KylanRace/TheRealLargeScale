package com.example.controller;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

public class DirectionFinder extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException
	{
		doGet(request, response);
	}

	public void doGet (HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		request.getRequestDispatcher("/directionsOutput.jsp").forward(request, response);//pass the output to this jsp
	}
	
}