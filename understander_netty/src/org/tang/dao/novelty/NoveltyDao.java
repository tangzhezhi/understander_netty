package org.tang.dao.novelty;

import org.tang.dto.novelty.NoveltyDTO;
import org.tang.utils.Pagination;

public interface NoveltyDao {
	public Pagination<?> queryNovelty(NoveltyDTO dto)throws Exception;
	
}
