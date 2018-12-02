package com.czz.hwy.base;

import java.util.Date;

/**
 * 
* <p>descriptions：分页请求
 *
 * <p>Notes:
 * 
 * <p>Case:
 *
 * @author jiqing  
 * @since  2014年2月14日
 */
public class PageReqParamDTO<T> {
    private static final long serialVersionUID = -2299846229089130634L;
    /**
	 * 页码
	 */
	private Integer page=1;
	/**
	 * 每页数据量
	 */
	private Integer rows=10;
	/**
	 * 
	 * 分页起始行
	 */
	private Integer row;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		result = prime * result
				+ ((rows == null) ? 0 : rows.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageReqParamDTO other = (PageReqParamDTO) obj;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		return true;
	}
	public Integer getRow() {
		if(this.page!=null&&this.page.intValue()!=0){
			
			this.row=(this.page-1)*this.rows;
		}else{
			
			this.row = 0;
		}
		return this.row;
	}
}
