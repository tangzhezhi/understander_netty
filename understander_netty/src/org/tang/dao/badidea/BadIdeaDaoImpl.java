package org.tang.dao.badidea;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tang.dto.badidea.BadIdeaDTO;
import org.tang.dto.novelty.NoveltyDTO;
import org.tang.utils.DateTool;
import org.tang.utils.Pagination;

@Repository
public class BadIdeaDaoImpl implements BadIdeaDao {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public Pagination<?> queryBadIdea(BadIdeaDTO dto) throws Exception {
		String condition = "";
		List<String> params = new ArrayList<String>();
		if(dto!=null){
			if(StringUtils.isNotBlank(dto.getTag())){
				condition += " and tag  = ? ";
				params.add(dto.getTag());
			}
			if(StringUtils.isNotBlank(dto.getUpdateTime())){
				condition += " and updatetime  = ? ";
				params.add(DateTool.getDateStringYMD(new Date()));
			}
			if(!StringUtils.isNotBlank(dto.getUpdateTime())){
				condition += " and updatetime  = ? ";
				params.add(dto.getUpdateTime());
			}
		}
		String sql = "select title,content,updatetime,tag from bad_idea t where 1=1  " + condition;
		return new Pagination(sql,params.toArray(),dto.getCurrentPage(), dto.getNumPerPage(), jdbcTemplate);
	}

}
