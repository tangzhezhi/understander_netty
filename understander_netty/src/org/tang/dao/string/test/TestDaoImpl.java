package org.tang.dao.string.test;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tang.dto.string.test.MsgDTO;

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

}
