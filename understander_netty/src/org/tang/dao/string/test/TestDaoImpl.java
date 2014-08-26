package org.tang.dao.string.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tang.dto.badidea.BadIdeaDTO;
import org.tang.dto.string.test.MsgDTO;
import org.tang.utils.Pagination;

@Repository
public class TestDaoImpl implements TestDao {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public int saveMsg(MsgDTO msg) throws Exception {
		String id = UUID.randomUUID().toString();
		String sql = "insert into t_chat_msg (id,fromuserid,touserid,content) values (?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, id,msg.getUserFrom(),msg.getUserTo(),msg.getContent());
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new Exception();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MsgDTO> findMsg(String content) throws Exception {
		String sql = "select * from t_chat_msg t where content  =  ? ";
		List<MsgDTO> msgDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MsgDTO.class), content);
		return msgDTOList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination findMsgPage(MsgDTO msg) throws Exception {
		String condition = "";
		List<String> params = new ArrayList<String>();
		if(msg!=null){
			if(StringUtils.isNotBlank(msg.getContent())){
				condition += " and content  like ? ";
				params.add(msg.getContent());
			}
		}
		String sql = "select * from t_chat_msg t where 1=1  " + condition;
		return new Pagination(sql,params.toArray(),msg.getCurrentPage(), msg.getNumPerPage(), jdbcTemplate);
		
	}

}
