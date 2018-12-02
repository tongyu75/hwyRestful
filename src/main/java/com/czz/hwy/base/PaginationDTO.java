package com.czz.hwy.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* <p>descriptions： 分页公共DTO
 *
 * <p>Notes:
 * 
 * <p>Case:
 *
 * @author jiqing  
 * @since  2014年2月14日
 */
public class PaginationDTO<T> extends BaseDTO {
    private static final long serialVersionUID = -3450154715527619747L;
	/**
	 * 数据集合
	 */
	private List<T> rows = new ArrayList<T>(0);
	
	/**
	 * 数据集合2(wyn添加:分配用户角色时使用)
	 */
	private List<T> otherRows = new ArrayList<T>(0);
	
	/**
	 * 记录总数
	 */
	private Integer results;
	/**
	 * 是否存在错误
	 */
	private Boolean hasError;
	/**
	 * 仅在 hasError : true 时使用
	 */
	private String error;
	/**
	 * 返回错误码
	 */
	private String errorCode;

	
	
	public PaginationDTO() {
		super();
	}

	public PaginationDTO(List<T> rows, Integer results) {
		super();
		this.rows = rows;
		this.results = results;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getResults() {
		return results;
	}

	public void setResults(Integer results) {
		this.results = results;
	}

	public Boolean getHasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<T> getOtherRows() {
		return otherRows;
	}

	public void setOtherRows(List<T> otherRows) {
		this.otherRows = otherRows;
	}

}
