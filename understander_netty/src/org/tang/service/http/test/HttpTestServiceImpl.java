package org.tang.service.http.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tang.dao.string.test.TestDao;
import org.tang.dto.string.test.MsgDTO;

@Qualifier("httpTestService")
@Service
public class HttpTestServiceImpl implements HttpTestService {
	
	@Autowired
	private TestDao testDao;
	
	@Override
	public int saveMsg(MsgDTO msg) throws Exception {
		return testDao.saveMsg(msg);
	}

}
