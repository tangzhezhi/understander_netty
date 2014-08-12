package org.tang.dao.string.test;

import org.tang.dto.string.test.MsgDTO;

public interface TestDao {
	
	public int saveMsg(MsgDTO msg) throws Exception;

}
