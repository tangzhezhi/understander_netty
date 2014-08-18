package org.tang.service.string.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.tang.dao.string.test.TestDao;
import org.tang.dto.string.test.MsgDTO;
import org.tang.utils.Pagination;

@Qualifier("testService")
@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestDao testDao;
	
	@Override
	public int saveMsg(MsgDTO msg) throws Exception {
		return testDao.saveMsg(msg);
	}
	
	@Cacheable(value="msgCache",key="#content + 'findMsg'")  
	@Override
	public List<MsgDTO> findMsg(String content) throws Exception {
		return testDao.findMsg(content);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination findMsgPage(MsgDTO msg) throws Exception {
		return  testDao.findMsgPage(msg);
		
	}

}
