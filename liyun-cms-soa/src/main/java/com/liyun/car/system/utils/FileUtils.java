package com.liyun.car.system.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;

import com.liyun.car.core.spring.SpringContextBeanFactory;
import com.liyun.car.system.service.UserService;

public class FileUtils extends Thread {
	
	public static UserService userService = SpringContextBeanFactory.getBean("userServiceImpl");
	private List<String> sqlList;
	private List<String[]> headList;
	private List<String> sheetNameList;
	private String fileName;
	
	@Override
	public void run() {
		try {
			getFileByBuildSql();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileUtils() {
		super();
	}

	public FileUtils(List<String> sqlList, List<String[]> headList, List<String> sheetNameList, String fileName) {
		super();
		this.sqlList = sqlList;
		this.headList = headList;
		this.sheetNameList = sheetNameList;
		this.fileName = fileName;
	}

	public File getFileByBuildSql() throws Exception{
		File file = new File(fileName);
		FileOutputStream fos = new FileOutputStream(file);
		SXSSFWorkbook swb = new SXSSFWorkbook(1000);
		Sheet sheet = null;
		
		//字体样式\只是美观
		Font font = swb.createFont();  
		font.setColor(XSSFFont.COLOR_NORMAL);
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 10);
		//表头样式
		CellStyle cellStyle = swb.createCellStyle();//创建格式
	    cellStyle.setFont(font);
	    cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    cellStyle.setWrapText(true);
		
		
		if(headList!=null && !headList.isEmpty()){
			for(int i=0;i<headList.size();i++){
				sheet = swb.createSheet(sheetNameList.get(i));
				String[] headers = headList.get(i);
				Row row = sheet.createRow(0);
				for(int j=0;j<headers.length;j++){
					 Cell cell = row.createCell(j);                     
	                 cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式  
	                 cell.setCellStyle(cellStyle);
	                 sheet.setColumnWidth(j, 20*256); //设置单元格宽度  
	                 cell.setCellValue(headers[j]);//写入内容
				}
				if(sqlList!=null && !sqlList.isEmpty()){
					buildLargeDataSheet(sqlList.get(i),sheet,cellStyle,0,200);
				}
			}
		}
		swb.write(fos);
		swb.close();
		fos.close();
		return file;
	}
	
	@SuppressWarnings("unchecked")
	public void buildLargeDataSheet(String sql,Sheet sheet, CellStyle cellStyle, int point, int step){
		List<Object[]> list = (List<Object[]>) userService.getObjByNativeSql(sql, point, step);
		if(list!=null &&!list.isEmpty()){
			for(int l=0;l<list.size();l++){
				Row bodyRow = null;
				if(point == 0){
					bodyRow = sheet.createRow(point*200 + l + 1);
				} else {
					bodyRow = sheet.createRow(point*200 + l + 1);
				}
				for(int m = 0;m<list.get(l).length;m++){
					Cell cell = bodyRow.createCell(m);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式
	                cell.setCellStyle(cellStyle);
					sheet.setColumnWidth(l, 20*256); //设置单元格宽度
					cell.setCellValue(list.get(l)[m]==null?"":list.get(l)[m].toString());
				}	
			}
			buildLargeDataSheet(sql, sheet, cellStyle, (point+1), step);
		}
	}
}
