package com.liyun.car.param.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.liyun.car.common.entity.Page;
import com.liyun.car.common.enums.BooleanEnum;
import com.liyun.car.common.utils.PropertyUtil;
import com.liyun.car.core.utils.DownloadUtil;
import com.liyun.car.param.entity.SyAnnouncement;
import com.liyun.car.param.service.AnnouncementService;
import com.liyun.car.system.entity.SyUser;
import com.liyun.car.system.enums.UserStatusEnum;
import com.liyun.car.system.enums.UserTypeEnum;
import com.liyun.car.system.service.UserService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("announcement")
public class AnnouncementController {

	@Autowired
	private AnnouncementService announcementService;
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(AnnouncementController.class);
	
	/**
	 * 查看单个公告
	 * @param announcement
	 */
	@RequestMapping("viewSingle")
	public String viewSingle(HttpServletRequest request,HttpServletResponse response,SyAnnouncement announcement){
		if(announcement.getId() != null){
			announcement = announcementService.getEntityById(SyAnnouncement.class, announcement.getId(), true);
		} else {
			SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
			announcement.setIsTop(BooleanEnum.YES);
			announcement.setIsPublish(BooleanEnum.YES);
	        Page<SyAnnouncement> page = announcementService.pageList(announcement,user, 1);
	        
	        if(page.getItems()!=null && !page.getItems().isEmpty()){
	        	announcement = page.getItems().get(0);
	        }
		}
		request.setAttribute("announcement", announcement);
		return "param/announcement/viewSingle";
	}
	
	/**
	 * 个人消息队列
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("myList")
	@ResponseBody
	public Map<String, Object> myList(HttpServletRequest request,HttpServletResponse response){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		SyUser user = (SyUser) request.getSession().getAttribute("loginUser");
		
		Map<String, Object> map = new HashMap<String,Object>();
        Page<SyAnnouncement> page = announcementService.pageList(null,user, pn);
        
        map.put("aaData", page.getItems());
        map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
		return map;
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyAnnouncement announcement){
		int length = ServletRequestUtils.getIntParameter(request, "length", 10);
		int start = ServletRequestUtils.getIntParameter(request, "start", 0);
		int pn = start == 0?1:(start/length+1);
		Map<String, Object> map = new HashMap<String,Object>();
		Page<SyAnnouncement> page = announcementService.pageList(announcement, pn, "headline","postType","isPublish");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, SyAnnouncement announcement){
		announcement = announcementService.getEntityById(SyAnnouncement.class, announcement.getId(), true);
		request.setAttribute("announcement", announcement);
		return "param/announcement/add";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, SyAnnouncement announcement){
		try{
			SyUser user = (SyUser)request.getSession().getAttribute("loginUser");
			
			announcementService.saveAnnouncement(announcement, user);
			announcement = announcementService.getEntityById(SyAnnouncement.class, announcement.getId(), true);
			request.setAttribute("msg", "保存成功!");
			request.setAttribute("announcement", announcement);
		}catch(Exception e){
			logger.error("保存失败,错误信息：",e);
			request.setAttribute("msg","保存失败,"+e.getMessage());
			request.setAttribute("announcement", announcement);
			return "param/announcement/add";
		}
		return "param/announcement/publish";
	}
	
	@RequestMapping("savePublish")
	public String savePublish(HttpServletRequest request, SyAnnouncement announcement){
		try{
			announcementService.updateAnnouncement(announcement);
			request.setAttribute("msg", "保存成功!");
		}catch(Exception e){
			logger.error("保存失败,错误信息：",e);
			request.setAttribute("msg","保存失败,"+e.getMessage());
			request.setAttribute("announcement", announcement);
			return "param/announcement/publish";
		}
		return "param/announcement/list";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request, SyAnnouncement announcement){
		announcement = announcementService.getEntityById(SyAnnouncement.class, announcement.getId(), true);
		request.setAttribute("announcement", announcement);
		return "param/announcement/view";
	}
	
	@RequestMapping("viewUserAjax")
	public void viewUserAjax(HttpServletRequest request,HttpServletResponse response,SyAnnouncement announcement){
		PrintWriter pw = null;
		try{
			pw = response.getWriter();
			announcement = announcementService.getEntityById(SyAnnouncement.class, announcement.getId(), true);
			List<String> list = formatUserTree(announcement);
			pw.print(JSONArray.fromObject(list).toString());
		} catch(Exception e){
			logger.error("加载失败,错误信息：",e);
		}
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, SyAnnouncement announcement){
		try{
			announcementService.deleteEntity(announcement);
			request.setAttribute("msg", "删除成功!");
		}catch(Exception e){
			logger.error("删除失败,错误信息：",e);
			request.setAttribute("msg","删除失败,"+e.getMessage());
		}
		return "param/announcement/list";
	}
	
	@RequestMapping("upload")
    public void upload(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request, HttpServletResponse response){
    	PrintWriter pw = null;
    	try {
    		pw = response.getWriter();
    		response.setContentType("text/html;charset=UTF-8");
    		String fileName = file.getOriginalFilename();
    		String path = PropertyUtil.getPropertyValue("ANNOUNCEMENT_FILE_PATH")+fileName;
			String id = request.getParameter("id");
			FileUtils.writeByteArrayToFile(new File(path), file.getBytes());
			
			SyAnnouncement announcement = announcementService.getEntityById(SyAnnouncement.class, Integer.parseInt(id), false);
			announcement.setFilename(fileName);
			announcement.setFileAddr(path);
			announcementService.updateEntity(announcement, "filename","fileAddr");
			pw.print("{\"msg\":\"success\",\"fileName\":\""+fileName+"\"}");
    	} catch (IOException e) {
    		logger.error("上传失败,错误信息：",e);
    		pw.print("{\"msg\":\""+e.getMessage()+"\"}");
			e.printStackTrace();
		}
    }
	
	@RequestMapping("download")
	public void download(HttpServletRequest request, HttpServletResponse response,SyAnnouncement announcement){
		try{
			announcement = announcementService.getEntityById(SyAnnouncement.class, announcement.getId(), false);
			DownloadUtil.download(request, response, new File(announcement.getFileAddr()), announcement.getFilename());
		} catch(Exception e){
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
	
	//封装用户树
		public List<String> formatUserTree(SyAnnouncement announcement){
			List<String> list = new ArrayList<String>();
			int i = 999999;
			list.add("{id:\""+i+"\",pId:\"0\",name:\"系统用户树\",isParent:true}");
			
			List<Integer> userIds = new ArrayList<Integer>();
			if(announcement.getUsers()!=null && !announcement.getUsers().isEmpty()){
				for(SyUser user : announcement.getUsers()){
					userIds.add(user.getUserId());
				}
			}
			Map<String,String> typeMap = new HashMap<String, String>();
			int j=1;
			for(UserTypeEnum userTypeEnum : UserTypeEnum.values()){
				if(!"".equals(userTypeEnum.getValue())){
					typeMap.put(userTypeEnum.getValue(), (i-j)+"");
					list.add("{id:\""+(i-j)+"\",pId:\""+i+"\",name:\""+userTypeEnum.getName()+"\"}");
					j++;
				}
			}
			SyUser syUser = new SyUser();
			syUser.setUserStatus(UserStatusEnum.N);
			List<SyUser> users = userService.getEntitysByParams(syUser,"userStatus");
			for(SyUser user : users){
				if(userIds.contains(user.getUserId())){
					list.add("{id:\""+user.getUserId()+"\",pId:\""+typeMap.get(user.getUserType().getValue())+"\",name:\""+user.getUserName()+"_"+user.getTrueName()+"\",checked:true}");
				} else {
					list.add("{id:\""+user.getUserId()+"\",pId:\""+typeMap.get(user.getUserType().getValue())+"\",name:\""+user.getUserName()+"_"+user.getTrueName()+"\"}");
				}
			}
			return list;
		}
}
