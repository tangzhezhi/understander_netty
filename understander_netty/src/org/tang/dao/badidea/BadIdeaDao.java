package org.tang.dao.badidea;

import org.tang.dto.badidea.BadIdeaDTO;
import org.tang.utils.Pagination;

public interface BadIdeaDao {
	
	public Pagination<?> queryBadIdea(BadIdeaDTO dto)throws Exception;
	
}
