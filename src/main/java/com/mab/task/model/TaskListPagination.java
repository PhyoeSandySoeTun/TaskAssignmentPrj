package com.mab.task.model;

import java.util.List;

public class TaskListPagination {
	private int totalPages;
	private int currentPage;
	private int pageSize;

	private List<TaskAssignment> listData;

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

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<TaskAssignment> getListData() {
		return listData;
	}

	public void setListData(List<TaskAssignment> listData) {
		this.listData = listData;
	}

}
