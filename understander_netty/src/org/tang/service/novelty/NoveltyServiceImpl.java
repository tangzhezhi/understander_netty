package org.tang.service.novelty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tang.dao.novelty.NoveltyDao;
import org.tang.dto.novelty.NoveltyDTO;
import org.tang.utils.Pagination;

@Qualifier("noveltyService")
@Service
public class NoveltyServiceImpl implements NoveltyService {
	
	@Autowired
	private NoveltyDao noveltyDao;

	@Override
	public Pagination<?> queryNovelty(NoveltyDTO dto) throws Exception {
		return noveltyDao.queryNovelty(dto);
	}

}
