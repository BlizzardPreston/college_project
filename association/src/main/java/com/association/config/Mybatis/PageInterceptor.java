package com.association.config.Mybatis;

import com.association.common.Page;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author: YangShouKun
 * @Date: 2019-08-11-0011 16:12
 * @Description: mybatis的拦截器, 自定义分页, 将默认的rowBound分页模式(逻辑分页)改为物理分页
 * 拦截所有查询请求，有分页就进行分页，没分页就默认进行
 **/
@Component
@Intercepts({
        @Signature(method = "query", type = Executor.class,
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PageInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor)invocation.getTarget();
        MappedStatement ms = (MappedStatement)invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        RowBounds rowBounds = (RowBounds)invocation.getArgs()[2];
        ResultHandler<?> resultHandler = (ResultHandler)invocation.getArgs()[3];
        if (rowBounds == null) {
            rowBounds = new RowBounds();
        }
        BoundSql boundSql = ms.getBoundSql(parameter);
        CacheKey key = executor.createCacheKey(ms, parameter, rowBounds, boundSql);

        if (rowBounds instanceof Page) {
            String totalSql = this.buildTotalSql(boundSql.getSql());
            int total = this.query(executor, ms, parameter, boundSql, totalSql);
            String pageSql = this.buildPageSql(boundSql.getSql(), rowBounds);
            this.setFieldValue(boundSql, "sql", pageSql);
            // 设置page的值
            Page page = (Page) rowBounds;
            page.setTotalCount(total);
            int pageCount = total / page.getPageSize();
            if (total % page.getPageSize() != 0) {
                pageCount++;
            }
            page.setPageCount(pageCount);
        }

        rowBounds = RowBounds.DEFAULT;
        return executor.query(ms, parameter, rowBounds, resultHandler, key, boundSql);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private Object getFieldValue(Object object, String fieldName) {
        Field field = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                if (field != null){
                    field.setAccessible(true);
                    break;
                }
            } catch (NoSuchFieldException e) {
                // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private void setFieldValue(Object object, String fieldName, Object value) {
        Field field = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                if (field != null){
                    field.setAccessible(true);
                    try {
                        field.set(object,value);
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage());
                    }
                    break;
                }
            } catch (NoSuchFieldException e) {
                //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
    }

    private int query(Executor executor, MappedStatement statement, Object parameter, BoundSql boundSql, String sql) {
        if (executor instanceof CachingExecutor) {
            executor = (Executor)getFieldValue(executor, "delegate");
        }
        if (executor instanceof SimpleExecutor) {
            try {
                BoundSql newBoundSql = new BoundSql(statement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
                setFieldValue(newBoundSql, "metaParameters", getFieldValue(boundSql, "metaParameters"));
                MappedStatement newStatement = this.copyFromMappedStatement(statement, newBoundSql);
                List<Integer> result = ((SimpleExecutor)executor).doQuery(newStatement, parameter, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, newBoundSql);
                if (result.size() == 1) {
                    return result.get(0);
                }
            } catch (SQLException var9) {
                logger.error("query count total rows failure, origin sql:{}, generated sql:{}", new Object[]{boundSql.getSql(), sql, var9});
            }
        }
        return 0;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, final BoundSql newBoundSql) {
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "-Count", new SqlSource() {
            @Override
            public BoundSql getBoundSql(Object parameterObject) {
                return newBoundSql;
            }
        }, ms.getSqlCommandType());
        statementBuilder.resource(ms.getResource());
        statementBuilder.fetchSize(ms.getFetchSize());
        statementBuilder.statementType(ms.getStatementType());
        statementBuilder.keyGenerator(ms.getKeyGenerator());
        statementBuilder.keyProperty(this.join(ms.getKeyProperties()));
        statementBuilder.keyColumn(this.join(ms.getKeyColumns()));
        statementBuilder.databaseId(ms.getDatabaseId());
        statementBuilder.lang(ms.getLang());
        statementBuilder.resultOrdered(ms.isResultOrdered());
        statementBuilder.resulSets(this.join(ms.getResulSets()));
        statementBuilder.timeout(ms.getTimeout());
        statementBuilder.parameterMap(ms.getParameterMap());
        statementBuilder.resultSetType(ms.getResultSetType());
        statementBuilder.flushCacheRequired(ms.isFlushCacheRequired());
        statementBuilder.useCache(ms.isUseCache());
        statementBuilder.cache(ms.getCache());
        ResultMap resultMap = (new ResultMap.Builder(ms.getConfiguration(), "Count", Integer.class, new ArrayList(), (Boolean)null)).build();
        statementBuilder.resultMaps(Lists.newArrayList(new ResultMap[]{resultMap}));
        return statementBuilder.build();
    }

    private String join(String[] items) {
        return items != null && items.length != 0 ? StringUtils.join(items) : null;
    }

    private String buildPageSql(String oldSql, RowBounds rowBounds) {
        String sql = oldSql + " limit " + rowBounds.getOffset() + "," + rowBounds.getLimit();
        return sql;
    }

    private String buildTotalSql(String oldSql) {
        String sql = "select count(*) total from (" + oldSql + ") t_t_table";
        return sql;
    }
}

