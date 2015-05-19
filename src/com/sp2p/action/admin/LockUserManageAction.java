package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fp2p.helper.ExcelHelper;
import com.fp2p.helper.infusion.SqlInfusionHelper;
import com.shove.data.DataException;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.service.UserService;

/**
 * 锁定用户
 */
public class LockUserManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(LockUserManageAction.class);
	private static final long serialVersionUID = 1L;

	private UserService userService;

	/**
	 * 查询锁定用户初始化
	 * 
	 * @return
	 */
	public String queryLockedUsersInit() {
		return SUCCESS;
	}

	/**
	 * 查询锁定用户
	 * 
	 * @return
	 */
	public String queryLockedUsersInfo() {
		String userName = SqlInfusionHelper
				.filteSqlInfusion(paramMap.get("username"));
		String realName = SqlInfusionHelper
				.filteSqlInfusion(paramMap.get("realName"));
		String startTime = SqlInfusionHelper.filteSqlInfusion(paramMap
				.get("startTime"));
		String endTime = SqlInfusionHelper.filteSqlInfusion(paramMap.get("endTime"));
		try {
			userService.queryLockUsers(userName, realName, startTime, endTime,
					2, pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 导出询锁定用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportLockedUsersInfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 用户名
			String userName = SqlInfusionHelper.filteSqlInfusion(request()
					.getParameter("userName") == null ? "" : request()
					.getParameter("userName"));
			userName = URLDecoder.decode(userName, "UTF-8");
			// 真实姓名
			String realName = SqlInfusionHelper.filteSqlInfusion(request()
					.getParameter("realName") == null ? "" : request()
					.getParameter("realName"));
			realName = URLDecoder.decode(realName, "UTF-8");
			// 时间
			String startTime = SqlInfusionHelper.filteSqlInfusion(request()
					.getParameter("startTime") == null ? "" : request()
					.getParameter("startTime"));
			String endTime = SqlInfusionHelper.filteSqlInfusion(request()
					.getParameter("endTime") == null ? "" : request()
					.getParameter("endTime"));
			startTime = URLDecoder.decode(startTime, "UTF-8");
			endTime = URLDecoder.decode(endTime, "UTF-8");
			// 待还款详情
			userService.queryLockUsers(userName, realName, startTime, endTime,
					2, pageBean);
			if (pageBean.getPage() == null) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return null;
			}
			if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
				getOut()
						.print(
								"<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return null;
			}
			HSSFWorkbook wb = ExcelHelper.exportExcel("锁定用户列表", pageBean
					.getPage(), new String[] { "账号", "真实姓名", "手机", "身份证",
					"锁定时间" }, new String[] { "username", "realName",
					"cellPhone", "idNo", "lockTime" });
			this.export(wb, new Date().getTime() + ".xls");
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解除锁定用户
	 * 
	 * @return
	 */
	public String unLockedUsers() {
		String ids = SqlInfusionHelper.filteSqlInfusion(request("id"));
		try {
			if (StringUtils.isNotBlank(ids)) {
				userService.updateLockedStatus(ids, 1);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 锁定用户
	 * 
	 * @return
	 */
	public String lockingUsers() {
		String ids = SqlInfusionHelper.filteSqlInfusion(request("id"));
		try {
			if (StringUtils.isNotBlank(ids)) {
				userService.updateLockedStatus(ids, 2);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查询未锁定用户初始化
	 * 
	 * @return
	 */
	public String queryLockingUsersInit() {
		return SUCCESS;
	}

	/**
	 * 查询未锁定用户初始化
	 * 
	 * @return
	 */
	public String queryLockingUsersInfo() {
		String userName = SqlInfusionHelper.filteSqlInfusion(paramMap.get("username"));
		String realName = SqlInfusionHelper.filteSqlInfusion(paramMap.get("realName"));
		String startTime = SqlInfusionHelper.filteSqlInfusion(paramMap.get("startTime"));
		String endTime = SqlInfusionHelper.filteSqlInfusion(paramMap.get("endTime"));
		try {
			userService.queryLockUsers(userName, realName, startTime, endTime,
					1, pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}