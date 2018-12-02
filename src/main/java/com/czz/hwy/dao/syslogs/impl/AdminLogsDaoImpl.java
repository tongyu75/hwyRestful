package com.czz.hwy.dao.syslogs.impl;

import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.manager.AdminLogs;
import com.czz.hwy.dao.syslogs.AdminLogsDao;
@Repository
public class AdminLogsDaoImpl extends BaseDaoImpl<AdminLogs> implements AdminLogsDao {
	/*@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int insertInfo(AdminLogs bean) {
		return sqlSessionTemplate.insert("insertAdminLogs",bean);

	}

	public int deleteInfo(AdminLogs bean) {
		return sqlSessionTemplate.delete("deleteAdminLogs",bean);

	}

	public int updateInfo(AdminLogs bean) {
		return sqlSessionTemplate.update("updateAdminLogs",bean);

	}

	public AdminLogs getInfoByBean(AdminLogs bean) {
		return sqlSessionTemplate.selectOne("getAdminLogsByBean", bean);

	}

	public AdminLogs getInfoById(int id) {
		return sqlSessionTemplate.selectOne("getAdminLogsById",id);

	}

	public int getInfoCount(int id) {
		return sqlSessionTemplate.selectOne("getAdminLogsCountById",id);

	}

	public List getAllInfo() {
		return sqlSessionTemplate.selectList("getAllAdminLogs");

	}
*/
}
