package com.waimai.controller.permission;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.waimai.model.permission.Role;
import com.waimai.model.permission.User;
import com.waimai.service.permission.RoleService;
import com.waimai.service.permission.UserService;
import com.waimai.service.permission.impl.LogUtil;
import com.waimai.util.LogType;
import com.waimai.util.MsgUtil;
import com.waimai.util.PageRainier;


@Controller
@RequestMapping("/background/sys")
@Scope("prototype")
/**
 * UserController:管理员的类
 * nian ge
 * 2014年11月29日 下午10:05:35
 * @version 1.0.0
 */
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	private PageRainier<User> users;
	private Integer pageSize = 10;
	
	@RequestMapping("/user/{pageNo}")
	public String list(@PathVariable Integer pageNo,Model model,HttpServletRequest request){
		System.out.println(1244443);
		users = userService.findAllUser(pageNo, pageSize, true);
		model.addAttribute("page",users);//map
		return "admin/sys/user/list";
	}
	
	@RequestMapping(value="/user/add",method=RequestMethod.GET)
	public String add(Model model) {
		return "admin/sys/user/add";
	}
	
	@RequestMapping(value="/user/add",method=RequestMethod.POST)
	public String add(User user, HttpServletRequest request) {
		try {
			user.setAccountNonLocked(true);
			//日志记录
			Role role = roleService.loadRoleByName(request.getParameter("role"));
			Role defaultR = roleService.findDefault();
			Set<Role> roles = new HashSet<Role>();
			roles.add(role);		//设置用户选择的权限
			roles.add(defaultR);	//设置默认权限
			user.setRoles(roles);
			userService.saveUser(user);
			LogUtil.getInstance().log(LogType.ADD,"用户名："+user.getUsername()+" 姓名："+user.getRealName());
		} catch (Exception e) {
			MsgUtil.setMsgAdd("error");
			e.printStackTrace();
		}
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX+"/admin/sys/user/users/1";
	}
	
	@RequestMapping(value="/user/{username}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable String username,Model model){
		model.addAttribute("model",userService.loadUserByName(username));
		return "admin/sys/user/detail";
	}
	
	@RequestMapping(value="/user/{username}/update",method=RequestMethod.GET)
	public String update(@PathVariable String username,Model model) {
		if (username != null) {
			model.addAttribute("model",userService.loadUserByName(username));
			model.addAttribute("rolesAjax", roleService.findAllByAjax());
		}
		return "admin/sys/user/update";
	}
	
	@RequestMapping(value="/user/{username}/update",method=RequestMethod.POST)
	public String update(HttpServletRequest request,@PathVariable String username,User user) {
		Set<Role> roles = null;
		StringBuilder content = new StringBuilder();
		if(user.getId()!=null){
			User temp = userService.loadUserById(user.getId());
			if(!temp.getUsername().equals(user.getUsername())){
				content.append("用户名由\""+temp.getUsername()+"\""+"修改为\""+user.getUsername()+"\";");
			}else{
				content.append("用户名："+temp.getUsername());
			}
			if(!temp.getRealName().equals(user.getRealName())){
				content.append("姓名由\""+temp.getRealName()+"\"修改为\""+user.getRealName()+"\"");
			}else{
				content.append("姓名："+temp.getRealName());
			}
			
			roles = new HashSet<Role>();
			String role = request.getParameter("role");
			String enabled = request.getParameter("enabled");
			user.setPassword(temp.getPassword());
			user.setAccountNonLocked(temp.getAccountNonLocked());
			user.setLastCloseDate(temp.getLastCloseDate());
			if(enabled!=null){
				user.setEnabled(true);
			}else{
				user.setEnabled(false);
				user.setLastCloseDate(new Date());
			}
			roles.add(roleService.findDefault());
			roles.add(roleService.loadRoleByName(role));
			user.setRoles(roles);
			userService.saveUser(user);
			MsgUtil.setMsgUpdate("success");
			LogUtil.getInstance().log(LogType.EDIT,content.toString());
		}
		return "redirect:/admin/sys/user/users/1";
	}
	
	@RequestMapping(value="/user/existUser",method=RequestMethod.POST)
	public String existUser(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		try {
			String username = request.getParameter("username");
			String name = request.getParameter("u");		//name为空表示添加，否则为编辑
			if(username!=null){
				response.setContentType("text/html;charset=UTF-8");
				out = response.getWriter();
				//如果没有修改username
				if(username.equals(name)){
					out.print(true);	//true表示可用
				}else{
					User u = userService.loadUserByName(username);
					if(u!=null){
						out.print(false);
					}else{
						out.print(true);	//true表示可用，用户名不存在
					}
				}
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
	}
	
	@RequestMapping(value="/user/{username}/reset",method=RequestMethod.GET)
	public String reset(@PathVariable String username,User user,HttpServletResponse response){
		PrintWriter out = null;
		String actionMsg = "";
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			if(user.getUsername()!=null){
				userService.resetPassword(user.getUsername());
				actionMsg = "重置密码成功！";
				LogUtil.getInstance().log(LogType.RESETPASSWORD, user.getUsername()+"的密码重置了");//日志记录
				out.write(actionMsg);
				out.flush();
			}else{
				actionMsg = "用户不存在！重置密码失败！";
				out.write(actionMsg);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
	}
	
	@RequestMapping(value="/user/{username}/unsubscribe",method=RequestMethod.GET)
	public String unsubscribe(@PathVariable String username,User user){
		if (user.getUsername() != null) {
			user = userService.loadUserByName(user.getUsername());
			userService.unsubscribe(user);
			MsgUtil.setMsg("success", "注销用户成功！");
			//日志记录
			LogUtil.getInstance().log(LogType.NSUBSCTIBE, user.getUsername()+"被注销了");
		}
		return "redirect:/admin/sys/user/users/1";
	}

	public PageRainier<User> getUsers() {
		return users;
	}

	public void setUsers(PageRainier<User> users) {
		this.users = users;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
