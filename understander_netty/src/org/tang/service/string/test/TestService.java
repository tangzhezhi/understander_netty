package org.tang.service.string.test;

import java.util.List;

import org.tang.dto.string.test.MsgDTO;
import org.tang.utils.Pagination;

public interface TestService {
	
	public int saveMsg(MsgDTO msg) throws Exception;
	
	public List<MsgDTO> findMsg(String content) throws Exception;
	
	@SuppressWarnings("unchecked")
	public Pagination findMsgPage(MsgDTO msg) throws Exception;

}
