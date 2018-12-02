package com.czz.hwy.dao.mission;

import java.util.List;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.mission.CheckGram;
import com.czz.hwy.bean.mission.KindInfo;

public interface CheckGramDao extends BaseDao<CheckGram>{
	public List<KindInfo> getKindCountInfo(CheckGram checkGram);
}