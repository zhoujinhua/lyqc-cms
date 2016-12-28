package com.liyun.car.fee.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.core.utils.DownloadUtil;
import com.liyun.car.dealer.entity.SyDealerCompany;
import com.liyun.car.fee.entity.CmsDealerSerfeeFlow;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBill;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonBillFlow;
import com.liyun.car.fee.entity.CmsDealerSerfeeMonFile;
import com.liyun.car.fee.entity.CmsSerfeeMonInfo;
import com.liyun.car.fee.enums.SerfeeBillStatusEnum;
import com.liyun.car.fee.enums.SerfeeStatusEnum;
import com.liyun.car.fee.service.DealerSerfeeMonBillService;
import com.liyun.car.fee.service.SerfeeMonInfoService;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.service.VUserRoleService;

/**
 * 账单管理
 * @author zhoufei
 *
 */
@Controller
@RequestMapping("bill")
public class DealerSerfeeMonBillController {

	private Logger logger = LoggerFactory.getLogger(DealerSerfeeMonBillController.class);
	@Autowired
	private DealerSerfeeMonBillService dealerSerfeeMonBillService;
	@Autowired
	private VUserRoleService vUserRoleService;
	@Autowired
	private SerfeeMonInfoService serfeeMonInfoService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request , CmsDealerSerfeeMonBill bill, String taskId){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
		boolean so = vUserRoleService.hasRole(user, "SO");
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsDealerSerfeeMonBill> page = dealerSerfeeMonBillService.pageList(bill, false, user, pn);
		
		if(bill.getFeeMon()!=null && !"".equals(bill.getFeeMon())){
			CmsSerfeeMonInfo info = serfeeMonInfoService.getEntityByCode(CmsSerfeeMonInfo.class, bill.getFeeMon(), false);
			map.put("info", info);
		}
		map.put("so", so);
		map.put("taskId", taskId);
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	/**
	 * 账单管理列表
	 * 经销商查看账单（只能查看自己，账单待发送之后的所有状态）
	 * 经销商还要有地方可以查看自己的达标情况
	 */
	@RequestMapping("faList")
	@ResponseBody
	public Map<String, Object> faList(HttpServletRequest request, CmsDealerSerfeeMonBill bill ){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
		boolean am = vUserRoleService.hasRole(user, "AM");
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsDealerSerfeeMonBill> page = null;
		page = dealerSerfeeMonBillService.pageList(bill, am, user, pn);
		
		map.put("user", user);
		map.put("am", am);
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("sendBill")
	public String sendBill(HttpServletRequest request ,CmsDealerSerfeeMonBill bill){
		try{
			String feeMons = request.getParameter("feeMons");
			if(feeMons == null ||"".equals(feeMons)){
				throw new RuntimeException("服务费批次不能为空.");
			}
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			
			dealerSerfeeMonBillService.sendBills(feeMons,bill,user);
			request.setAttribute("msg", "操作成功,邮件异步发送,可通过查看邮件发送详情菜单进行邮件发送跟踪");
		} catch(Exception e){
			logger.error("发送账单失败,错误信息：",e);
			request.setAttribute("msg", "操作失败,"+e.getMessage());
		}
		return "fee/bill/faList";
	}
	
	@RequestMapping("faConfirm")
	public String faConfirm(HttpServletRequest request, CmsDealerSerfeeFlow flow){
		try{
			SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
			CmsSerfeeMonInfo info = serfeeMonInfoService.getEntityByCode(CmsSerfeeMonInfo.class, flow.getFeeMon(), false);
			if(info == null){
				throw new RuntimeException("服务费批次不存在.");
			}
			if(info.getStt() != SerfeeStatusEnum.GENZH){ //退回并且状态不正确
				throw new RuntimeException(flow.getFeeMon()+"批次服务费状态为："+info.getStt().getName()+",不允许进行退回操作.");
			} else {
				serfeeMonInfoService.saveFaConfirm(flow,user);
			}
			request.setAttribute("msg", flow.getFeeMon()+"批次服务费计算结果成功"+(flow.getTyp() == BooleanEnum.YES?"确认":"退回"));
		} catch(Exception e){
			logger.error("FA确认服务费计算结果失败,错误信息：",e);
			request.setAttribute("msg", "操作失败,"+e.getMessage());
		}
		return "fee/bill/faList";
	}
	
	@RequestMapping("loanSuc")
	public String loanSuc(HttpServletRequest request){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			String feeMons = request.getParameter("feeMons");
			
			dealerSerfeeMonBillService.updateBillSucc(feeMons,user);
			request.setAttribute("msg", "确认"+feeMons+"批次放款结果成功.");
		} catch(Exception e){
			logger.error("批次服务费设置放款状态失败,错误信息：",e);
			request.setAttribute("msg", "批次服务费设置放款状态失败,"+e.getMessage());
		}
		return "fee/bill/faList";
	}
	
	/**
	 * 历史放款文件
	 */
	@RequestMapping("listHisEBank")
	@ResponseBody
	public Map<String, Object> listHisEBank(HttpServletRequest request, CmsDealerSerfeeMonFile file){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsDealerSerfeeMonFile> page = dealerSerfeeMonBillService.pageList(file,  pn, "");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("downloadHisEbank")
	public void downloadHisEbank(HttpServletRequest request, HttpServletResponse response, CmsDealerSerfeeMonFile file){
		file = dealerSerfeeMonBillService.getEntityById(CmsDealerSerfeeMonFile.class, file.getId(), false);
		if(new File(file.getFileDir()).exists()){
			try {
				DownloadUtil.download(request, response, new File(file.getFileDir()), file.getFileName());
			} catch (IOException e) {
				try {
					show_msg(response, "下载失败,"+e.getMessage());
				} catch (IOException e1) {
					logger.error("下载失败,错误信息："+e1.getStackTrace());
					e1.printStackTrace();
				}
			}
		} else {
			try {
				show_msg(response, "下载失败,文件不存在.");
			} catch (IOException e1) {
				logger.error("下载失败,错误信息："+e1.getStackTrace());
				e1.printStackTrace();
			}
		}
	}
	@RequestMapping("downloadEbank")
	public void downloadEbank(HttpServletRequest request, HttpServletResponse response, CmsSerfeeMonInfo info){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			String feeMons = request.getParameter("feeMons");
			
			File file = dealerSerfeeMonBillService.generSerfeeMonEbank(feeMons,user);
			DownloadUtil.download(request, response, file, file.getName());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("下载失败,错误信息：",e);
			try {
				show_msg(response, "下载失败,"+e.getMessage());
			} catch (IOException e1) {
				logger.error("下载失败,错误信息："+e1.getStackTrace());
				e1.printStackTrace();
			}
		}
		
	}
	public void show_msg(HttpServletResponse response,String msg) throws IOException{
	    response.setContentType("text/html; charset=gbk");  
	    PrintWriter out = response.getWriter();   
	    out.println("<script language='javascript'>");   
    	out.println("alert('"+msg+"');");
    	out.println("history.go(-1);");
	    out.print("</script>");   
	}
	
	@RequestMapping("confirmBill")
	public String confirmBill(HttpServletRequest request, CmsDealerSerfeeMonBill bill){
		try{
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			
			dealerSerfeeMonBillService.updateSerfeeMonBill(bill,user);
			request.setAttribute("msg", bill.getFeeMon()+"批次服务费账单经销商确认放款成功.");
		} catch(Exception e){
			logger.error("批次服务费账单确认放款失败,错误信息：",e);
			request.setAttribute("msg", bill.getFeeMon()+"批次服务费账单经销商确认放款失败,"+e.getMessage());
		}
		return "fee/bill/faList";
	}
	
	@RequestMapping("listFlow")
	@ResponseBody
	public Map<String, Object> listFlow(HttpServletRequest request, CmsDealerSerfeeMonBillFlow flow){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		
		Map<String, Object> map = new HashMap<String,Object>();
		Page<CmsDealerSerfeeMonBillFlow> page = dealerSerfeeMonBillService.pageList(flow,  pn, "feeMon");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("billDetail")
	public String billDetail(HttpServletRequest request, CmsDealerSerfeeMonBill bill, SyDealerCompany dc){
		List<CmsDealerSerfeeMonBill> bills = new ArrayList<>();
		if(bill != null && bill.getFeeMon() != null && !"".equals(bill.getFeeMon())){
			bill = dealerSerfeeMonBillService.getSerfeeMonBillById(bill.getFeeMon(), bill.getCompanyCode(), true);
			bills.add(bill);
		} else {
			SerfeeBillStatusEnum[] status = SerfeeBillStatusEnum.getInvoiceBillStatus();
			
			int size = 4;
			bills = dealerSerfeeMonBillService.getSerfeeMonBills(dc, status, size);
			request.setAttribute("method", "invoice");
		}
		
		request.setAttribute("bills", bills);
		return "fee/bill/billDetail";
	}
}
