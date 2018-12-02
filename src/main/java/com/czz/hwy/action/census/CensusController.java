package com.czz.hwy.action.census;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.user.AttendanceForPlans;
import com.czz.hwy.service.area.DutyAreaService;
import com.czz.hwy.service.mission.CheckGramService;
import com.czz.hwy.service.mission.NoQualifiedNumberService;
import com.czz.hwy.service.usermanagement.AttendanceForPlansService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;

/**
 * 统计管理
 * @author 佟士儒
 *
 */
@Controller
@RequestMapping(value = "/censusController")
public class CensusController {
	
	public static final String FILE_SEPARATOR = System.getProperties()
			.getProperty("file.separator");

    @Autowired
    private AttendanceForPlansService attService;
    
    @Autowired
    private DutyAreaService areaService;
    
    @Autowired
    private NoQualifiedNumberService noQualifiedNumberService;
    
    @Autowired
    private CheckGramService checkGramService;
    
    @Resource
    private AccessOrigin accessOrigin;    
    /**
     * 道路清洁程度（"以克论净"之平均克数）
     */
    @RequestMapping(value="/fiveGramEvg", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> todayFiveGramSupervision(HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object>();
        // 获取从第一到第七责任区当天五克平均克数
        List<Map<String, Object>> lstBean = noQualifiedNumberService.getFiveGramEvg();
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", lstBean.size());
        map.put("rows", lstBean);
        return map;
    }    
    
    /**
     * 五克检测合格情况（"以克论净"之检测力度）
     */
    @RequestMapping(value="/fiveGramNum", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getFiveGramNum(HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object>();
        // 获取从第一到第七责任区当天五克检测次数
        List<Map<String, Object>> lstBean = noQualifiedNumberService.getFiveGramNum();
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", lstBean.size());
        map.put("rows", lstBean);
        return map;
    }
    
    /**
     * 环卫工出勤率（当前环卫工出勤情况统计）
     */
    @RequestMapping(value="/attCensus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAttCensus(HttpServletRequest request, 
            HttpServletResponse response) throws IOException{
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object>();
        // 员工考勤状态
        List<Map<String, Object>> lstWorkAtt = attService.getAllCityWorkAtt();
        // 考勤查岗的责任区
        List<Map<String, Object>> lstArea = areaService.selectAttendanceInspectArea();
        
        // 取得第一个责任区ID
        int areaId = 0;
        if (lstWorkAtt.size() > 0) {
            Map<String, Object> mp = lstWorkAtt.get(0);
            areaId = (Integer) mp.get("areaId");
        }
        // 初始化考核参数
        List<Map<Object, Object>> lstMp = new ArrayList<Map<Object, Object>>();
        Map<Object, Object> mpParm = this.getInitParam();
        for (Map<String, Object> mp : lstWorkAtt) {
            // 当责任区不同时，建立新的返回值
            if (areaId == (Integer) mp.get("areaId")) {
                for (Map<String, Object> area : lstArea) {
                    if ((Integer) mp.get("areaId") == area.get("areaId")) {
                        this.setParam(mpParm, mp);
                    }
                }
                mpParm.put("areaName", (String) mp.get("areaName"));
                mpParm.put("areaId", (Integer) mp.get("areaId"));
            } else {
                lstMp.add(mpParm);
                mpParm = this.getInitParam();
                for (Map<String, Object> area : lstArea) {
                    if ((Integer) mp.get("areaId") == area.get("areaId")) {
                        this.setParam(mpParm, mp);
                    }
                }
                areaId = (Integer) mp.get("areaId");
                // 设置责任区名称
                mpParm.put("areaName", (String) mp.get("areaName"));
                // 设置责任区ID
                mpParm.put("areaId", areaId);
            }
        }
        lstMp.add(mpParm);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", lstMp.size());
        map.put("rows", lstMp);
        return map;
    }
    
    /**
     * 设置返回参数
     */
    public void setParam(Map<Object, Object> mpParm, Map<String, Object> mp) {
        if ("上班".equals(mp.get("curStatus"))) {
            mpParm.put("workNum", (Long) mp.get("num"));
        } else if ("迟到".equals(mp.get("curStatus"))) {
            mpParm.put("lateNum", (Long) mp.get("num"));
        } else if ("未上班".equals(mp.get("curStatus"))) {
            mpParm.put("offNum", (Long) mp.get("num"));
        } else if ("任务中".equals(mp.get("curStatus"))) {
            mpParm.put("taskNum", (Long) mp.get("num"));
        } else if ("请假中".equals(mp.get("curStatus"))) {
            mpParm.put("leaveNum", (Long) mp.get("num"));
        }    
    }
    
    /**
     * 初始化考核参数
     */
    public Map<Object, Object> getInitParam() {
        // 初始化考核参数
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("areaName", "");
        map.put("areaId", "");
        map.put("leaveNum", 0l);
        map.put("workNum", 0l);
        map.put("offNum", 0l);
        map.put("lateNum", 0l);
        map.put("taskNum", 0l);
        return map;
    }
    
    /**
     * 考勤统计
     */
    @RequestMapping(value="/attendanceCount", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> attendanceCount(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","参数不能为空！");
			return map;
		}
    	// 对请求参数解码并转换为相应对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        paramMap.put("employeeId", json.getString("employeeId"));
    	String recordDate = json.getString("recordDate");
    	if("".equals(recordDate)){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        	recordDate = sdf.format(new Date());
        	paramMap = getSundays(new Date(),paramMap,recordDate);
        	paramMap.put("recordDate", recordDate);
    	}else{
    		paramMap = getSundays(new Date(),paramMap,recordDate);
        	paramMap.put("recordDate", json.getString("recordDate"));
    	}
        paramMap.put("showName", json.getString("showName"));
        paramMap.put("areaId", json.getString("areaId"));
        paramMap.put("pointId", json.getString("pointId"));
    	int row = json.getInt("row");
    	row = (row - 1) * 10;
        paramMap.put("row", row);
        // 返回考勤统计集合
        List<Map<String, Object>> resultList = attService.attendanceCountInfo(paramMap);
        int count = attService.attendanceCountTotal(paramMap);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", count);
        map.put("rows", resultList);
        return map;
    }    
    
    /**
     * 考勤统计
     */
    @RequestMapping(value="/attendanceInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> attendanceInfo(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object>();
        //1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","参数不能为空！");
			return map;
		}
        JSONObject json = JSONObject.fromObject(acceptContent);
        AttendanceForPlans attendanceForPlans = (AttendanceForPlans) JSONObject.toBean(json, AttendanceForPlans.class);
        // 返回考勤统计集合
        List<Map<String, Object>> resultList = attService.attendanceInfo(attendanceForPlans);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("rows", resultList);
        return map;
    }    
    
    //返回当月星期天数
    public static Map<String,Object> getSundays(Date dat,Map<String,Object> paramMap,String date) {
    	List<Integer> list = new ArrayList<Integer>();
    	SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.CHINA);
    	Calendar setDate = Calendar.getInstance();
    	// 从第一天开始
    	int day;
    	for (day = 1; day <= getDaysOfTheMonth(dat); day++) {
    		setDate.set(Calendar.DATE, day);
    		String str = sdf.format(setDate.getTime());
    		//System.out.println("str:"+str);
    		if (str.equals("星期日")) {
    			list.add(day);
    		}
    	}
    	SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
    	String month = sdf2.format(new Date());
    	int index = month.indexOf("-");
    	String day2 = month.substring(index + 1);
    	int _day = Integer.parseInt(day2);
    	if(month.substring(0, index).equals(date.substring(date.indexOf("-") + 1))){
    		if(_day > list.get(3)){
				paramMap.put("jiaban", 4);
			}else if(_day > list.get(2)){
				paramMap.put("jiaban", 3);
			}else if(_day > list.get(1)){
				paramMap.put("jiaban", 2);
			}else if(_day > list.get(0)){
				paramMap.put("jiaban", 1);
			}
    	}else{
    		paramMap.put("jiaban", 4);
    	}
    	return paramMap;
    }

    public static int getDaysOfTheMonth(Date date){//获取当月天数
    	Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date); // 要计算你想要的月份，改变这里即可
    	int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
    	return days;
    }
    
    
    public static CellStyle cellStyle = null;
    
    /**
     * 为单元格设置边框及前景色（棕色 10号）
     * @param wb
     */
    private static void createCellStyle(Workbook wb){
    	cellStyle = wb.createCellStyle();
    	HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
        palette.setColorAtIndex((short) 10, (byte) 255, (byte) 204, (byte) 153); 
        cellStyle.setFillForegroundColor((short) 10);// 前景色(10是在palette中设计的序号)
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
    	HSSFFont font = (HSSFFont) wb.createFont();
    	font.setFontName("宋体");
    	font.setFontHeightInPoints((short) 10);// 设置字体大小
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle.setFont(font);
    	cellStyle.setWrapText(true);//设置自动换行
    	cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
    	cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
    }
    
    public static CellStyle cellStyle2 = null;
    
    /**
     * 为单元格设置边框及前景色（黄色 8号）
     * @param wb
     */
    private static void createCellStyle2(Workbook wb){
    	cellStyle2 = wb.createCellStyle();
    	HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
		palette.setColorAtIndex((short) 11, (byte) 255, (byte) 255, (byte) 0); 
		cellStyle2.setFillForegroundColor((short) 11);// 前景色(10是在palette中设计的序号)
		cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
    	HSSFFont font = (HSSFFont) wb.createFont();
    	font.setFontName("宋体");
    	font.setFontHeightInPoints((short) 8);// 设置字体大小
    	cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
    	cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
    	cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
    	cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
    	cellStyle2.setFont(font);
    	cellStyle2.setWrapText(true);//设置自动换行
    	cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
    	cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
    }
    
    public static CellStyle cellStyle3 = null;
    
    /**
     * 为单元格设置边框及前景色（棕色 8号）
     * @param wb
     */
    private static void createCellStyle3(Workbook wb){
    	cellStyle3 = wb.createCellStyle();
    	HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
        palette.setColorAtIndex((short) 10, (byte) 255, (byte) 204, (byte) 153); 
        cellStyle3.setFillForegroundColor((short) 10);// 前景色(10是在palette中设计的序号)
        cellStyle3.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
    	HSSFFont font = (HSSFFont) wb.createFont();
    	font.setFontName("宋体");
    	font.setFontHeightInPoints((short) 8);// 设置字体大小
    	cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
    	cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
    	cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
    	cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
    	cellStyle3.setFont(font);
    	cellStyle3.setWrapText(true);//设置自动换行
    	cellStyle3.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
    	cellStyle3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
    }
    
    public static String filePath = "";
    
    @RequestMapping(value="/exportAttendace", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> exportAttendace(String acceptContent, HttpServletRequest request,HttpServletResponse response) throws Exception{
    	// 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        Map<String,Object> map = new HashMap<String,Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","参数不能为空！");
			return map;
		}
    	// 对请求参数解码并转换为相应对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
    	String recordDate = json.getString("recordDate");
    	String dutyArea = json.getString("dutyArea");
    	int year = Integer.parseInt(recordDate.substring(0, recordDate.indexOf("-")));
    	int month = Integer.parseInt(recordDate.substring(recordDate.indexOf("-") + 1));
    	String docsPath = request.getSession().getServletContext()
				.getRealPath("file");
		String templateName = "考勤表模板.xls";// Excel模版
		String templatePath = docsPath + FILE_SEPARATOR + templateName;
		String fileName = year+"年"+month+"月份考勤表.xls";// 导出Excel文件名
		filePath = docsPath + FILE_SEPARATOR + fileName;
		
		try {
			boolean flag2 = true;//判断是否填入表中 姓名和员工号
			boolean flag3 = true;//判断是否填入表中 责任区
			int index = 0;//姓名员工号 索引递增
			int index3 = 0;//上下班状态 行 索引递增
			int index5 = 0;
			Calendar setDate = Calendar.getInstance();
			setDate.clear();
			setDate.set(Calendar.YEAR, year);
			setDate.set(Calendar.MONTH, month - 1);//注意,Calendar对象默认一月为0  
			SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.CHINA);
			int TotalDay = setDate.getActualMaximum(Calendar.DATE);//得到当月总天数
			if (TotalDay == 31) {
				index5 = -1;
			} else if (TotalDay == 28) {
				index5 = index5 + 2;
			} else if (TotalDay == 29) {
				index5++;
			} 
			AttendanceForPlans attendanceForPlans = new AttendanceForPlans();
			attendanceForPlans.setAreaId(Integer.parseInt(dutyArea));
			attendanceForPlans.setRecoreDateS(recordDate);
			//excel模板路径  
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					templatePath));
			//读取excel模板  
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			createCellStyle(wb);
			createCellStyle2(wb);
			createCellStyle3(wb);
			//读取了模板内所有sheet内容  
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFCell _cell = sheet.getRow(0).getCell(0);
			_cell.setCellValue(year + "年" + month + "月份考勤表");
			//日期和星期赋值
			for (int k = 1; k <= TotalDay + index5; k++) {
				setDate.set(Calendar.DATE, k);
				String week = sdf.format(setDate.getTime());
				week = week.substring(2);
				//在相应的单元格进行赋值  
				HSSFCell cell = sheet.getRow(2).getCell(2 + k - 1);
				cell.setCellValue(week);
				HSSFCell cell2 = sheet.getRow(3).getCell(2 + k - 1);
				cell2.setCellValue(k);
			}
			List<Map<String, Object>> employeeIdList = attService
					.getEmployeeId(attendanceForPlans);//查询环卫工员工号
			for (Map<String, Object> employeeId : employeeIdList) {
				int index2 = 0;//上下班状态 列 索引递增
				int banci = 0;
				flag2 = true;
				boolean _flag = true;
				Integer employeeId2 = (Integer) employeeId.get("employeeId");
				attendanceForPlans.setEmployeeId(employeeId2);
				for (int i = 1; i <= TotalDay + index5; i++) {
					attendanceForPlans.setRecoreDateS(recordDate + "-" + i);
					List<Map<String, Object>> resultList = attService
							.attendanceInfoExport(attendanceForPlans);
					if (resultList.size() > 0) {
						//责任区
						if (flag3) {
							HSSFCell _cell2 = sheet.getRow(1).getCell(2);
							_cell2.setCellValue((String) resultList.get(i).get(
									"area_name"));
							flag3 = false;
						}
						//姓名 员工号
						if (flag2) {
							attendanceForPlans.setRecoreDateS(recordDate);
							List<Map<String, Object>> maxBanciList = attService
									.attendanceMaxBanci(attendanceForPlans);
							Long banciLong = (Long) maxBanciList.get(0).get(
									"banci");
							banci = Integer.parseInt(String.valueOf(banciLong));
							for (int j = 0; j < banci; j++) {
								Row row = sheet.createRow(4 + index + j); // 创建一个行
								if (_flag) {
									sheet.addMergedRegion(new CellRangeAddress(
											4 + index, // 起始行
											4 + index + banci - 1, // 结束行
											0, // 起始列
											0 // 结束列
									));
								}
								row.createCell(0);

								for (Cell cell : row) {
									if (_flag) {
										cell.setCellValue((String) resultList
												.get(0).get("showname")
												+ "   "
												+ (Integer) resultList.get(0)
														.get("employeeId"));
										_flag = false;
									}
									cell.setCellStyle(cellStyle);
								}

							}
						}
						_flag = true;
						setDate.set(Calendar.DATE, i);
						String week = sdf.format(setDate.getTime());
						if (banci <= 2) {//两个班
							if (flag2) {
								HSSFRow _row = sheet.getRow(4 + index);
								_row.createCell(1 + index2);

								HSSFRow _row2 = sheet.getRow(5 + index);
								_row2.createCell(1 + index2);

								_row.getCell(1 + index2).setCellStyle(
										cellStyle3);
								_row.getCell(1 + index2).setCellValue("上午");

								_row2.getCell(1 + index2).setCellStyle(
										cellStyle3);
								_row2.getCell(1 + index2).setCellValue("下午");

								flag2 = false;
							}
							if (resultList.size() == 1) {
								String dutyOnTime = (String) resultList.get(0)
										.get("duty_on_time");
								dutyOnTime = dutyOnTime.substring(0,
										dutyOnTime.indexOf(":"));
								HSSFRow row = null;
								if (Integer.parseInt(dutyOnTime) < 12) {
									row = sheet.getRow(4 + index3);
									row.createCell(2 + index2);
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);

									setDataValidationList(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									HSSFRow row2 = sheet.getRow(5 + index3);
									row2.createCell(2 + index2);
									row2.getCell(2 + index2).setCellStyle(
											cellStyle2);

									setDataValidationList(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));
								} else {
									row = sheet.getRow(5 + index3);
									row.createCell(2 + index2);
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);

									setDataValidationList(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									HSSFRow row2 = sheet.getRow(4 + index3);
									row2.createCell(2 + index2);
									row2.getCell(2 + index2).setCellStyle(
											cellStyle2);

									setDataValidationList(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));
								}
								if ("星期日".equals(week)) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("◆");
								} else if ("上班".equals((String) resultList.get(
										0).get("get_on_status"))) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("√");
								} else if ("请假".equals((String) resultList.get(
										0).get("get_on_status"))) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("●");
								} else if ("休息".equals((String) resultList.get(
										0).get("get_on_status"))) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("▲");
								} else if ("未上班".equals((String) resultList
										.get(0).get("get_on_status"))) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("■");
								} else {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("√");
								}
							} else {
								for (int ii = 0; ii < resultList.size(); ii++) {
									HSSFRow row = sheet.getRow(4 + index + ii);
									row.createCell(2 + index2);

									setDataValidationList(sheet,
											(short) (4 + index + ii),
											(short) (4 + index + ii),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (4 + index + ii),
											(short) (4 + index + ii),
											(short) (2 + index2),
											(short) (2 + index2));
									if ("星期日".equals(week)) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"◆");
									} else if ("上班".equals((String) resultList
											.get(ii).get("get_on_status"))) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"√");
									} else if ("请假".equals((String) resultList
											.get(ii).get("get_on_status"))) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"●");
									} else if ("休息".equals((String) resultList
											.get(ii).get("get_on_status"))) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"▲");
									} else if ("未上班".equals((String) resultList
											.get(ii).get("get_on_status"))) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"■");
									} else {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue("√");
									}
								}
							}
							index2++;
						} else {//其他班
							if (flag2) {
								HSSFRow _row = sheet.getRow(4 + index);
								_row.createCell(1 + index2);

								HSSFRow _row2 = sheet.getRow(5 + index);
								_row2.createCell(1 + index2);

								HSSFRow _row3 = sheet.getRow(6 + index);
								_row3.createCell(1 + index2);

								_row.getCell(1 + index2).setCellStyle(
										cellStyle3);
								_row.getCell(1 + index2).setCellValue("上午");

								_row2.getCell(1 + index2).setCellStyle(
										cellStyle3);
								_row2.getCell(1 + index2).setCellValue("下午");

								_row3.getCell(1 + index2).setCellStyle(
										cellStyle3);
								_row3.getCell(1 + index2).setCellValue("晚上");

								for (int i4 = 3; i4 < banci; i4++) {
									HSSFRow _row5 = sheet
											.getRow(4 + index + i4);
									_row5.createCell(1 + index2);
									_row5.getCell(1 + index2).setCellStyle(
											cellStyle3);
								}

								flag2 = false;
							}
							if (resultList.size() == 1) {
								String dutyOnTime = (String) resultList.get(0)
										.get("duty_on_time");
								dutyOnTime = dutyOnTime.substring(0,
										dutyOnTime.indexOf(":"));
								HSSFRow row = null;
								if (Integer.parseInt(dutyOnTime) < 12) {
									row = sheet.getRow(4 + index3);
									row.createCell(2 + index2);

									setDataValidationList(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									row.getCell(2 + index2).setCellStyle(
											cellStyle2);

									HSSFRow row2 = sheet.getRow(5 + index3);
									row2.createCell(2 + index2);

									setDataValidationList(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									row2.getCell(2 + index2).setCellStyle(
											cellStyle2);

									HSSFRow row3 = sheet.getRow(6 + index3);
									row3.createCell(2 + index2);

									setDataValidationList(sheet,
											(short) (6 + index3),
											(short) (6 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (6 + index3),
											(short) (6 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									row3.getCell(2 + index2).setCellStyle(
											cellStyle2);

								} else if (Integer.parseInt(dutyOnTime) < 18) {
									row = sheet.getRow(5 + index3);
									row.createCell(2 + index2);
									setDataValidationList(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);

									HSSFRow row2 = sheet.getRow(4 + index3);
									row2.createCell(2 + index2);
									setDataValidationView(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));
									
									setDataValidationList(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									row2.getCell(2 + index2).setCellStyle(
											cellStyle2);

									HSSFRow row3 = sheet.getRow(6 + index3);
									row3.createCell(2 + index2);
									setDataValidationList(sheet,
											(short) (6 + index3),
											(short) (6 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (6 + index3),
											(short) (6 + index3),
											(short) (2 + index2),
											(short) (2 + index2));
									row3.getCell(2 + index2).setCellStyle(
											cellStyle2);
								} else {
									HSSFRow row3 = sheet.getRow(4 + index3);
									row3.createCell(2 + index2);
									setDataValidationList(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (4 + index3),
											(short) (4 + index3),
											(short) (2 + index2),
											(short) (2 + index2));
									row3.getCell(2 + index2).setCellStyle(
											cellStyle2);

									HSSFRow row2 = sheet.getRow(5 + index3);
									row2.createCell(2 + index2);
									setDataValidationList(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (5 + index3),
											(short) (5 + index3),
											(short) (2 + index2),
											(short) (2 + index2));
									row2.getCell(2 + index2).setCellStyle(
											cellStyle2);

									row = sheet.getRow(6 + index3);
									row.createCell(2 + index2);
									setDataValidationList(sheet,
											(short) (6 + index3),
											(short) (6 + index3),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (6 + index3),
											(short) (6 + index3),
											(short) (2 + index2),
											(short) (2 + index2));
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);

								}
								if ("星期日".equals(week)) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("◆");
								} else if ("上班".equals((String) resultList.get(
										0).get("get_on_status"))) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("√");
								} else if ("请假".equals((String) resultList.get(
										0).get("get_on_status"))) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("●");
								} else if ("休息".equals((String) resultList.get(
										0).get("get_on_status"))) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("▲");
								} else if ("未上班".equals((String) resultList
										.get(0).get("get_on_status"))) {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("■");
								} else {
									row.getCell(2 + index2).setCellStyle(
											cellStyle2);
									row.getCell(2 + index2).setCellValue("√");
								}
							} else {
								for (int ii = 0; ii < resultList.size(); ii++) {
									HSSFRow row = sheet.getRow(4 + index + ii);
									row.createCell(2 + index2);
									setDataValidationList(sheet,
											(short) (4 + index + ii),
											(short) (4 + index + ii),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (4 + index + ii),
											(short) (4 + index + ii),
											(short) (2 + index2),
											(short) (2 + index2));
									if ("星期日".equals(week)) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"◆");
									} else if ("上班".equals((String) resultList
											.get(ii).get("get_on_status"))) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"√");
									} else if ("请假".equals((String) resultList
											.get(ii).get("get_on_status"))) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"●");
									} else if ("休息".equals((String) resultList
											.get(ii).get("get_on_status"))) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"▲");
									} else if ("未上班".equals((String) resultList
											.get(ii).get("get_on_status"))) {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue(
												"■");
									} else {
										row.getCell(2 + index2).setCellStyle(
												cellStyle2);
										row.getCell(2 + index2).setCellValue("√");
									}

								}
							}
							if (resultList.size() != banci) {
								for (int q = resultList.size(); q < banci; q++) {
									HSSFRow _row5 = sheet.getRow(4 + index + q);
									_row5.createCell(2 + index2);
									setDataValidationList(sheet,
											(short) (4 + index + q),
											(short) (4 + index + q),
											(short) (2 + index2),
											(short) (2 + index2));

									setDataValidationView(sheet,
											(short) (4 + index + q),
											(short) (4 + index + q),
											(short) (2 + index2),
											(short) (2 + index2));
									_row5.getCell(2 + index2).setCellStyle(
											cellStyle2);
								}
							}
							index2++;
						}
					} else {
						for (int i3 = 0; i3 < banci; i3++) {
							HSSFRow _row = sheet.getRow(4 + index + i3);
							_row.createCell(2 + index2);
							setDataValidationList(sheet,
									(short) (4 + index + i3),
									(short) (4 + index + i3),
									(short) (2 + index2), (short) (2 + index2));

							setDataValidationView(sheet,
									(short) (4 + index + i3),
									(short) (4 + index + i3),
									(short) (2 + index2), (short) (2 + index2));
							_row.getCell(2 + index2).setCellStyle(cellStyle2);
						}
						index2++;
					}
					if (i == TotalDay + index5) {
						if (TotalDay == 29) {//当月29天
							for (int _i = 0; _i < banci; _i++) {
								HSSFRow row = sheet.getRow(4 + index3 + _i);
								row.createCell(31);
								setDataValidationList(sheet,
										(short) (4 + index3 + _i),
										(short) (4 + index3 + _i),
										(short) (31), (short) (31));

								setDataValidationView(sheet,
										(short) (4 + index3 + _i),
										(short) (4 + index3 + _i),
										(short) (31), (short) (31));
								row.getCell(31).setCellStyle(cellStyle2);
								row.getCell(31).setCellValue("√");
							}
						} else if (TotalDay == 28) {//当月28天
							for (int _i = 0; _i < banci; _i++) {
								HSSFRow row = sheet.getRow(4 + index3 + _i);
								row.createCell(31);
								setDataValidationList(sheet,
										(short) (4 + index3 + _i),
										(short) (4 + index3 + _i),
										(short) (31), (short) (31));

								setDataValidationView(sheet,
										(short) (4 + index3 + _i),
										(short) (4 + index3 + _i),
										(short) (31), (short) (31));
								row.getCell(31).setCellStyle(cellStyle2);
								row.getCell(31).setCellValue("√");
							}
							for (int _i = 0; _i < banci; _i++) {
								HSSFRow row = sheet.getRow(4 + index3 + _i);
								row.createCell(30);
								setDataValidationList(sheet,
										(short) (4 + index + _i),
										(short) (4 + index + _i), (short) (30),
										(short) (30));

								setDataValidationView(sheet,
										(short) (4 + index + _i),
										(short) (4 + index + _i), (short) (30),
										(short) (30));
								row.getCell(30).setCellStyle(cellStyle2);
								row.getCell(30).setCellValue("√");
							}
						} else if (TotalDay == 31) {//当月31天
							attendanceForPlans.setRecoreDateS(recordDate + "-" + 31);
							List<Map<String, Object>> resultList2 = attService
									.attendanceInfoExport(attendanceForPlans);
							for (int _i2 = 0; _i2 < resultList2.size(); _i2++) {
								if (!"上班".equals((String) resultList2.get(_i2)
										.get("get_on_status"))) {
									for (int _i3 = 31; _i3 > 1; _i3--) {
										HSSFCell cell = sheet.getRow(
												4 + index3 + _i2).getCell(_i3);
										String value = cell
												.getStringCellValue();
										if ("√".equals(value)) {
											if ("请假".equals((String) resultList2
													.get(_i2).get(
															"get_on_status"))) {
												cell.setCellValue("●31");
												cell.setCellStyle(
														cellStyle2);
											} else if ("休息"
													.equals((String) resultList2
															.get(_i2)
															.get("get_on_status"))) {
												cell.setCellValue("▲31");
												cell.setCellStyle(
														cellStyle2);
											} else if ("未上班"
													.equals((String) resultList2
															.get(_i2)
															.get("get_on_status"))) {
												cell.setCellValue("■31");
												cell.setCellStyle(
														cellStyle2);
											}
											break;
										}
									}
								}
							}
						}
					}
				}
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("recordDate", recordDate);
				paramMap.put("areaId", Integer.parseInt(dutyArea));
				paramMap.put("employeeId", employeeId2);
				List<Map<String, Object>> countAttendanceList = attService
						.attendanceCountInfoExport(paramMap);
				Long jiaban3 = (Long) countAttendanceList.get(0).get("jiaban");
				BigDecimal jiaban = new BigDecimal(jiaban3);
				BigDecimal xiuxi = (BigDecimal) countAttendanceList.get(0).get(
						"xiuxi");
				BigDecimal qingjia = (BigDecimal) countAttendanceList.get(0)
						.get("qingjia");
				BigDecimal kuanggong = (BigDecimal) countAttendanceList.get(0)
						.get("kuanggong");
				BigDecimal zhengchang = (BigDecimal) countAttendanceList.get(0)
						.get("zhengchang");
				String qingjia2 = new java.text.DecimalFormat("0.0")
						.format(qingjia);
				String xiuxi2 = new java.text.DecimalFormat("0.0")
						.format(xiuxi);
				String kuanggong2 = new java.text.DecimalFormat("0.0")
						.format(kuanggong);
				String jiaban2 = new java.text.DecimalFormat("0.0")
						.format(jiaban);
				String zhengchang2 = new java.text.DecimalFormat("0.0")
						.format(zhengchang);
				BigDecimal bigDecimal = new BigDecimal(4);
				if ((kuanggong.add(qingjia).add(xiuxi)).compareTo(bigDecimal) == 1
						|| (kuanggong.add(qingjia).add(xiuxi))
								.compareTo(bigDecimal) == 0) {//1：大于 0：等于 -1：小于
					zhengchang2 = new java.text.DecimalFormat("0.0")
							.format(zhengchang);

					for (int j = 0; j < banci; j++) {
						HSSFRow row = sheet.getRow(4 + index3 + j);
						if (_flag) {
							sheet.addMergedRegion(new CellRangeAddress(
									4 + index3, // 起始行
									4 + index3 + banci - 1, // 结束行
									33, // 起始列
									33 // 结束列
							));
						}
						row.createCell(33);

						if (_flag) {
							row.getCell(33).setCellValue(
									zhengchang2.substring(
											zhengchang2.indexOf(".") + 1)
											.equals("0") ? zhengchang2
											.substring(0,
													zhengchang2.indexOf("."))
											: zhengchang2);
							_flag = false;
						}
						row.getCell(33).setCellStyle(cellStyle);

					}
					_flag = true;

					for (int j = 0; j < banci; j++) {
						HSSFRow row = sheet.getRow(4 + index3 + j);
						if (_flag) {
							sheet.addMergedRegion(new CellRangeAddress(
									4 + index3, // 起始行
									4 + index3 + banci - 1, // 结束行
									34, // 起始列
									34 // 结束列
							));
						}
						row.createCell(34);

						if (_flag) {
							row.getCell(34).setCellValue(
									zhengchang2.substring(
											zhengchang2.indexOf(".") + 1)
											.equals("0") ? zhengchang2
											.substring(0,
													zhengchang2.indexOf("."))
											: zhengchang2);
							_flag = false;
						}
						row.getCell(34).setCellStyle(cellStyle);

					}

					_flag = true;
					
					for (int j = 0; j < banci; j++) {
						HSSFRow row = sheet.getRow(4 + index3 + j);
						if (_flag) {
							sheet.addMergedRegion(new CellRangeAddress(
									4 + index3, // 起始行
									4 + index3 + banci - 1, // 结束行
									35, // 起始列
									35 // 结束列
							));
						}
						row.createCell(35);

						row.getCell(35).setCellStyle(cellStyle);

					}

				} else {
					jiaban = jiaban.subtract(xiuxi).subtract(kuanggong)
							.subtract(qingjia);
					zhengchang = zhengchang.add(jiaban);
					jiaban2 = new java.text.DecimalFormat("0.0").format(jiaban);
					zhengchang2 = new java.text.DecimalFormat("0.0")
							.format(zhengchang);
					String shiji = new java.text.DecimalFormat("0.0")
							.format(zhengchang.add(jiaban));

					for (int j = 0; j < banci; j++) {
						HSSFRow row = sheet.getRow(4 + index3 + j);
						if (_flag) {
							sheet.addMergedRegion(new CellRangeAddress(
									4 + index3, // 起始行
									4 + index3 + banci - 1, // 结束行
									33, // 起始列
									33 // 结束列
							));
						}
						row.createCell(33);

						if (_flag) {
							row.getCell(33).setCellValue(
									shiji.substring(shiji.indexOf(".") + 1)
											.equals("0") ? shiji.substring(0,
											shiji.indexOf(".")) : shiji);
							_flag = false;
						}
						row.getCell(33).setCellStyle(cellStyle);

					}

					_flag = true;

					for (int j = 0; j < banci; j++) {
						HSSFRow row = sheet.getRow(4 + index3 + j);
						if (_flag) {
							sheet.addMergedRegion(new CellRangeAddress(
									4 + index3, // 起始行
									4 + index3 + banci - 1, // 结束行
									34, // 起始列
									34 // 结束列
							));
						}
						row.createCell(34);

						if (_flag) {
							row.getCell(34).setCellValue(
									zhengchang2.substring(
											zhengchang2.indexOf(".") + 1)
											.equals("0") ? zhengchang2
											.substring(0,
													zhengchang2.indexOf("."))
											: zhengchang2);
							_flag = false;
						}
						row.getCell(34).setCellStyle(cellStyle);

					}

					_flag = true;

					for (int j = 0; j < banci; j++) {
						HSSFRow row = sheet.getRow(4 + index3 + j);
						if (_flag) {
							sheet.addMergedRegion(new CellRangeAddress(
									4 + index3, // 起始行
									4 + index3 + banci - 1, // 结束行
									35, // 起始列
									35 // 结束列
							));
						}
						row.createCell(35);

						if (_flag) {
							row.getCell(35).setCellValue(
									jiaban2.substring(jiaban2.indexOf(".") + 1)
											.equals("0") ? jiaban2.substring(0,
											jiaban2.indexOf(".")) : jiaban2);
							_flag = false;
						}
						row.getCell(35).setCellStyle(cellStyle);

					}

					_flag = true;

				}

				for (int j = 0; j < banci; j++) {
					HSSFRow row = sheet.getRow(4 + index3 + j);
					if (_flag) {
						sheet.addMergedRegion(new CellRangeAddress(4 + index3, // 起始行
								4 + index3 + banci - 1, // 结束行
								36, // 起始列
								36 // 结束列
						));
					}
					row.createCell(36);

					if (_flag) {
						row.getCell(36).setCellValue(
								xiuxi2.substring(xiuxi2.indexOf(".") + 1)
										.equals("0") ? xiuxi2.substring(0,
										xiuxi2.indexOf(".")) : xiuxi2);
						_flag = false;
					}
					row.getCell(36).setCellStyle(cellStyle);

				}

				_flag = true;

				for (int j = 0; j < banci; j++) {
					HSSFRow row = sheet.getRow(4 + index3 + j);
					if (_flag) {
						sheet.addMergedRegion(new CellRangeAddress(4 + index3, // 起始行
								4 + index3 + banci - 1, // 结束行
								37, // 起始列
								37 // 结束列
						));
					}
					row.createCell(37);

					if (_flag) {
						row.getCell(37).setCellValue(
								qingjia2.substring(qingjia2.indexOf(".") + 1)
										.equals("0") ? qingjia2.substring(0,
										qingjia2.indexOf(".")) : qingjia2);
						_flag = false;
					}
					row.getCell(37).setCellStyle(cellStyle);

				}

				_flag = true;

				for (int j = 0; j < banci; j++) {
					HSSFRow row = sheet.getRow(4 + index3 + j);
					if (_flag) {
						sheet.addMergedRegion(new CellRangeAddress(4 + index3, // 起始行
								4 + index3 + banci - 1, // 结束行
								38, // 起始列
								38 // 结束列
						));
					}
					row.createCell(38);

					if (_flag) {
						row.getCell(38).setCellValue(
								kuanggong2.substring(
										kuanggong2.indexOf(".") + 1)
										.equals("0") ? kuanggong2.substring(0,
										kuanggong2.indexOf(".")) : kuanggong2);
						_flag = false;
					}
					row.getCell(38).setCellStyle(cellStyle);

				}

				_flag = true;

				for (int j = 0; j < banci; j++) {
					HSSFRow row = sheet.getRow(4 + index3 + j);
					row.createCell(32);
					setDataValidationList(sheet, (short) (4 + index + j),
							(short) (4 + index + j), (short) (32), (short) (32));

					setDataValidationView(sheet, (short) (4 + index + j),
							(short) (4 + index + j), (short) (32), (short) (32));

					row.getCell(32).setCellStyle(cellStyle2);

				}

				for (int j = 0; j < banci; j++) {
					HSSFRow row = sheet.getRow(4 + index3 + j);
					if (_flag) {
						sheet.addMergedRegion(new CellRangeAddress(4 + index3, // 起始行
								4 + index3 + banci - 1, // 结束行
								39, // 起始列
								39 // 结束列
						));
					}
					row.createCell(39);

					row.getCell(39).setCellStyle(cellStyle);

				}
				index = index + banci;
				index3 = index3 + banci;
			}
			FileOutputStream fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);
			fileOut.close();
			map.put("result", ConstantUtil.SUCCESS);
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			e.printStackTrace();
		}
		return map;
    }
    
    @RequestMapping(value="/downloadExal")
    private void download(HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(filePath);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(filename, "UTF-8"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
    
    public static void setDataValidationList(HSSFSheet sheet,short firstRow,short firstCol,short endRow, short endCol){  
        //设置下拉列表的内容  
        String[] textlist={"√","●","◆","▲","■"};
          
        //加载下拉列表内容  
        DVConstraint constraint=DVConstraint.createExplicitListConstraint(textlist);  
        //设置数据有效性加载在哪个单元格上。  
          
        //四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions=new CellRangeAddressList(firstRow,firstCol,endRow,endCol);  
        //数据有效性对象  
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint); 
        
        sheet.addValidationData(data_validation_list);
          
    }  
    public static void setDataValidationView(HSSFSheet sheet,short firstRow,short firstCol,short endRow, short endCol){  
        //构造constraint对象  
        DVConstraint constraint=DVConstraint.createCustomFormulaConstraint("BB1");  
        //四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions=new CellRangeAddressList(firstRow,firstCol,endRow,endCol);  
        //数据有效性对象  
        HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);  
        
        //设置提示内容,标题,内容  
        data_validation_view.createPromptBox("","√   正常 ● 请假 ◆   加班 ▲   休息 ■   旷工");
        //工作表添加验证数据  
        sheet.addValidationData(data_validation_view);
    }  
      
}
