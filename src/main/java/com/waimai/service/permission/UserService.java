package com.waimai.service.permission;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.waimai.model.permission.User;
import com.waimai.util.PageRainier;


public interface UserService extends UserDetailsService {
	/**
	 * @FunName: findAllUser
	 * @Description:  查询所有用户。也是用户列表
	 * @param pageNo
	 * @param pageSize
	 * @param flag
	 * @return
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public com.waimai.util.PageRainier<User> findAllUser(Integer pageNo,Integer pageSize,boolean flag);
	/**
	 * @FunName: loadUserByName
	 * @Description:  根据用户名查询用户。用户名是唯一的
	 * @param username
	 * @return
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public User loadUserByName(String username);
	/**
	 * @FunName: saveUser
	 * @Description:  保存用户
	 * @param model
	 * @return
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public User saveUser(User model);
	/**
	 * @FunName: deleteUser
	 * @Description:  删除用户
	 * @param model
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public void deleteUser(User model);
	/**
	 * @FunName: deleteUserById
	 * @Description:  根据ID删除用户
	 * @param id
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public void deleteUserById(Serializable id);
	/**
	 * @FunName: getPaswordById
	 * @Description:  
	 * @param id
	 * @return
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public String getPaswordById(Serializable id);
	/**
	 * @FunName: findCount
	 * @Description:  计算所有用户数
	 * @return
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public Long findCount();
	/**
	 * @FunName: batchAdd
	 * @Description:  批量添加
	 * @param lists
	 * @return
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public List<User> batchAdd(List<User> lists);
	/**
	 * @FunName: batchDel
	 * @Description:  批量删除
	 * @param users
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public void batchDel(List<User> users);
	/**
	 * @FunName: findUserByRoleLike
	 * @Description:  根据用户的角色模糊查询
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public PageRainier<User> findUserByRoleLike(String role,Integer pageNo,Integer pageSize);
	/**
	 * @FunName: loadUserById
	 * @Description:  根据用户ID加载用户对象
	 * @param id
	 * @return
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public User loadUserById(Serializable id);
	/**
	 * @FunName: unsubscribe
	 * @Description:  注销用户
	 * @param model
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public void unsubscribe(User model);
	/**
	 * @FunName: unsubscribe
	 * @Description:  根据用户Id注销
	 * @param id
	 * @Author: 李年
	 * @CreateDate: 2013-5-24
	 */
	public void unsubscribe(Serializable id);
	
	public void resetPassword(String username);
	
}
