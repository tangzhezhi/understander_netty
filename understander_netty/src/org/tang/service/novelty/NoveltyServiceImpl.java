package org.tang.service.novelty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tang.dao.novelty.NoveltyDao;
import org.tang.dto.novelty.NoveltyDTO;
import org.tang.utils.Pagination;
import org.tang.utils.ReflectUtil;

@Qualifier("noveltyService")
@Service
public class NoveltyServiceImpl implements NoveltyService {
	
	@Autowired
	private NoveltyDao noveltyDao;

	@Override
	public Pagination<?> queryNovelty(NoveltyDTO dto) throws Exception {
		
		Pagination page = noveltyDao.queryNovelty(dto);
		
		return page;
	}

}
