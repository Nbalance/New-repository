package com.gloudtek.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;

import com.gloudtek.entity.PersonnelEntity;
import com.gloudtek.service.PersonnelService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.PersonneUtils;
import com.gloudtek.utils.R;


/**
 * 个人信息表
 * 
 * @author Seac.Cong
 * @email Seac.Cong@gloudtek.com
 * @date 2017-12-15 10:48:44
 */
@Controller
@RequestMapping("api/v1/personnel")
public class PersonnelController {
	private static final String WINDOWS_UPLOAD_PATH = "D:/data/user_book/personnel/";
	private static final String LINUX_UPLOAD_PATH = "/data/user_book/personnel/";
	@Autowired
	private PersonnelService personnelService;
	
	/**
	@RequestMapping("/personnel.html")
	public String list(){
		return "personnel/personnel.html";
	}
	*/
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("personnel:list")
	public R list(@RequestBody PersonneUtils personneUtils){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (personneUtils.getCurrentPage() - 1) * personneUtils.getPageSize());
		map.put("limit", personneUtils.getPageSize());
		map.put("personnelName", personneUtils.getPersonnelName());
	
		//查询列表数据
		List<PersonnelEntity> list = personnelService.queryList(map);
		int total = personnelService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(list, total, personneUtils.getPageSize(), personneUtils.getCurrentPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
//	@RequiresPermissions("personnel:info")
	public R info(@PathVariable("id") String id){
		PersonnelEntity personnel = personnelService.queryObject(id);
		
		return R.ok().put("personnel", personnel);
	}
	
	/**
	 * 文件上传
	 */
	@ResponseBody
	@RequestMapping(value = "/UploadFile", method = RequestMethod.POST)
	public R upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile fileName)throws Exception {
		try {
			String uploadFileName = fileName.getOriginalFilename();// 得到上传文件的文件名称
			String os = System.getProperty("os.name");
			String rootPath;
			if (os.toLowerCase().startsWith("win")) {
				rootPath = WINDOWS_UPLOAD_PATH;
			} else {
				rootPath = LINUX_UPLOAD_PATH;
			}
			// 上传文件
			File file = new File(rootPath);
			if (!file.isFile()) {
				file.mkdir();
			}
			File uploadfile = new File(file, fileName.getOriginalFilename());
			fileName.transferTo(uploadfile);
			PersonnelEntity pe = new PersonnelEntity();
			pe.setPersonnelName("路人甲");
			pe.setSex("男");
			pe.setAge("20");
			pe.setPlaceOrigin("昆仑山");
			pe.setFileName(uploadFileName);
			personnelService.save(pe);
			return R.ok("文件上传成功").put("uploadfile", uploadfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error("上传失败");
	}
	
	/**
	 * 
	 * @param下载
	 * @param request
	 * @param response
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping("/upload")  
	public R download(HttpServletRequest request, HttpServletResponse response, PersonnelEntity personnel)throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("multipart/form-data");
		try {
			String filename = personnel.getFileName();
			String os = System.getProperty("os.name");
			String rootPath;
			if (os.toLowerCase().startsWith("win")) {
				rootPath = WINDOWS_UPLOAD_PATH;
			} else {
				rootPath = LINUX_UPLOAD_PATH;
			}
			InputStream inputStream = new FileInputStream(new File(rootPath + filename));
			OutputStream osc = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.setContentType("application/OCTET-STREAM;charset=UTF-8");
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				osc.write(b, 0, length);
			}
			// 这里主要关闭。
			osc.close();
			inputStream.close();
			return R.ok("文件下载成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.error("下载失败");
	} 

	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions("personnel:save")
	public R save(@RequestBody PersonnelEntity personnel){
		personnelService.save(personnel);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("personnel:update")
	public R update(@RequestBody PersonnelEntity personnel){
		personnelService.update(personnel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions("personnel:delete")
	public R delete(@RequestBody String[] ids){
		personnelService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
