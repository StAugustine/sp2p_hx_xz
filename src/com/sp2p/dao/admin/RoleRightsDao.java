package com.sp2p.dao.admin;import java.sql.Connection;import java.sql.SQLException;import java.util.List;import java.util.Map;import org.apache.commons.lang.StringEscapeUtils;import com.fp2p.helper.shove.DataSetHelper;import com.shove.Convert;import com.shove.data.DataException;import com.shove.data.DataSet;import com.shove.data.dao.Database;import com.shove.data.dao.Parameter;import com.sp2p.constants.IConstants;import com.sp2p.database.Dao;public class RoleRightsDao {	public Long addRoleRihts(Connection conn,Long roleId,Long rightsId) throws SQLException{		Dao.Tables.t_role_rights role_rights = new Dao().new Tables().new t_role_rights();		role_rights.roleId.setValue(roleId);		role_rights.rightsId.setValue(rightsId);		return role_rights.insert(conn);	}		public Long updateRoleRihts(Connection conn,Long id,Long roleId,Long rightsId) throws SQLException{		Dao.Tables.t_role_rights role_rights = new Dao().new Tables().new t_role_rights();		role_rights.roleId.setValue(roleId);		role_rights.rightsId.setValue(rightsId);		return role_rights.update(conn, " id="+id);	}		/**	 * 根据角色编号删除角色权限	 * @param conn	 * @param roleId	 * @return	 * @throws SQLException	 */	public Long deleteRoleRightsByRoleId(Connection conn,long roleId) throws SQLException{		Dao.Tables.t_role_rights role_rights = new Dao().new Tables().new t_role_rights();		return role_rights.delete(conn, " roleId = "+roleId);	}		/**	 * 根据角色编号查询角色权限信息	 * @param conn	 * @param roleId 角色编号	 * @return	 * @throws SQLException	 * @throws DataException	 */	public List<Map<String,Object>> queryRoleRightByRoleId(Connection conn,long roleId) throws SQLException, DataException{		Dao.Tables.t_role_rights roleRight = new Dao().new Tables().new t_role_rights();		DataSet dataSet = roleRight.open(conn, "", " roleId ="+roleId, "", -1, -1);		dataSet.tables.get(0).rows.genRowsMap();		return dataSet.tables.get(0).rows.rowsMap;	}	/**	 * 根据角色编号查询权限信息	 * 	 * @param conn	 * @param roleId	 * @return	 * @throws SQLException	 * @throws DataException	 */	public List<Map<String, Object>> queryRoleRightsIdByRoleId(Connection conn,			long roleId) throws SQLException, DataException {		Dao.Tables.t_role_rights roleRight = new Dao().new Tables().new t_role_rights();		DataSet dataSet = roleRight.open(conn,				" roleId,rightsId", " roleId ="						+ roleId, "", -1, -1);		dataSet.tables.get(0).rows.genRowsMap();		return dataSet.tables.get(0).rows.rowsMap;	}		/**	 * 根据角色编号查询管理员角色权限信息	 * @param conn	 * @param roleId 角色编号	 * @return	 * @throws DataException 	 * @throws SQLException 	 */	public List<Map<String,Object>> queryAdminRoleRightMenu(Connection conn,long roleId) throws SQLException, DataException{		Dao.Views.v_t_role_rights_menu rrm = new Dao().new Views().new v_t_role_rights_menu();		DataSet dataSet = rrm.open(conn, "", " roleId = "+roleId, " indexs "+IConstants.SORT_TYPE_ASC, -1, -1);		dataSet.tables.get(0).rows.genRowsMap();		return dataSet.tables.get(0).rows.rowsMap;	}	/**	 * 查询角色是否有权限操作该路径	 * 	 * @param roleId	 *            角色编号	 * @return	 * @throws DataException	 * @throws SQLException	 * @throws SQLException	 * @throws DataException	 */	public boolean queryAdminRoleIsHaveRights(Connection conn, long roleId,			String url) throws SQLException, DataException {		// 判断是否存在该条action，不存在不拦截		StringBuilder sql = new StringBuilder(" ");		sql.append("SELECT count(1) as counts from bt_rights ");		sql.append(" where  bt_rights.action='");		sql.append(StringEscapeUtils.escapeSql(url));		sql.append("'");		DataSet ds = Database.executeQuery(conn, sql.toString(),				new Parameter[] {});		Map<String, String> map = DataSetHelper.dataSetToMap(ds);		int count = Convert.strToInt(map.get("counts"), -1);		if (count == 0) {			return true;		}		sql = new StringBuilder(" ");		sql.append("SELECT count(1) as counts from t_role_rights ");		sql				.append("left join bt_rights on t_role_rights.rightsId = bt_rights.id");		sql.append(" where t_role_rights.roleId=");		sql.append(roleId);		sql.append(" and bt_rights.action='");		sql.append(StringEscapeUtils.escapeSql(url));		sql.append("'");		ds = Database.executeQuery(conn, sql.toString(), new Parameter[] {});		map = DataSetHelper.dataSetToMap(ds);		boolean reslut = false;		if (map != null) {			count = Convert.strToInt(map.get("counts"), -1);			if (count > 0) {				reslut = true;			}		}		return reslut;	}	}