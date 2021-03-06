package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.ShoveBorrowTypeDao;
import com.sp2p.dao.admin.ShoveBorrowTypeLogDao;

/**
 * 标种类型 service
 * 
 * @author C_J
 * 
 */
public class ShoveBorrowTypeService extends BaseService {
	public static Log log = LogFactory.getLog(ShoveBorrowTypeService.class);
	private ShoveBorrowTypeDao shoveBorrowTypeDao;
	private ShoveBorrowTypeLogDao shoveBorrowTypeLogDao;// 历史记录
	private ShoveBorrowStyleService shoveBorrowStyleService;

	/**
	 * 增加标种类型
	 * 
	 * @throws SQLException
	 */
	public Long addShoveBoorowType(String nid, int status, String name,
			String title, String description, double account_multiple,
			int password_status, int amount_type, double amount_first,
			double amount_end, double frost_scale_vip, double apr_first,
			double apr_end, int check_first, int check_end,
			String tender_account_min, String tender_account_max,
			String period_month, String period_day, String validate,
			int award_status, double award_scale_first, double award_scale_end,
			double award_account_first, double award_account_end,
			int subscribe_status, int verify_auto_status,
			String verify_auto_remark, String styles, double vip_frost_scale,
			int late_days_month, int late_days_day, double vip_late_scale,
			double all_late_scale, double all_frost_scale) throws Exception {

		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = shoveBorrowTypeDao.addShoveBoorowType(conn, nid, status,
					name, title, description, account_multiple,
					password_status, amount_type, amount_first, amount_end,
					frost_scale_vip, apr_first, apr_end, check_first,
					check_end, tender_account_min, tender_account_max,
					period_month, period_day, validate, award_status,
					award_scale_first, award_scale_end, award_account_first,
					award_account_end, subscribe_status, verify_auto_status,
					verify_auto_remark, styles, vip_frost_scale,
					late_days_month, late_days_day, vip_late_scale,
					all_late_scale, all_frost_scale);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return result;

	}

	/**
	 * 增加历史记录
	 * 
	 * @throws SQLException
	 */
	public Long addBorrowTypeLog(String nid, int status, String name,
			String title, String description, double account_multiple,
			int password_status, int amount_type, double amount_first,
			double amount_end, double apr_first, double apr_end,
			int check_first, int check_end, String tender_account_min,
			String tender_account_max, String period_month, String period_day,
			String validate, int award_status, double award_scale_first,
			double award_scale_end, double award_account_first,
			double award_account_end, int subscribe_status,
			String counter_guarantee, String institution, String styles,
			double vip_frost_scale, int late_days_month, int late_days_day,
			double vip_late_scale, double all_late_scale,
			double all_frost_scale, long user_id, long update_time,
			String update_ip, String identifier, double locan_fee,
			int locan_month, double locan_fee_month, double day_fee)
			throws Exception {
		Connection conn = MySQL.getConnection();// 得到连接
		long result = -1L;
		try {
			result = shoveBorrowTypeLogDao.addShoveBoorowTypeLog(conn, nid,
					status, name, title, description, account_multiple,
					password_status, amount_type, amount_first, amount_end,
					apr_first, apr_end, check_first, check_end,
					tender_account_min, tender_account_max, period_month,
					period_day, validate, award_status, award_scale_first,
					award_scale_end, award_account_first, award_account_end,
					subscribe_status, counter_guarantee, institution, styles,
					vip_frost_scale, late_days_month, late_days_day,
					vip_late_scale, all_late_scale, all_frost_scale, user_id,
					update_time, update_ip, identifier, locan_fee, locan_month,
					locan_fee_month, day_fee);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 根据编号查询标种
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public Map<String, String> queryShoveBorrowTypeById(int id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = shoveBorrowTypeDao.queryShoveBorrowTypeById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * 根据标识符查询标种
	 * 
	 * @param conn
	 * @param nid
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public Map<String, String> queryShoveBorrowTypeByNid(String Nid)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = shoveBorrowTypeDao.queryShoveBorrowTypeByNid(conn, Nid);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * 查询最小的投标金额
	 * 
	 * @param nid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<String> queryMinAmountListByNid(String nid) throws Exception {
		Connection conn = MySQL.getConnection();
		List<String> list = null;
		try {
			String result = shoveBorrowTypeDao.queryOneByNid(conn,
					"tender_account_min", nid);
			String[] results = result.split(",");
			if (results != null) {
				list = Arrays.asList(results);
			} else {
				list = new ArrayList<String>();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 查询最大的投标金额
	 * 
	 * @param nid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<String> queryMaxAmountListByNid(String nid) throws Exception {
		Connection conn = MySQL.getConnection();
		List<String> list = null;
		try {
			String result = shoveBorrowTypeDao.queryOneByNid(conn,
					"tender_account_max", nid);
			String[] results = result.split(",");
			if (results != null) {
				list = Arrays.asList(results);
			} else {
				list = new ArrayList<String>();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 查询筹款期限
	 * 
	 * @param nid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<String> queryRaiseTermLisByNid(String nid) throws Exception {
		Connection conn = MySQL.getConnection();
		List<String> list = null;
		try {
			String result = shoveBorrowTypeDao.queryOneByNid(conn, "validate",
					nid);
			String[] results = result.split(",");
			if (results != null) {
				list = Arrays.asList(results);
			} else {
				list = new ArrayList<String>();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 查询按天计算
	 * 
	 * @param nid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<String> queryDeadlineDayListByNid(String nid) throws Exception {
		Connection conn = MySQL.getConnection();
		List<String> list = null;
		try {
			String result = shoveBorrowTypeDao.queryOneByNid(conn,
					"period_day", nid);
			String[] results = result.split(",");
			if (results != null) {
				list = Arrays.asList(results);
			} else {
				list = new ArrayList<String>();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 查询按月计算
	 * 
	 * @param nid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<String> queryDeadlineMonthListByNid(String nid)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<String> list = null;
		try {
			String result = shoveBorrowTypeDao.queryOneByNid(conn,
					"period_month", nid);
			String[] results = result.split(",");
			if (results != null) {
				list = Arrays.asList(results);
			} else {
				list = new ArrayList<String>();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 修改标种类型
	 * 
	 * @throws SQLException
	 */
	public Long updateShoveBorrowType(int id, int status, String title,
			String description, double account_multiple, int password_status,
			int amount_type, double amount_first, double amount_end,
			double apr_first, double apr_end, int check_first, int check_end,
			String tender_account_min, String tender_account_max,
			String period_month, String period_day, String validate,
			int award_status, double award_scale_first, double award_scale_end,
			double award_account_first, double award_account_end,
			int subscribe_status, String institution, String counter_guarantee,
			String styles, double vip_frost_scale, int late_days_month,
			int late_days_day, double vip_late_scale, double all_late_scale,
			double all_frost_scale, int vorsion, String nid, double locan_fee,
			int locan_month, double locan_fee_month, double day_fee)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = shoveBorrowTypeDao.updateShoveBorrowType(conn, id, status,
					title, description, account_multiple, password_status,
					amount_type, amount_first, amount_end, apr_first, apr_end,
					check_first, check_end, tender_account_min,
					tender_account_max, period_month, period_day, validate,
					award_status, award_scale_first, award_scale_end,
					award_account_first, award_account_end, subscribe_status,
					counter_guarantee, institution, styles, vip_frost_scale,
					late_days_month, late_days_day, vip_late_scale,
					all_late_scale, all_frost_scale, vorsion, nid, locan_fee,
					locan_month, locan_fee_month, day_fee);
			conn.commit();
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 分页查询
	 * 
	 * @param conn
	 * @param pageBean
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public void queryShoveBorrowTypePageAll(
			PageBean<Map<String, Object>> pageBean) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			shoveBorrowTypeDao.queryShoveBorrowTypePageAll(conn, pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 删除
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public Long deleteShoveBorrowType(int id) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = shoveBorrowTypeDao.deleteShoveBorrowType(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 在历史记录表中查询 标种类型的历史记录
	 * 
	 * @param nid_log
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBorrowTypeLogByNid(String nid_log)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = shoveBorrowTypeLogDao.queryBorrowTypeLogByNid(conn, nid_log);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;

	}

	
	 public void changeShoveTitle(PageBean<Map<String, Object>> pageBean){
		 List<Map<String, Object>> list = pageBean.getPage();
		 try {
			for (Map<String, Object> map : list) {
				int i = 0;
				String stytyles = map.get("styles") + "";
				if (stytyles.length() > 0) {
					List<Map<String, Object>> titleList;
					
						titleList = shoveBorrowStyleService.queryBorrowAmountByIds(map.get("styles") + "");
					
					if (titleList != null) {
						StringBuffer buf = new StringBuffer();
						for (Map<String, Object> titleMap : titleList) {
							buf.append(titleMap.get("title"));
							i++;
							if (i != titleList.size()) {
								buf.append("|");
							}
						}
						map.put("titles", buf.toString());
					}
				}
			}
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	public void setShoveBorrowTypeDao(ShoveBorrowTypeDao shoveBorrowTypeDao) {
		this.shoveBorrowTypeDao = shoveBorrowTypeDao;
	}

	public void setShoveBorrowTypeLogDao(
			ShoveBorrowTypeLogDao shoveBorrowTypeLogDao) {
		this.shoveBorrowTypeLogDao = shoveBorrowTypeLogDao;
	}

}
