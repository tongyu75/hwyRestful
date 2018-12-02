package com.czz.hwy.action.mission.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.app.CheckDutyApp;
import com.czz.hwy.bean.mission.app.CheckImageApp;
import com.czz.hwy.bean.mission.app.CheckTimeApp;
import com.czz.hwy.bean.mission.app.EvalTypeApp;
import com.czz.hwy.service.mission.app.CheckDutyAppService;
import com.czz.hwy.service.mission.app.CheckImageAppService;
import com.czz.hwy.service.mission.app.CheckTimeAppService;
import com.czz.hwy.service.mission.app.EvalTypeAppService;
import com.czz.hwy.utils.ConstantUtil;

/***
 * 考核上传，查询图片功能，用于app接口
 * @author 张咏雪
 * @Date 2016-11-09
 */
@Controller
@RequestMapping(value = "/checkImageAppController")
public class CheckImageAppController {

	@Autowired
	private CheckImageAppService checkImageAppService;//图片功能业务层

	@Autowired
	private CheckTimeAppService checkTimeAppService;//五分钟考核业务层
	
	@Autowired
	private CheckDutyAppService checkDutyAppService;//五分钟考核责任人业务层
	
	@Autowired
	private EvalTypeAppService evalTypeAppService;//考核分类业务层
	
	
    // 定义附件类型
// 	private static final String fileSuffix[] = { ".PDF", ".BMP", ".PNG",".GIF", ".JPG", ".JPEG" };
    
 	// 定义考核类型为5克任务
 	private static final int EVAL_TYPE_GRAM = 1;
 	
 	// 定义考核类型为5分钟任务
 	private static final int EVAL_TYPE_MINUTES = 2;
	
	/**
	 * 新增排班计划，2016-10-20
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST) 
    @ResponseBody
	public Map<String,Object> uploadFile(String acceptContent, HttpServletResponse response, HttpServletRequest request){
//    public Map<String,Object> uploadFile(CheckImageApp checkImageApp, HttpServletResponse response, HttpServletRequest request){
		
		JSONObject json = JSONObject.fromObject(acceptContent);
		CheckImageApp checkImageApp = (CheckImageApp) JSONObject.toBean(json, CheckImageApp.class);
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		if (checkImageApp.getCheckId() == 0 || checkImageApp.getEvalId() == 0) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", "附件上传失败:考核任务或考核类型不存在！");
			return map;
		}
		EvalTypeApp evalTypeApp = new EvalTypeApp();
		evalTypeApp.setEvalValue(checkImageApp.getEvalId());
		evalTypeApp = evalTypeAppService.getEvalTypeByBeanApp(evalTypeApp);//根据考核类型ID获取考核类型详细信息，2016-11-09
		if(evalTypeApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information", "附件上传失败：考核类型不存在！");
			return map;
		}
				
		// 判断该任务是否存在
//		CheckGramApp resultGram = null;
		CheckTimeApp resultTime = null;
//		Reports resultReport = null;
		if (evalTypeApp.getType() == EVAL_TYPE_GRAM){//五克考核
//			CheckGramApp checkGramApp = new CheckGramApp();
//			checkGram.setId(checkIdInt);
//			checkGram.setEvalType(evalTypeInt);
//			resultGram = workService.getWorkForGramInfoByBean(checkGram);
		} else if (evalTypeApp.getType() == EVAL_TYPE_MINUTES) {//五分钟考核
			CheckTimeApp checkTimeApp = new CheckTimeApp();
			checkTimeApp.setId(checkImageApp.getCheckId());
			checkTimeApp.setEvalType(checkImageApp.getEvalId());
			resultTime = checkTimeAppService.getCheckTimeByBeanApp(checkTimeApp);//根据主键ID和考核类型ID获取五分钟考核信息，2016-11-09
		} else {//监察或举报考核
//			Reports report = new Reports();
//			report.setId(checkIdInt);
//			report.setSupervisorType(evalTypeInt);
//			resultReport = workService.getWorkReportInfoByBean(report);
		}
//		if (resultGram == null && resultMinutes == null && resultReport == null) {
		if (resultTime == null) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", "上传失败：该考核任务不存在！");
			return map;
		}
		// 取得需要上传的文件数组
		List<File> files = checkImageApp.getImage();
		int index = 0;
		int result = 0;
		for (File file : files) {
			index ++;
			// 上传附件
			// 定义文件保存的路径
			FileInputStream is = null;
			FileOutputStream fos = null;
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("/");
			String downLoadPath = ctxPath + "public/work/image/";
			String savefileName = downLoadPath + checkImageApp.getCheckId()
					+ "_" + checkImageApp.getEvalId() + index + ".jpg";
			try {
				File newfile = new File(downLoadPath);
				if(!newfile.exists()){
					newfile.mkdirs();
				}
				is = new FileInputStream(file);
				fos = new FileOutputStream(savefileName);
				byte[] buff = new byte[1024 * 8];
				int len = 0;
				while ((len = is.read(buff)) > 0) {
					fos.write(buff, 0, len);
				}
			} catch (Exception e) {
				// 如果上传失败，则删除该任务信息
				if (evalTypeApp.getType() == EVAL_TYPE_GRAM){//五克考核
//					CheckGram checkGram = new CheckGram();
//					checkGram.setId(checkIdInt);
//					checkGram.setEvalType(evalTypeInt);
//					workService.deleteWorkForGramInfoByBean(checkGram);
				} else if (evalTypeApp.getType() == EVAL_TYPE_MINUTES) {//五分钟考核
					CheckTimeApp checkTimeApp = new CheckTimeApp();
					checkTimeApp.setId(checkImageApp.getCheckId());
					checkTimeApp.setEvalType(checkImageApp.getEvalId());
					checkTimeAppService.deleteCheckTimeByBeanApp(checkTimeApp);//根据主键ID和考核类型ID，删除五分钟考核记录，2016-11-09
					CheckDutyApp checkDutyApp = new CheckDutyApp();
					checkDutyApp.setCheckId(checkImageApp.getCheckId());
					checkDutyApp.setEvalType(checkImageApp.getEvalId());
					checkDutyAppService.deleteCheckDutyByBeanApp(checkDutyApp);//根据主键ID和考核类型ID，删除五分钟考核责任人记录，2016-11-09
				} else {//监察或举报考核
//					Reports report = new Reports();
//					report.setId(checkIdInt);
//					report.setSupervisorType(evalTypeInt);
//					workService.deleteWorkForReportInfoByBean(report);
				}
				
				checkImageAppService.deleteCheckImageByBeanApp(checkImageApp);//根据外键ID和考核类型ID，删除图片信息。2016-11-09
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.FILE_SAVE_FAIL);
				return map;
			} finally {
				try {
					if (fos != null) {
						fos.flush();
						fos.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (Exception e1) {
					map.put("result", ConstantUtil.FAIL);
					map.put("information", ConstantUtil.ERROR_MSG);
					return map;
				}

			}
			try {
				// 如果文件保存成功，需要加文件信息保存到数据库
				CheckImageApp checkImage = new CheckImageApp();
				// 设置任务ID
				checkImage.setCheckId(checkImageApp.getCheckId());
				// 设置任务类型
				checkImage.setEvalId(checkImageApp.getEvalId());
				// 设置插入时间
				checkImage.setCreateAt(new Date());
				// 设置文件属性
				// 文件属性
				Path path = Paths.get(file.getAbsolutePath());
				String contentType;
				contentType = Files.probeContentType(path);
				checkImage.setImageContentType(contentType);
				// 文件名字
				checkImage.setImageFileName(file.getName());
				// 文件大小
				Long l = new Long(file.length());
				checkImage.setImageFileSize(l.toString());
				result += checkImageAppService.insetCheckImageByBeanApp(checkImage);//新增一条图片信息记录，2016-11-09
			} catch (IOException e) {
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.ERROR_MSG);
				return map;
			}
		}
		if(result > 0){
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", "上传成功！");
		}else{
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.FILE_SAVE_FAIL);
		}
		return map;
	} 
}
