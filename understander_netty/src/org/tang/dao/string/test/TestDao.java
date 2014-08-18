package org.tang.dao.string.test;

import java.util.List;

import org.tang.dto.string.test.MsgDTO;
import org.tang.utils.Pagination;

public interface TestDao {
	
	public int saveMsg(MsgDTO msg) throws Exception;
	
	public List<MsgDTO> findMsg(String content) throws Exception;

	public Pagination findMsgPage(MsgDTO msg)throws Exception;
	
}
