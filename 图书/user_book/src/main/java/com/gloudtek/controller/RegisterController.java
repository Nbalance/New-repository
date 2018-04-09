package com.gloudtek.controller;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gloudtek.entity.SysUserEntity;
import com.gloudtek.service.SysUserRoleService;
import com.gloudtek.service.SysUserService;
import com.gloudtek.utils.MailSendUtil;
import com.gloudtek.utils.R;
import com.gloudtek.utils.RoleUtil;
import com.gloudtek.utils.SearchUtil;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping("api/v1/Processregister")
public class RegisterController {
	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 注册获取邮箱验证码
	 * 
	 * @param request
	 * @param response
	 * @return SearchUtil
	 * @throws IOException
	 */

	@ResponseBody
	@RequestMapping(value = "/send")
	public R send(HttpServletResponse response, HttpServletRequest request, @RequestBody SearchUtil searchUtil)
			throws IOException {
		try {
			// 生成文字验证码
			String checkCode = producer.createText();// 验证码
			if (StringUtils.isBlank(searchUtil.getEmail())) {
				return R.error("用户邮箱不能为空");
			}
			if (!searchUtil.getEmail().matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
				return R.error("用户邮箱错误");
			}
			String sendTo = searchUtil.getEmail();// 邮箱
			// 主题
			String subject = "关于获取验证码的邮件";
			StringBuilder builder = new StringBuilder();
			builder.append("<html><head>");
			builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
			builder.append("</head><body>");
			builder.append("尊敬的");
			builder.append(sendTo);
			builder.append("<br />欢迎您使用：伟哥(家里蹲)搬砖无限公司<br />");
			builder.append("\t验证码为：");
			builder.append(checkCode);
			builder.append("<br /><br />");
			builder.append("邮编：433000<br />电话：0728-2743131<br />传真：010-2743131<br />"
					+ "网址：www.wgjld.com<br />公司地址：武汉市汉阳大道201号");
			builder.append("</body></html>");
			String Content = builder.toString();
			MailSendUtil.sendMail(sendTo, subject, Content);// 调用发送邮箱的方法，给用户发送邮箱
			final HttpSession session = request.getSession();
			session.setAttribute("validateCode", checkCode);// 把验证码放入session
			final Timer timer = new Timer();// 定时器，验证码10分钟之内没有输入并注册就失效
			timer.schedule(new TimerTask() {
				public void run() {
					session.removeAttribute("validateCode");
					timer.cancel();
				}
			}, 1000 * 60 * 10);// 设定指定的时间time,此处为10分钟
			return R.ok("验证码发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("验证码发送失败");
		}
	}

	/**
	 * 注册用户
	 * 
	 * @param response
	 * @param request
	 * @param searchUtil
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public R verification(HttpServletResponse response, HttpServletRequest request, @RequestBody SearchUtil searchUtil)
			throws IOException {
		try {
			// 获取session中的验证码去比较
			HttpSession session = request.getSession();
			if (StringUtils.isBlank((String) session.getAttribute("validateCode"))) {
				return R.error("请获取验证码");
			} else if (!session.getAttribute("validateCode").equals(searchUtil.getValidateCode())) {
				return R.error("验证码错误");
			}
			if (StringUtils.isBlank(searchUtil.getUsername())) {
				return R.error("用户账号不能为空");
			} else if (!searchUtil.getUsername().matches("[a-zA-Z0-9]{6,16}")) {
				return R.error("用户账号:为字母和数字最短6位最长不能超过16位");
			} else if (StringUtils.isBlank(searchUtil.getPassword())) {
				return R.error("密码不能为空");
			} else if (!searchUtil.getUsername().matches("[a-zA-Z0-9]{6,16}")) {
				return R.error("密码:为字母和数字最短6位最长不能超过16位");
			} else if (StringUtils.isBlank(searchUtil.getEmail())) {
				return R.error("邮箱不能为空");
			} else if (!searchUtil.getEmail()
					.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
				return R.error("用户邮箱错误");
			}
			SysUserEntity sysUser = sysUserService.checkuser(searchUtil.getUsername());
			if (sysUser != null) {
				return R.error("您输入的用户账号已存在");
			}
			SysUserEntity SysMail = sysUserService.checkemail(searchUtil.getEmail());
			if (SysMail != null) {
				return R.error("您输入的邮箱已被注册");
			} else {
			/*	String password = new Sha256Hash(searchUtil.getPassword()).toHex();*/
				SysUserEntity Sys = new SysUserEntity();
				Sys.setUsername(searchUtil.getUsername());
				Sys.setEmail(searchUtil.getEmail());
				Sys.setPassword(searchUtil.getPassword());
				Sys.setStatus(1);
				Sys.setRemark("用户");
				sysUserService.save(Sys);
			}
			SysUserEntity sysUsers = sysUserService.checkuser(searchUtil.getUsername());
			RoleUtil roleUtil = new RoleUtil();
			roleUtil.setUserId(sysUsers.getUserId());
			roleUtil.setRoleId((long) 3);
			sysUserRoleService.saveRole(roleUtil);
			return R.ok("注册成功");
		} catch (Exception e) {
			return R.error("注册失败");
		}
	}

	/**
	 * 修改用户密码
	 * 
	 * @param response
	 * @param request
	 * @param searchUtil
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public R updatepassword(HttpServletResponse response, HttpServletRequest request,
			@RequestBody SearchUtil searchUtil) throws IOException {
		try {
			// 获取session中的验证码去比较
			HttpSession session = request.getSession();
			if (StringUtils.isBlank((String) session.getAttribute("validateCode"))) {
				return R.error("请获取验证码");
			} else if (!session.getAttribute("validateCode").equals(searchUtil.getValidateCode())) {
				return R.error("验证码错误");
			} else if (!searchUtil.getPassword().matches("[a-zA-Z0-9]{6,16}")) {
				return R.error("密码:为字母和数字最短6位最长不能超过16位");
			} else if (StringUtils.isBlank(searchUtil.getEmail())) {
				return R.error("邮箱不能为空");
			} else if (!searchUtil.getEmail()
					.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
				return R.error("用户邮箱错误");
			}
			SysUserEntity SysMail = sysUserService.checkemail(searchUtil.getEmail());
			if (SysMail == null) {
				return R.error("您输入的邮箱不存在,请注册");
			} else {
				String password = new Sha256Hash(searchUtil.getPassword()).toHex();
				SysUserEntity sys = new SysUserEntity();
				sys.setEmail(searchUtil.getEmail());
				sys.setPassword(password);
				sysUserService.updatepass(sys);
			}
			return R.ok("密码修改成功");
		} catch (Exception e) {
			return R.ok("密码修改失败");
		}
	}
}
