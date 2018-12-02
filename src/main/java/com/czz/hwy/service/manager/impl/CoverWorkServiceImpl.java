package com.czz.hwy.service.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.CoverWork;
import com.czz.hwy.dao.manager.CoverWorkDao;
import com.czz.hwy.service.manager.CoverWorkService;

/**
 * 代班记录查询功能的service接口实现类,用于pc端
 * @Author 张咏雪
 * @createDate 2016-11-16
 */
@Service
public class CoverWorkServiceImpl implements CoverWorkService {

    @Autowired
    private CoverWorkDao coverWorkDao; // 代班dao层接口

    /**
     * 查询代班信息记录总条数，2016-11-16
     */
	public int getCoverWorkCount(CoverWork coverWork) {
		return coverWorkDao.getInfoCount("getCoverWorkCount", coverWork);
	}

	/**
	 * 查询代班信息记录集合，2016-11-17，分页
	 */
	public List<CoverWork> getCoverWorkList(CoverWork coverWork) {
		return coverWorkDao.getInfoListByBean("getCoverWorkList", coverWork);
	}

}
