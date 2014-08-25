package org.tang.service.novelty;

import org.tang.dto.novelty.NoveltyDTO;
import org.tang.utils.Pagination;


public interface NoveltyService {
	
	public Pagination<?> queryNovelty(NoveltyDTO dto) throws Exception;

}
