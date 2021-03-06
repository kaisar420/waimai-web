package com.waimai.service.permission.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.stereotype.Service;

import com.waimai.dao.permission.ResourceMapper;
import com.waimai.dao.permission.UserMapper;
import com.waimai.model.permission.Resource;
import com.waimai.model.permission.User;
import com.waimai.service.permission.UserService;
import com.waimai.util.PageRainier;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userDao;
	private UserCache userCache = new NullUserCache();
	@Autowired
	private ResourceMapper resourceDao ;
	/**
	 * 根据用户名查询用户，用户名唯一
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		return loadUserByName(username);
	}
	
	public User loadUserByName(String username) {
		return userDao.findByName(username);
	}
	
	/**
	 * 保存用户
	 */
	public User saveUser(User model) {
		try{
			if(model.getId()==null){	//新增用户
				model.setPassword(new Md5PasswordEncoder().encodePassword(model.getPassword(), null));
			}
			User u =  userDao.save(model);
			if(u!=null){
				return u;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除用户
	 */
	public void deleteUser(User model) {
		try{
			userDao.delete(model);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 根据Id删除用户
	 */
	public void deleteUserById(Serializable id) {
		try {
			userDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据Id删除密码
	 */
	public String getPaswordById(Serializable id) {
		return userDao.getPasswordById(id);
	}

	public UserMapper getUserDao() {
		return userDao;
	}

	public void setUserDao(UserMapper userDao) {
		this.userDao = userDao;
	}

	/**
	 * 批量添加
	 */
	public List<User> batchAdd(List<User> lists){
		return userDao.save(lists);
	}
	
	/**
	 * 批量删除
	 */
	public void batchDel(List<User> users){
		userDao.deleteInBatch(users);
	}
	
	public Long findCount() {
		return userDao.count();
	}
		
	/**
	 * @FunName: changePassword
	 * @Description:  修改密码
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @param currentUser 当前用户
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public void changePassword(String oldPassword, String newPassword, Authentication currentUser){
		currentUser = SecurityContextHolder.getContext().getAuthentication();
		if(currentUser ==null){
			throw new AccessDeniedException("修改密码错误，不存在此用户！");
		}
		String username = currentUser.getName();
		userDao.changePassword(username,newPassword);
		SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser,newPassword));
		userCache.removeUserFromCache(username);
	}
	
	protected Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());
        UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());
        return newAuthentication;
    }
	
	public UserCache getUserCache() {
		return userCache;
	}
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
	/**
	 * @FunName: resetPassword
	 * @Description:  重置密码
	 * @param username
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public void resetPassword(String username){
		userDao.changePassword(username, new Md5PasswordEncoder().encodePassword(username,null));
	}

	public User loadUserById(Serializable id) {
		return userDao.findOne(id);
	}
	/**
	 * 注销用户
	 */
	public void unsubscribe(User model) {
		userDao.unsubscribe(model.getUsername());
	}
	/**
	 * 注销用户
	 */
	public void unsubscribe(Serializable id) {
		userDao.unsubscribe(id);
	}
	
	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {
		//List<Resource> resources = resourceDao.findResourceByRole(user.getRoles());
		List<Resource> resources = null;
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Resource res : resources) {
			// TODO:ZZQ 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
			// 关联代码：applicationContext-security.xml
			// 关联代码：com.huaxin.security.MySecurityMetadataSource#loadResourceDefine
			authSet.add(new SimpleGrantedAuthority("ROLE_" + res.getRes_string()));
		}
		return authSet;
	}

	public PageRainier<User> findAllUser(Integer pageNo, Integer pageSize,
			boolean flag) {
		return null;
	}

	public PageRainier<User> findUserByRoleLike(String role, Integer pageNo,
			Integer pageSize) {
		return null;
	}
}
