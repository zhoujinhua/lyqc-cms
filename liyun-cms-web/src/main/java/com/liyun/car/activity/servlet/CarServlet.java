package com.liyun.car.activity.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liyun.car.remote.service.NetworkModeCarService;

public class CarServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String brandId = req.getParameter("brandId");
		String familyId = req.getParameter("familyId");
		String groupId = req.getParameter("groupId");
		String disTon = req.getParameter("disTon");
		String gearBox = req.getParameter("gearBox");
		String flag = req.getParameter("flag");//
		
		PrintWriter w = null;
		w = resp.getWriter();
		String str ="";
		if ("-1".equals(flag)) {
			str = NetworkModeCarService.getBrand();
		}else if("0".equals(flag)) {
			str = NetworkModeCarService.getCseriesByBrandId(brandId);
		}else if("1".equals(flag)) {
			str = NetworkModeCarService.getCstyleByFamilyId(familyId);
		}else if("2".equals(flag)) {
			str = NetworkModeCarService.getCparentByPam(groupId, disTon, gearBox);
		}else {
			System.out.println("请求URL错误,请查看flag标志位是否正确");
		}
		w.print(str);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
