package com.association.common.Util;


import com.association.common.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 *
 * @author MR•HE
 * @date 2021/1/27
 **/
public class PageUtil {

    /**
     * 集合分页展示
     *
     * @param page   分页参数
     * @param result 包含全部数据的集合
     * @param <T>    泛型
     * @return 泛型集合
     */
    public static <T> List<T> getPageList(Page page, List<T> result) {
        List<T> collect = new ArrayList<>();
        if(page==null){
            return result;
        }
        // 设置页码的总数
        page.setTotalCount(result.size());
        if (result.size() <= page.getPageSize()) {
            if (page.getPageNo() == 1) {
                return result;
            }
        } else {
            int i1 = page.getPageSize() * (page.getPageNo() - 1);
            int i2 = i1 + page.getPageSize();
            for (int i = i1; i < i2; i++) {
                if (i <= result.size() - 1) {
                    collect.add(result.get(i));
                } else {
                    break;
                }
            }
        }
        return collect;
    }

}
