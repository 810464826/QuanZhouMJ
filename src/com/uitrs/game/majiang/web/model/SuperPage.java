package com.uitrs.game.majiang.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义一个超类page 提取公共的属性
 * 
 * @author 小石潭记 2017年2月22日 下午2:25:59
 */
public class SuperPage<T> {
	private List<T> list = new ArrayList<T>();
	private int currentPage;
	private int totalPages;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "SuperPage [list=" + list + ", currentPage=" + currentPage + ", totalPages=" + totalPages + "]";
	}

	public SuperPage(List<T> list, int currentPage, int totalPages) {
		super();
		this.list = list;
		this.currentPage = currentPage;
		this.totalPages = totalPages;
	}

	public SuperPage() {
		super();
	}
}
