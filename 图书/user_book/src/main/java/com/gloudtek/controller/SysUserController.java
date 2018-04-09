package com.gloudtek.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gloudtek.dao.SysUserDao;
import com.gloudtek.entity.BasePage;
import com.gloudtek.entity.SysLogEntity;
import com.gloudtek.entity.SysRoleEntity;
import com.gloudtek.entity.SysUserEntity;
import com.gloudtek.service.SysLogService;
import com.gloudtek.service.SysRoleService;
import com.gloudtek.service.SysUserRoleService;
import com.gloudtek.service.SysUserService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;
import com.gloudtek.utils.RoleUtil;
import com.gloudtek.utils.ShiroUtils;
import com.gloudtek.utils.UploadUtil;
import com.gloudtek.utils.message.LogInfo;
import com.gloudtek.utils.message.Logutil;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/api/sys/user")
public class SysUserController extends AbstractController {

	private static final String WINDOWS_UPLOAD_PATH = "F:/data/user_book/imgs/";
	
	private static final String LINUX_UPLOAD_PATH = "/data/user_book/imgs/";

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysLogService sysLogService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	// @RequiresPermissions("sys:user:list")
	public R list(@RequestBody BasePage basePage) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (basePage.getCurrentPage() - 1) * basePage.getPageSize());
		map.put("limit", basePage.getPageSize());

		// 查询列表数据
		List<SysUserEntity> list = sysUserService.queryList(map);
		for (int i = 0; i < list.size(); i++) {
			SysUserEntity sys = list.get(i);
			if (sys.getStatus() == 1) {
				sys.setNormal("1");
			} else {
				sys.setNormal("0");
			}
		}
		// 查询列表数据
		List<SysRoleEntity> rolelist = sysRoleService.queryList(map);
		List<Object> sumlist = new ArrayList<>();
		sumlist.add(list);
		sumlist.add(rolelist);
		int total = sysUserService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(sumlist, total, basePage.getCurrentPage(), basePage.getPageSize());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info() {
		SysRoleEntity roleEntity = sysUserDao.queryRoleName(super.getUser().getUserId());
		SysUserEntity user = super.getUser();
		user.setRoleName(roleEntity.getRoleName());
		return R.ok().put("user", user);
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/infos")
	// @RequiresPermissions("sys:user:info")
	public R info(@RequestBody Long userId) {
		SysUserEntity userlist = sysUserService.queryObject(userId);
		return R.ok().put("user", userlist);
	}

	/**
	 * 保存用户
	 * 
	 * @param roleUtil
	 */
	@RequestMapping("/save")
	// @RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user) {
		if (StringUtils.isBlank(user.getUsername())) {
			return R.error("用户名不能为空");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return R.error("密码不能为空");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", user.getUsername());
		map.put("userId", user.getUserId());
		if (sysUserDao.selectName(map) > 0) {
			return R.error("您输入的用户账号已存在");
		}
		SysUserEntity SysMail = sysUserService.checkemail(user.getEmail());
		if (SysMail != null) {
			return R.error("您输入的邮箱已存在");
		} else {
			sysUserService.save(user);
		}
		RoleUtil roleUtil = new RoleUtil();
		roleUtil.setUserId(user.getUserId());
		sysUserRoleService.saveRole(roleUtil);
		return R.ok();
	}

	/**
	 * 修改用户
	 */
	@RequestMapping("/update")
	// @RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", user.getUsername());
		map.put("userId", user.getUserId());
		if (sysUserDao.selectName(map) > 0) {
			return R.error("您输入的用户账号已存在");
		} else {
			sysUserService.update(user); 
		}

		return R.ok();
	}

	/**
	 * 保存用户角色(如1.超级管理员2.管理员3.用户)
	 */
	@RequestMapping("/saverole")
	// @RequiresPermissions("sys:user:save")
	public R saverole(@RequestBody RoleUtil roleUtil) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", roleUtil.getUserId());
		if (roleUtil.getUserId() == 1) {
			return R.error("你已经是超级管理员不能在选择");
		}
		if (roleUtil.getRoleId() == null) {
			return R.error("角色选择不能为空");
		}
		if (roleUtil.getRoleId() == 1) {
			return R.error("超级管理员已被选定");
		} else {
			sysUserRoleService.updateRole(roleUtil);
		}
		return R.ok();
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	// @RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long userId) {
		if (userId == 1) {
			return R.error("超级管理员不能删除");
		}
		sysUserService.deletetype(userId);
		;
		return R.ok();
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/delUser")
	@RequiresPermissions("api:sys:user:delUser")
	public R delUser(@RequestBody Long[] suerId) {
		List<Long> list = new ArrayList<>();
		for (int i = 0; i < suerId.length; i++) {
			list.add(suerId[i]);
		}
		sysUserService.delUser(list);
		return R.ok("删除成功");
	}

	/**
	 * 修改用户，用户信息页面
	 */
	@RequestMapping("/updateInfo")
	// @RequiresPermissions("sys:user:update")
	public R updateInfo(@RequestBody SysUserEntity user) {

		if (StringUtils.isBlank(user.getUsername())) {
			return R.error("用户名不能为空");
		}
		user.setUpdateTime(new Date());
		user.setUpdateBy(user.getUsername());
		sysUserService.updateInfo(user);
		SysUserEntity users = sysUserService.queryByUserId(Long.toString(user.getUserId()));
		Subject subject = ShiroUtils.getSubject();
		String password = users.getPassword();
		UsernamePasswordToken token = new UsernamePasswordToken(users.getUsername(), password);
		subject.login(token);
		return R.ok();
	}

	/**
	 * 头像上传
	 * 
	 * @param username
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload")
	public R upload(String username, MultipartFile file, HttpServletRequest request, UploadUtil uploadUtil,
			LogInfo logInfo, Logutil logutil) {
		try {
			String fileurl = uploadUtil.upload(request, "upload", username);
			SysUserEntity sys = new SysUserEntity();
			sys.setUsername(username);
			sys.setUrl(fileurl);
			sysUserService.updateurl(sys);
			SysLogEntity syslog = new SysLogEntity();
			syslog.setUsername(username);
			syslog.setType(logInfo.LOG_TYPE_UPDATE);
			syslog.setDetail(logInfo.LOG_DETAIL_UPDATE_PROFILEHEAD);
			syslog.setIp(Logutil.getClientIp(request));
			syslog.setPlace(Logutil.getAddressByIP(request));
			sysLogService.save(syslog);
			return R.ok("用户头像更新成功");
		} catch (Exception e) {
			return R.error("用户头像更新失败");
		}
	}

	/**
	 * 获取用户头像
	 */
	@RequestMapping(value = "/head")
	public R head(String username, HttpServletRequest request, HttpServletResponse response) {
		try {
			SysUserEntity user = super.getUser();
			String path = user.getUrl();
			if(path==null){
				return R.error("系统找不到指定路径");
			}
			String os = System.getProperty("os.name");
			String rootPath;
			if (os.toLowerCase().startsWith("win")) {
				rootPath = WINDOWS_UPLOAD_PATH;
			} else {
				rootPath = LINUX_UPLOAD_PATH;
			}
			String picturePath = rootPath + path;
			response.setContentType("image/jpeg; charset=UTF-8");
			ServletOutputStream outputStream = response.getOutputStream();
			FileInputStream inputStream = new FileInputStream(picturePath);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			outputStream = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok();
	}
}
