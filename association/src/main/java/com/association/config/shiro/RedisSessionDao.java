package com.association.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    @Qualifier("objectRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    //配置yml写定超时时间
    @Value("${xinjianchen.shiro.redis.expire}")
    private int expire;

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "web-redis-session:";


    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        String key = keyPrefix + session.getId().toString();
        session.setTimeout(expire);
        redisTemplate.opsForValue().set(key, session, expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        String key = keyPrefix + session.getId().toString();
        redisTemplate.delete(key);
    }


    @Override
    public Collection<Session> getActiveSessions() {
//        Set不可重复储存
        Set<Session> sessions = new HashSet<>();
//        读取redis中以keyPrefix开头的redis值
        Set<String> keys = redisTemplate.keys(this.keyPrefix + "*");
        if (keys != null || keys.size() > 0) {
            for (String key : keys) {
                Session s = (Session) redisTemplate.opsForValue().get(key);
                sessions.add(s);
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
/*        生成要应用于指定会话实例的新ID。此方法通常从子类的doCreate 实现中调用，在该实现中,它们将返回的ID分配给会话实例，
        然后在EIS数据存储中使用此ID 创建记录*/
        Serializable sessionId = this.generateSessionId(session);

/*        可用于希望将生成的会话 ID 直接分配给会话实例的子类的实用方法。
        AbstractSessionDAO 实现不直接使用此方法，但提供它以便子类在不需要时不需要知道 Session 实现。*/
        this.assignSessionId(session, sessionId);
//保存
        this.saveSession(session);
        return sessionId;
    }

    //    获取Session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            log.error("session id is null");
            return null;
        }
        return (Session) redisTemplate.opsForValue().get(keyPrefix + sessionId);
    }

    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
