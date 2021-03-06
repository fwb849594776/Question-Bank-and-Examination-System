/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.1
  Time: 10:18
  Info:
*/

package justdj.top.controller.manager;

import justdj.top.pojo.Role;
import justdj.top.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Controller
public class RoleController {
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	private Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.6.1
	 *@description 角色管理界面 待完善
	 */
	@RequestMapping(value = "/ma/role",method = RequestMethod.GET)
	public String roleManagerPage(Model model){
		
		
		List<Role> roleList;
		roleList = roleService.selectAllRole();
		model.addAttribute(roleList);
		
		return "/ma/adminRoleManage";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [name, permissionList, model]
	 *@return  void
	 *@date  18.6.1
	 *@description 角色管理界面 待完善 添加角色
	 */
	@RequestMapping(value = "/ma/role",method = RequestMethod.POST)
	@ResponseBody
	public String addRole(@RequestParam("name")String name,
		                @RequestParam(value = "permission",required = false)String[] permissionList,
		                Model model){
		
//		System.err.println(permissionList.length + permissionList.toString());
		Role role = new Role();
		role.setName(name);
		role.setPermission(Arrays.asList(permissionList));
		try{
			roleService.addRoleWithPermission(role);
		}catch (RuntimeException e){
			e.printStackTrace();
			logger.info("新建角色失败！" + e.getMessage());
			return "新建角色失败！";
		}
		
		
		logger.info("新建角色成功");
		return "新建角色成功！";
	}
	
	
	
	
}
