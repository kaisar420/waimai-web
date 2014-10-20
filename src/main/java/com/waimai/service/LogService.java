package com.waimai.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.waimai.dao.LogDao;
import com.waimai.model.Log;
import com.waimai.model.Menu;
import com.waimai.model.User;
import com.waimai.util.DateConverter;
import com.waimai.util.LogType;
import com.waimai.util.PageRainier;


@Component("logService")
public class LogService {
	@Autowired
	private LogDao logDao;

	public Log saveLog(Log log){
		return logDao.save(log);
	}

	public void log(LogType type, String content) {
		Log log = new Log();
		User u = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		log.setType(type.getName());
		log.setContent(content);
		log.setCreateTime(new Date());
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
			      .getRequestAttributes()).getRequest();
		log.setMenu((Menu)request.getSession().getAttribute("menu"));
		log.setOperator(u.getUsername());
		log.setOperatorRealName(u.getRealName());
		logDao.save(log);
	}

	public void log(LogType type, 
			Map<String, Object> logMap) {
		Log log = new Log();
		User u = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		log.setCreateTime(new Date());
		//log.setMenu(module);
		log.setOperatorRealName(u.getRealName());
		log.setOperator(u.getUsername());
		log.setType(type.getName());
		Set<String> keys = logMap.keySet();
		StringBuilder sb = new StringBuilder();
		for(Iterator<String> it = keys.iterator();it.hasNext();){
			String key = it.next();
			Object obj = logMap.get(key);
			sb.append(key+"="+obj.toString()+" ");
		}
		log.setContent(sb.toString());
	}
}
