package com.wissen.mbeans;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.wissen.model.User;
import com.wissen.service.UserService;

@ManagedBean(name = "userbean")
public class UserBean {

	private User user;

	@EJB
	UserService userService;

	@PostConstruct
	public void init() {
		user = new User();
	}

	public String saveWithJDBC() {
		userService.saveUserWithJDBC(this.user);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", "User is registered with JDBC"));
		return "userbean";

	}

	public String saveWithJPA() {
		userService.saveUserWithJPA(this.user);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", "User is registered with JPA"));
		return "userbean";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
