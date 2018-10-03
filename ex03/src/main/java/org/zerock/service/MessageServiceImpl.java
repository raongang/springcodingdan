package org.zerock.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.MessageVO;
import org.zerock.persistence.MessageDAO;
import org.zerock.persistence.PointDAO;

@Service
public class MessageServiceImpl implements MessageService{

	@Inject
	private MessageDAO messageDAO;
	
	@Inject
	private PointDAO pointDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	
	//@Transactional
	@Override
	public void addMessage(MessageVO vo) throws Exception {
		// TODO Auto-generated method stub
		logger.info("addMessage Enter");
		
		logger.info(vo.getTargetid());
		logger.info(vo.getSender());
		logger.info(vo.getMessage());
		
		
		messageDAO.create(vo);
		pointDAO.updatePoint(vo.getSender(), 10);
	}

	@Override
	public MessageVO readMessage(String uid, Integer mno) throws Exception {
		// TODO Auto-generated method stub
		messageDAO.updateState(mno);
		pointDAO.updatePoint(uid, 5);
		
		return messageDAO.readMessage(mno);
	}

}
