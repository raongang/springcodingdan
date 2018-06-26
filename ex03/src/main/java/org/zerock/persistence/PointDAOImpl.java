package org.zerock.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOImpl implements PointDAO{

	private static String namespace="org.zerock.mapper.PointMapper";
	
	@Inject
	private SqlSession session;
	
	private static final Logger logger = LoggerFactory.getLogger(PointDAOImpl.class);
	
	@Override
	public void updatePoint(String uid, int point) throws Exception {
		// TODO Auto-generated method stub
		
		logger.info("uid : " + uid + " point : " + point);
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("uid", uid);
		paraMap.put("point", point);
		
		session.update(namespace+".updatePoint",paraMap);
	}
}
