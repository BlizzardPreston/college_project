package com.association.common.cache;


import com.association.common.Util.JacksonUtils;
import com.association.common.cache.api.CacheManager;
import com.association.config.redis.RedisProperties;
import com.fasterxml.jackson.databind.JavaType;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: zhangfang
 * @Date: 2019/11/5 11:33
 */
public class RedisManager implements CacheManager {
    private StringRedisTemplate stringRedisTemplate;
    private RedissonClient redisson;
//    public RScript rScript = redisson.getScript();



    public void init(StringRedisTemplate redisTemplate, RedisProperties properties) {

        stringRedisTemplate = redisTemplate;
        if (properties.getInitRedisson()) {
            initRedisson(properties);
        }
    }

    @Override
    public void init(StringRedisTemplate redisTemplate, org.springframework.boot.autoconfigure.data.redis.RedisProperties properties) {

    }

    @Override
    public StringRedisTemplate stringRedisTemplate() {
        return stringRedisTemplate;
    }

    private void initRedisson(RedisProperties prop) {

        Config config = new Config();
        if (prop.getCluster() != null) {
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            clusterServersConfig.setScanInterval(3000);
            if (StringUtils.isNoneEmpty(prop.getPassword())) {
                clusterServersConfig.setPassword(prop.getPassword());
            }
            clusterServersConfig.setPingConnectionInterval(3000);
            clusterServersConfig.setConnectTimeout(3000);
            clusterServersConfig.setIdleConnectionTimeout(3000);
            prop.getCluster().getNodes().stream()
                    .map(it -> "redis://" + it)
                    .forEach(clusterServersConfig::addNodeAddress);
        } else if (prop.getSentinel() != null) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            if (StringUtils.isNoneEmpty(prop.getPassword())) {
                sentinelServersConfig.setPassword(prop.getPassword());
            }
            sentinelServersConfig.setMasterName(prop.getSentinel().getMaster());
            sentinelServersConfig.setPingConnectionInterval(3000);
            prop.getSentinel().getNodes().stream()
                    .map(it -> "redis://" + it).forEach(sentinelServersConfig::addSentinelAddress);

        } else {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress("redis://" + prop.getHost() + ":" + prop.getPort());
            singleServerConfig.setDatabase(prop.getDatabase());
            singleServerConfig.setPingConnectionInterval(2000);
            if (StringUtils.isNoneEmpty(prop.getPassword())) {
                singleServerConfig.setPassword(prop.getPassword());
            }
        }
        redisson = Redisson.create(config);
    }

    @Override
    public RedissonClient redissonClient() {
        return redisson;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void del(String key) {
        stringRedisTemplate.delete(key);
    }
    @Override
    public void del(Set<String> keys) {
        stringRedisTemplate.delete(keys);
    }
    @Override
    public void unlink(String key) {
        stringRedisTemplate.unlink(key);
    }

    @Override
    public Set<String> keys(String key) {
        return stringRedisTemplate.keys(key);
    }

    @Override
    public void del(List<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    @Override
    public long incr(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    @Override
    public long incrBy(String key, long delt) {
        return stringRedisTemplate.opsForValue().increment(key, delt);
    }

    @Override
    public long decr(String key) {
        return stringRedisTemplate.opsForValue().decrement(key);
    }

    @Override
    public long decrBy(String key, long delt) {
        return stringRedisTemplate.opsForValue().decrement(key, delt);
    }

    @Override
    public Boolean expire(String key, int seconds) {
        return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Boolean expireAt(String key, long seconds) {
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(seconds);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return stringRedisTemplate.expireAt(key, date);
    }

    @Override
    public Boolean expireAt(String key, Date date) {
        return stringRedisTemplate.expireAt(key, date);
    }

    @Override
    public Long ttl(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public long hincrBy(String key, String field, long value) {
        return stringRedisTemplate.opsForHash().increment(key, field, value);
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(byte[] key, byte[] value) {
        stringRedisTemplate.opsForValue().set(new String(key), new String(value));
    }

    @Override
    public void set(byte[] key, byte[] value, int expireTime) {
        stringRedisTemplate.opsForValue().set(new String(key), new String(value), expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, String value, int expireTime) {
        stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void hmset(String key, Map<String, String> hash) {
        stringRedisTemplate.opsForHash().putAll(key, hash);
    }

    @Override
    public void hmset(String key, Map<String, String> hash, int time) {
        stringRedisTemplate.opsForHash().putAll(key, hash);
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    @Override
    public Boolean setNx(String key, String value, int time, TimeUnit timeUnit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    @Override
    public void set(String key, Object value, int expireTime) {
        stringRedisTemplate.opsForValue().set(key, getDataString(value), expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, getDataString(value));
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }


    @Override
    public void setbitBatch(String key, Set<Long> offsets, boolean value) {
        offsets.forEach(it -> {
            stringRedisTemplate.opsForValue().setBit(key, it, value);
        });

    }

    @Override
    public byte[] get(byte[] key) {
        Object o = stringRedisTemplate.opsForValue().get(new String(key));
        if (o != null) {
            return JacksonUtils.toJson(o).getBytes();
        }
        return null;
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Class<T> tClass) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (tClass == String.class) {
            return (T) value;
        }
        if (value == null) {
            return null;
        }
        return JacksonUtils.toBean(value, tClass);
    }

    @Override
    public Object get(String key, JavaType javaType) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return JacksonUtils.toBean(value, javaType);
    }

    @Override
    public <T> List<T> gets(String key, Class<T> tClass) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null) {
            return new ArrayList<>();
        }
        return (List<T>) JacksonUtils.toBeanCollection(value, ArrayList.class, tClass);
    }

    @Override
    public Boolean getbit(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    public Set<Long> getbitOffset(String key) {
        Set<Long> sets = new HashSet<>();
        String val = stringRedisTemplate.opsForValue().get(key);
        byte[] bytes = StringUtils.isNotEmpty(val) ? val.getBytes() : null;
        if (bytes != null) {
            BitSet bitSet = fromByteArrayReverse(bytes);
            if (bitSet != null && bitSet.size() > 0) {
                bitSet.stream().forEach(o -> {
                    sets.add(Long.parseLong(String.valueOf(o)));
                });
            }
        }
        return sets;
    }

    private BitSet fromByteArrayReverse(final byte[] bytes) {
        final BitSet bits = new BitSet();
        for (int i = 0; i < bytes.length * 8; i++) {
            if ((bytes[i / 8] & (1 << (7 - (i % 8)))) != 0) {
                bits.set(i);
            }
        }
        return bits;
    }

    @Override
    public Set<Long> bitopDiff(String... srcKeys) {
        String destKey = buildTempDestKey("sdiff", srcKeys);
        byte[][] bytes = new byte[srcKeys.length][];
        for (int i = 0; i < srcKeys.length; i++) {
            bytes[i] = srcKeys[i].getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Set<Long>>) con -> {
                    con.bitOp(RedisStringCommands.BitOperation.AND, destKey.getBytes(), bytes);
                    con.bitOp(RedisStringCommands.BitOperation.XOR, destKey.getBytes(), bytes[0], destKey.getBytes());
                    return getbitOffset(destKey);
                }
        );
    }

    private String buildTempDestKey(String bitop, String... srcKeys) {
        String tmpKey = "bitmap:tmp:bitop:" + bitop;
        for (int i = 0; i < srcKeys.length; i++) {
            tmpKey += ":" + srcKeys[i];
        }
        return tmpKey;
    }

    @Override
    public Long bitopDiffStore(String destKey, String... srcKeys) {
        byte[][] bytes = new byte[srcKeys.length][];
        for (int i = 0; i < srcKeys.length; i++) {
            bytes[i] = srcKeys[i].getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> {
                    con.bitOp(RedisStringCommands.BitOperation.AND, destKey.getBytes(), bytes);
                    return con.bitOp(RedisStringCommands.BitOperation.XOR, destKey.getBytes(), bytes[0], destKey.getBytes());
                }
        );
    }

    @Override
    public Set<Long> bitopIntersect(String... srcKeys) {
        String destKey = buildTempDestKey("sinter", srcKeys);
        byte[][] bytes = new byte[srcKeys.length][];
        for (int i = 0; i < srcKeys.length; i++) {
            bytes[i] = srcKeys[i].getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Set<Long>>) con -> {
                    con.bitOp(RedisStringCommands.BitOperation.AND, destKey.getBytes(), bytes);
                    return getbitOffset(destKey);
                }
        );
    }

    @Override
    public Long bitopIntersectStore(String destKey, String... srcKeys) {
        byte[][] bytes = new byte[srcKeys.length][];
        for (int i = 0; i < srcKeys.length; i++) {
            bytes[i] = srcKeys[i].getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> {
                    return con.bitOp(RedisStringCommands.BitOperation.AND, destKey.getBytes(), bytes);
                }
        );
    }

    @Override
    public Set<Long> bitopUnion(String... srcKeys) {
        String destKey = buildTempDestKey("sunion", srcKeys);
        byte[][] bytes = new byte[srcKeys.length][];
        for (int i = 0; i < srcKeys.length; i++) {
            bytes[i] = srcKeys[i].getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Set<Long>>) con -> {
                    con.bitOp(RedisStringCommands.BitOperation.OR, destKey.getBytes(), bytes);
                    return getbitOffset(destKey);
                }
        );
    }

    @Override
    public Long bitopUnionStore(String destKey, String... srcKeys) {
        byte[][] bytes = new byte[srcKeys.length][];
        for (int i = 0; i < srcKeys.length; i++) {
            bytes[i] = srcKeys[i].getBytes();
        }
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> {
                    return con.bitOp(RedisStringCommands.BitOperation.OR, destKey.getBytes(), bytes);
                }
        );
    }

    @Override
    public Long bitcount(String key) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> {
            return con.bitCount(key.getBytes());
        });
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> {
            return con.bitCount(key.getBytes(), start, end);
        });
    }

    @Override
    public Long bitpos(String key, boolean value) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> {
            return con.bitPos(key.getBytes(), value);
        });
    }

    @Override
    public Long bitpos(String key, boolean value, long start) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> {
            return con.bitPos(key.getBytes(), value, Range.of(Range.Bound.inclusive(start), Range.Bound.unbounded()));
        });
    }

    @Override
    public Long bitpos(String key, boolean value, long start, long end) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> {
            return con.bitPos(key.getBytes(), value, Range.of(Range.Bound.inclusive(start), Range.Bound.exclusive(end)));
        });
    }

    @Override
    public void hset(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public void hset(String key, String field, String value, int time) {
        stringRedisTemplate.opsForHash().put(key, field, value);
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    @Override
    public void hset(String key, String field, Object value) {
        stringRedisTemplate.opsForHash().put(key, field, getDataString(value));
    }

    @Override
    public <T> T hget(String key, String field, Class<T> tClass) {
        String value = (String) stringRedisTemplate.opsForHash().get(key, field);
        if (tClass == String.class) {
            return (T) value;
        }
        if (value == null) {
            return null;
        }
        return JacksonUtils.toBean(value, tClass);
    }

    @Override
    public <T> List<T> hgets(String key, String field, Class<T> tClass) {
        String value = (String) stringRedisTemplate.opsForHash().get(key, field);
        if (value == null) {
            return new ArrayList<>();
        }
        return (List<T>) JacksonUtils.toBeanCollection(value, ArrayList.class, tClass);
    }

    @Override
    public void hdel(String key, String field) {
        stringRedisTemplate.opsForHash().delete(key, field);
    }

    @Override
    public <T> Map<String, T> hgetAll(String key, Class<T> clazz) {
        Map<Object, Object> result = stringRedisTemplate.opsForHash().entries(key);
        if (result.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, T> newMap = new HashMap<>(result.size());
        result.forEach((k, v) -> newMap.put(k.toString(), JacksonUtils.toBean(v.toString(), clazz)));
        return newMap;
    }

    @Override
    public Boolean hexists(String key, String field) {

        return stringRedisTemplate.opsForHash().hasKey(key, field);
    }

    @Override
    public Boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public boolean zAdd(String key, String value) {
        return zAdd(key, 0, value);
    }

    @Override
    public boolean zAdd(String key, double score, String value) {
        return stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    @Override
    public boolean zAdd(String key, double score, Object value) {
        return stringRedisTemplate.opsForZSet().add(key, getDataString(value), score);
    }

    @Override
    public Long zCard(String key) {
        return stringRedisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public void zRem(String key, String value) {
        stringRedisTemplate.opsForZSet().remove(key, value);
    }

    @Override
    public Long zRemByScore(String key, double minScore, double maxScore) {
        return stringRedisTemplate.opsForZSet().removeRangeByScore(key, minScore, maxScore);
    }

    @Override
    public <T> List<T> zrange(String key, long start, long end, Class<T> clazz) {
        Set<String> value = stringRedisTemplate.opsForZSet().range(key, start, end);
        return toList(value, clazz);
    }

    @Override
    public <T> List<T> zrevrange(String key, long start, long end, Class<T> clazz) {
        Set<String> value = stringRedisTemplate.opsForZSet().reverseRange(key, start, end);
        return toList(value, clazz);
    }

    @Override
    public <T> List<T> zrangeByScore(String key, double minScore, double maxScore, Class<T> clazz) {
        Set<String> value = stringRedisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
        return toList(value, clazz);
    }

    @Override
    public <T> List<T> zrevByScore(String key, double minScore, double maxScore, Class<T> clazz) {
        Set<String> value = stringRedisTemplate.opsForZSet().reverseRangeByScore(key, minScore, maxScore);
        return toList(value, clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> toList(Collection<String> value, Class<T> clazz) {
        if (value != null) {
            if (clazz == String.class) {
                return (List<T>) new ArrayList<>(value);
            }
            List<T> newValue = new ArrayList<>();
            for (String temp : value) {
                newValue.add(JacksonUtils.toBean(temp, clazz));
            }
            return newValue;
        }
        return new ArrayList<>();
    }


    @Override
    public void lpush(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public void rpush(String key, Object value) {
        stringRedisTemplate.opsForList().rightPush(key, getDataString(value));
    }

    @Override
    public void rpush(String key, String value) {
        stringRedisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public <T> List<T> lrange(String key, int start, int end, Class<T> clazz) {
        List<String> value = stringRedisTemplate.opsForList().range(key, start, end);

        return toList(value, clazz);
    }

    @Override
    public void ltrim(String key, int start, int end) {
        stringRedisTemplate.opsForList().trim(key, start, end);
    }

    @Override
    public long llen(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    @Override
    public Long sAdd(String key, String... members) {
        return stringRedisTemplate.opsForSet().add(key, members);
    }

    @Override
    public boolean sIsMember(String key, String member) {
        return stringRedisTemplate.opsForSet().isMember(key, member);
    }

    @Override
    public Long sCard(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    @Override
    public Set<String> sMembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    @Override
    public Long sRem(String key, String... members) {
        return stringRedisTemplate.opsForSet().remove(key, members);
    }

    @Override
    public Set<String> sIntersect(String... keys) {
        String key = keys[0];
        Set<String> other = new HashSet<>();
        for (int i = 1; i < keys.length; i++) {
            other.add(keys[i]);
        }
        return stringRedisTemplate.opsForSet().intersect(keys[0], other);
    }

    @Override
    public Long sIntersectStore(String dstkey, String... keys) {
        String key = keys[0];
        Set<String> other = new HashSet<>();
        for (int i = 1; i < keys.length; i++) {
            other.add(keys[i]);
        }
        return stringRedisTemplate.opsForSet().intersectAndStore(key, other, dstkey);
    }

    @Override
    public Set<String> sUnion(String... keys) {
        String key = keys[0];
        Set<String> other = new HashSet<>();
        for (int i = 1; i < keys.length; i++) {
            other.add(keys[i]);
        }
        return stringRedisTemplate.opsForSet().union(key, other);
    }

    @Override
    public Long sUnionStore(String dstkey, String... keys) {
        String key = keys[0];
        Set<String> other = new HashSet<>();
        for (int i = 1; i < keys.length; i++) {
            other.add(keys[i]);
        }
        return stringRedisTemplate.opsForSet().unionAndStore(key, other, dstkey);
    }

    @Override
    public Set<String> sDiff(String... keys) {
        String key = keys[0];
        Set<String> other = new HashSet<>();
        for (int i = 1; i < keys.length; i++) {
            other.add(keys[i]);
        }
        return stringRedisTemplate.opsForSet().difference(key, other);
    }

    @Override
    public Long sDiffStore(String dstkey, String... keys) {
        String key = keys[0];
        Set<String> other = new HashSet<>();
        for (int i = 1; i < keys.length; i++) {
            other.add(keys[i]);
        }
        return stringRedisTemplate.opsForSet().differenceAndStore(key, other, dstkey);
    }


    @Override
    public <T> T rpop(String key, Class<T> clazz) {
        String value = stringRedisTemplate.opsForList().rightPop(key);
        if (clazz == String.class) {
            return (T) value;
        }
        if (value == null) {
            return null;
        }
        return JacksonUtils.toBean(value, clazz);
    }

    @Override
    public <T> T lpop(String key, Class<T> clazz) {
        String value = stringRedisTemplate.opsForList().leftPop(key);
        if (clazz == String.class) {
            return (T) value;
        }
        if (value == null) {
            return null;
        }
        return JacksonUtils.toBean(value, clazz);
    }

    @Override
    public void lpush(String key, Object value) {
        stringRedisTemplate.opsForList().leftPush(key, getDataString(value));
    }

    @Override
    public String spop(String key) {
        return stringRedisTemplate.opsForSet().pop(key);
    }

    @Override
    public Set<String> spop(String key, long count) {
        List<String> pop = stringRedisTemplate.opsForSet().pop(key, count);
        if (pop == null || pop.isEmpty()) {
            return new HashSet<>();
        }
        return pop.stream().collect(Collectors.toSet());
    }

    @Override
    public long eval(String script, List<String> keys, List<String> args) {
        return stringRedisTemplate.execute(RedisScript.of(script), keys, args);
    }


    @Override
    public long evalsha(String sha1, List<String> keys, List<String> args) {
        return stringRedisTemplate.execute(RedisScript.of(sha1), keys, args);
    }

    @Override
    public String rename(String var1, String var2) {
        stringRedisTemplate.rename(var1, var2);
        return null;
    }

    private String getDataString(Object message) {
        String messageStr = null;
        if (message == null) {
            return null;
        }
        if (message instanceof String) {
            messageStr = (String) message;
        } else {
            messageStr = (String) JacksonUtils.toJsonNotNull(message);
        }
        return messageStr;
    }

}

