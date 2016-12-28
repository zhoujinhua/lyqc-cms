package com.liyun.car.fee.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.dealer.service.DealerCompanyService;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBill;
import com.liyun.car.fee.service.DealerSerfeeMonBillService;
import com.liyun.car.fee.service.SerfeeMonInfoService;
import com.liyun.car.system.entity.SyUser;

/**
 * 发票登记
 * @author zhoufei
 *
 */
@Controller
@RequestMapping("invoice")
public class InvoiceRegistController {
	
	private Logger logger = LoggerFactory.getLogger(InvoiceRegistController.class);
	@Autowired
	private DealerCompanyService dealerCompanyService;
	@Autowired
	private DealerSerfeeMonBillService dealerSerfeeMonBillService;
	@Autowired
	private SerfeeMonInfoService serfeeMonInfoService;
	
	@RequestMapping("suspend")
	public String suspend(HttpServletRequest request, SyDealerCompany dc){
		try{
			dealerCompanyService.updateSuspendBill(dc);
			request.setAttribute("msg","设置成功！");
		} catch(Exception e	){
			logger.error("设置失败,错误信息：",e);
			request.setAttribute("msg", "设置失败,"+e.getMessage());
		}
		return "fee/invoice/list";
	}
	
	@RequestMapping("invoiceRegister")
	public void register(HttpServletRequest request, HttpServletResponse response, CmsDealerSerfeeMonBill bill){
		PrintWriter pw = null;
		try{
			pw = response.getWriter();
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			
			bill = dealerSerfeeMonBillService.updateBillInvoiceRegister(bill, user);
			pw.print("{\"flag\":1,\"msg\":\"操作成功!\",\"stt\":\""+bill.getStt().getName()+"\"}");
		} catch(Exception e){
			logger.error("发票登记失败,错误信息：",e);
			pw.print("{\"flag\":0,\"msg\":\"操作失败,"+e.getMessage()+"!\"}");
		}
	}
	
	@RequestMapping("suspendBill")
	public void suspendBill(HttpServletRequest request, HttpServletResponse response, CmsDealerSerfeeMonBill bill){
		PrintWriter pw = null;
		try{
			pw = response.getWriter();
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			
			bill = dealerSerfeeMonBillService.updateSuspendBill(bill, user);
			pw.print("{\"flag\":1,\"msg\":\"操作成功!\",\"stt\":\""+bill.getPayStt().getName()+"\"}");
		}catch(Exception e){
			logger.error("暂缓单个账单发送失败,错误信息：",e);
			pw.print("{\"flag\":0,\"msg\":\"操作失败,"+e.getMessage()+"!\"}");
		}
	}
	
	@RequestMapping("freeze")
	public String freeze(HttpServletRequest request ){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			String feeMons = request.getParameter("feeMons");
			
			serfeeMonInfoService.updateFeeMonFreeze(feeMons, user);
			request.setAttribute("msg", feeMons+"批次服务费操作已冻结.");
		} catch(Exception e){
			logger.error("批次服务费操作冻结失败,错误信息：",e);
			request.setAttribute("msg", "操作失败,"+e.getMessage());
		}
		return "fee/invoice/list";
	}
}
