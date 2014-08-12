package org.tang.service.string.test;

import org.tang.dto.string.test.MsgDTO;

public interface TestService {
	
	public int saveMsg(MsgDTO msg) throws Exception;

}
