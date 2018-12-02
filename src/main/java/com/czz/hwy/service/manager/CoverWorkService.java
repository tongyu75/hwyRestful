package com.czz.hwy.service.manager;

import java.util.List;

import com.czz.hwy.bean.manager.CoverWork;


/**
 * 代班记录查询功能的service接口,用于pc端
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-16
 */
public interface CoverWorkService{

	/**
	 * 查询代班信息记录总条数，2016-11-16
	 * @param coverWork
	 * @return
	 */
	int getCoverWorkCount(CoverWork coverWork);

	/**
	 * 查询代班信息记录集合，2016-11-17，分页
	 * @param coverWork
	 * @return
	 */
	List<CoverWork> getCoverWorkList(CoverWork coverWork);


	
}
