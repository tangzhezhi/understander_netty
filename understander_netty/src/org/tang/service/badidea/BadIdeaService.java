package org.tang.service.badidea;

import org.tang.dto.badidea.BadIdeaDTO;
import org.tang.utils.Pagination;


public interface BadIdeaService {
	
	public Pagination<?> queryBadIdea(BadIdeaDTO dto) throws Exception;

}
