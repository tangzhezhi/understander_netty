package org.tang.dao.string.test;

import java.util.List;

import org.tang.dto.string.test.MsgDTO;

public interface TestDao {
	
	public int saveMsg(MsgDTO msg) throws Exception;
	
	public List<MsgDTO> findMsg(String content) throws Exception;
	
}
