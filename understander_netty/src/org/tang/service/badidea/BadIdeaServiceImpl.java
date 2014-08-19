package org.tang.service.badidea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tang.dao.badidea.BadIdeaDao;
import org.tang.dao.string.test.TestDao;
import org.tang.dto.badidea.BadIdeaDTO;
import org.tang.dto.string.test.MsgDTO;
import org.tang.utils.Pagination;

@Qualifier("badIdeaService")
@Service
public class BadIdeaServiceImpl implements BadIdeaService {
	
	@Autowired
	private BadIdeaDao badIdeaDao;

	@Override
	public Pagination<?> queryBadIdea(BadIdeaDTO dto) throws Exception {
		return badIdeaDao.queryBadIdea(dto);
	}

}
