package com.liuxc.proSever.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.liuxc.proSever.entity.User;

@RestController
@RequestMapping(value="/user")
public class UserController {

	static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
	
	@ApiOperation(value="获取用户列表", notes="")
	@RequestMapping(value="", method=RequestMethod.GET)
	public List<User> getUserList() {
		List<User> userList = new ArrayList<User>(users.values());
		return userList;
	}
	
	@ApiOperation(value="创建用户", notes="根据user对象创建用户")
	@ApiImplicitParam(name="user", value="用户实体bean", required=true, dataType="User")
	@RequestMapping(value="", method=RequestMethod.POST)
	public String postUser(@RequestBody User user) {
		users.put(user.getId(), user);
		return "success";
	}
	
	@ApiOperation(value="获取用户信息", notes="根据ID获取用户信息")
	@ApiImplicitParam(name="id", value="用户ID", required=true, dataType="Long")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public User getUser(@PathVariable("id") Long id) {
		return users.get(id);
	}
	
	@ApiOperation(value="更新用户信息", notes="根据URL中ID指定更新对象")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id", value="用户ID", required=true, dataType="Long"),
		@ApiImplicitParam(name="user", value="用户entity", required=true, dataType="User")
	})
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String putUser(@PathVariable Long id, @RequestBody User user) {
		User u = users.get(id);
		u.setName(user.getName());
		u.setAge(user.getAge());
		users.put(id, u);
		return "success";
	}
	
	@ApiOperation(value="删除用户", notes="根据URL中ID删除指定用户")
	@ApiImplicitParam(name="id", value="用户ID", required=true, dataType="Long")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String deleteUser(@PathVariable Long id) {
		users.remove(id);
		return "success";
	}
}
