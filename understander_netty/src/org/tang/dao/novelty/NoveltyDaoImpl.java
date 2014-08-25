package org.tang.dao.novelty;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tang.dto.badidea.BadIdeaDTO;
import org.tang.dto.novelty.NoveltyDTO;
import org.tang.utils.DateTool;
import org.tang.utils.Pagination;

@Repository
public class NoveltyDaoImpl implements NoveltyDao {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public Pagination<?> queryNovelty(NoveltyDTO dto) throws Exception {
		String condition = "";
		if(dto!=null){
			if(StringUtils.isNotBlank(dto.getTag())){
				condition += " and tag  = '"+dto.getTag()+"' ";
			}
			if(StringUtils.isNotBlank(dto.getUpdateTime())){
				condition += " and updatetime  = '"+dto.getUpdateTime()+"' ";
			}
			if(!StringUtils.isNotBlank(dto.getUpdateTime())){
				condition += " and updatetime  = '"+DateTool.getDateStringYMD(new Date())+"' ";
			}
		}
		String sql = "select title,content,updatetime,tag from novelty t where 1=1  " + condition;
		return new Pagination(sql,dto.getCurrentPage(), dto.getNumPerPage(), jdbcTemplate);
	}

}
