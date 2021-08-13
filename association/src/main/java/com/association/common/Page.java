package com.association.common;


import org.apache.ibatis.session.RowBounds;

/**
 * @Author: YangShouKun
 * @Date: 2019-08-11-0011 14:02
 * @Description: 分页参数
 **/
public class Page extends RowBounds {
    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 当前第几页
     */
    private int pageNo;
    /**
     * 每页多少条
     */
    private int pageSize;
    /**
     * 总共多少页
     */
    private int pageCount;
    /**
     * 总共多少条
     */
    private long totalCount;

    public Page() {
        this.pageNo = DEFAULT_PAGE_NO;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Page(int pageNo, int pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = 1;
        }
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    @Override
    public int getLimit() {
        return pageSize;
    }
}
