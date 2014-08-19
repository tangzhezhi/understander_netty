package org.tang.dto;

import java.util.List;

import org.tang.utils.Pagination;

public class PageDTO<T> {
	// 一页显示的记录数
	private int numPerPage = 10;
	// 记录总数
	private int totalRows;
	// 总页数
	private int totalPages;
	// 当前页码
	private int currentPage;
	
	public PageDTO(){}
	
	@SuppressWarnings("unchecked")
	public PageDTO(Pagination<?> p){
//		PageDTO pdto = new PageDTO();
		this.setCurrentPage(p.getCurrentPage());
		this.setTotalPages(p.getTotalPages());
		this.setTotalRows(p.getTotalRows());
		this.setData((List<T>) p.getResultList());
	}
	
	
	private List<T> data;

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> list) {
		this.data = (List<T>) list;
	}
	
}
