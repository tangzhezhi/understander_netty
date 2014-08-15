package org.tang.service.http.test;

import org.tang.dto.string.test.MsgDTO;

public interface HttpTestService {
	
	public int saveMsg(MsgDTO msg) throws Exception;

}
