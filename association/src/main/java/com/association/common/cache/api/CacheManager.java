package com.association.common.cache.api;

import com.fasterxml.jackson.databind.JavaType;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 直接使用
 *     @Autowired
 *     private CacheManager cacheManager;
 *     调用接口使用方法即可
 * CacheManager
 *
 * @author zhangfang
 * @date 2019-10-29
 */
public interface CacheManager {
    void init(StringRedisTemplate redisTemplate, RedisProperties properties);
    StringRedisTemplate stringRedisTemplate();
    RedissonClient redissonClient();

    void destroy();

    /**
     * del
     * @param key
     */
    void del(String key);
    void del(Set<String> keys);
    void unlink(String key);
    Set<String> keys(String key);

    /**
     * del
     * @param keys
     */
    void del(List<String> keys);

    /**
     * incr
     * @param key
     * @return
     */
    long incr(String key);

    /**
     * incrBy
     * @param key
     * @param delt
     * @return
     */
    long incrBy(String key, long delt);

    /**
     * decr
     * @param key
     * @return
     */
    long decr(String key);

    /**
     * decrBy
     * @param key
     * @param delt
     * @return
     */
    long decrBy(String key, long delt);

    /**
     * Set a timeout on the specified key. After the timeout the key will be automatically deleted
     * by the server. A key with an associated timeout is said to be volatile in Redis terminology.
     * <p>
     * Voltile keys are stored on disk like the other keys, the timeout is persistent too like all
     * the other aspects of the dataset. Saving a dataset containing expires and stopping the server
     * does not stop the flow of time as Redis stores on disk the time when the key will no longer
     * be available as Unix time, and not the remaining seconds.
     * <p>
     * Since Redis 2.1.3 you can update the value of the timeout of a key already having an expire
     * set. It is also possible to undo the expire at all turning the key into a normal key using
     * the {@link #(String) PERSIST} command.
     * <p>
     * Time complexity: O(1)
     *
     * @see <a href="http://code.google.com/p/redis/wiki/ExpireCommand">ExpireCommand</a>
     * @param key
     * @param seconds
     * @return Integer reply, specifically: 1: the timeout was set. 0: the timeout was not set since
     *         the key already has an associated timeout (this may happen only in Redis versions
     *         &lt; 2.1.3, Redis &gt;= 2.1.3 will happily update the timeout), or the key does not
     *         exist.
     */
    Boolean expire(final String key, final int seconds);

    /**
     * expireAt
     * @param key
     * @param seconds
     * @return
     */
    Boolean expireAt(final String key, final long seconds);

    /**
     * expireAt
     * @param key
     * @param date
     * @return
     */
    Boolean expireAt(final String key, final Date date);

    /**
     * get remain expired time
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * hincrBy
     * @param key
     * @param field
     * @param value
     * @return
     */
    long hincrBy(String key, String field, long value);

    void set(String key, String value);

    void set(byte[] key, byte[] value);

    /**
     *
     * @author LiuQing.Qin
     * @date 2017年12月9日 下午6:32:04
     * @param key
     * @param value
     * @param expireTime 过期时间，单位：秒
     */
    void set(byte[] key, byte[] value, int expireTime);

    void set(String key, String value, int expireTime);
    void hmset(String key, Map<String, String> hash);
    public void hmset(String key, Map<String, String> hash, int time);
    Boolean setNx(String key, String value, int time, TimeUnit timeUnit);

    void set(String key, Object value, int expireTime);
    void set(String key, Object value);
    Boolean setbit(String key, long offset, boolean value);


    void setbitBatch(String key, Set<Long> offsets, boolean value);

    byte[] get(byte[] key);
    String get(String key);
    /**
     * 获取指定的key的value值
     * @param key
     * @param tClass
     * @param <T>
     * @return T类型对象
     */
    <T> T get(String key, Class<T> tClass);
    Object get(String key, JavaType javaType);
    /**
     * 获取指定的key的list value值
     * @param key
     * @param tClass
     * @return T类型对象
     */
    <T> List<T> gets(String key, Class<T> tClass);

    Boolean getbit(String key, long offset);

    /**
     * 对指定的 key 求offset值
     *
     * @author LiuQing.Qin
     * @date 2017年9月18日 下午9:13:45
     * @param key
     * @return
     */
    Set<Long> getbitOffset(String key);


    /**
     * 对一个或多个 key 求逻辑差(先求交集后求差)
     *
     * @author LiuQing.Qin
     * @date 2017年9月19日 上午10:19:10
     * @param srcKeys
     * @return
     */
    Set<Long> bitopDiff(final String... srcKeys);

    /**
     * 对一个或多个 key 求逻辑差(先求交集后求差)，并将结果保存到 destkey
     *
     * @author LiuQing.Qin
     * @date 2017年9月19日 上午10:24:01
     * @param destKey
     * @param srcKeys
     * @return
     */
    Long bitopDiffStore(final String destKey, final String... srcKeys);

    /**
     * 对一个或多个 key 求逻辑并 (交集)
     *
     * @author LiuQing.Qin
     * @date 2017年9月19日 上午10:17:27
     * @param srcKeys
     * @return
     */
    Set<Long> bitopIntersect(final String... srcKeys);

    /**
     * 对一个或多个 key 求逻辑并(交集)，并将结果保存到 destkey
     *
     * @author LiuQing.Qin
     * @date 2017年9月19日 上午10:01:17
     * @param destKey
     * @param srcKeys
     * @return
     */
    Long bitopIntersectStore(final String destKey, final String... srcKeys);

    /**
     * 对一个或多个 key 求逻辑或(并集)
     *
     * @author LiuQing.Qin
     * @date 2017年9月19日 上午10:15:50
     * @param srcKeys 取并集的Key
     * @return
     */
    Set<Long> bitopUnion(final String... srcKeys);

    /**
     * 对一个或多个 key 求逻辑或(并集)，并将结果保存到 destkey
     *
     * @author LiuQing.Qin
     * @date 2017年9月19日 上午10:14:56
     * @param destKey
     * @param srcKeys
     * @return
     */
    Long bitopUnionStore(final String destKey, final String... srcKeys);

    Long bitcount(final String key);

    Long bitcount(final String key, long start, long end);

    Long bitpos(String key, boolean value);

    Long bitpos(String key, boolean value, long start);

    Long bitpos(String key, boolean value, long start, long end);

    /**
     * hset
     * @param key
     * @param field
     * @param value
     */
    void hset(String key, String field, String value);
    /**
     * hset
     * @param key
     * @param field
     * @param value
     */
    void hset(String key, String field, String value, int time);

    /**
     * hset
     * @param key
     * @param field
     * @param value
     */
    void hset(String key, String field, Object value);

    /**
     * hset
     * @param key
     * @param field
     * @param tClass
     * @param <T>
     * @return
     */
    <T> T hget(String key, String field, Class<T> tClass);

    /**
     * hsets
     * @param key
     * @param field
     * @param tClass
     * @param <?>
     * @return
     */
    <T> List<T> hgets(String key, String field, Class<T> tClass);

    /**
     * hdel
     * @param key
     * @param field
     */
    void hdel(String key, String field);

    /**
     * hgetAll
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> Map<String, T> hgetAll(String key, Class<T> clazz);

    /**
     * Test for existence of a specified field in a hash. <b>Time complexity:</b> O(1)
     *
     * @param key
     * @param field
     * @return Return 1 if the hash stored at key contains the specified field. Return 0 if the key
     *         is not found or the field is not present.
     */
    Boolean hexists(final String key, final String field);

    /**
     * exists
     * @param key
     * @return
     */
    Boolean exists(final String key);

    /**
     * zAdd
     * @param key
     * @param value
     */
    boolean zAdd(String key, String value);

    /**
     * zAdd
     * @param key
     * @param score
     * @param value
     */
    boolean zAdd(String key, double score, String value);

    /**
     * zAdd
     * @param key
     * @param score
     * @param value
     */
    boolean zAdd(String key, double score, Object value);

    /**
     * zCard
     * @param key
     * @return
     */
    Long zCard(String key);

    /**
     * zRem
     * @param key
     * @param value
     */
    void zRem(String key, String value);

    /**
     *
     * @param key
     * @param minScore
     * @param maxScore
     */
    public Long zRemByScore(String key, double minScore, double maxScore);
    /**
     * zrange
     * @param key
     * @param start
     * @param end
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> zrange(String key, long start, long end, Class<T> clazz);
    <T> List<T> zrevrange(String key, long start, long end, Class<T> clazz);
    <T> List<T> zrangeByScore(String key, double minScore, double maxScore, Class<T> clazz);
    <T> List<T> zrevByScore(String key, double minScore, double maxScore, Class<T> clazz);
    /**
     * lpush
     * @param key
     * @param value
     */
    void lpush(String key, String value);
    void rpush(String key, Object value);
    /**
     * lpush
     * @param key
     * @param value
     */
    void rpush(String key, String value);
    /**
     * lrange
     * @param key
     * @param start
     * @param end
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> lrange(String key, int start, int end, Class<T> clazz);

    /**
     * ltrim
     * 可用来清空list：ltrim(key,1,0)
     * @param key
     * @param start
     * @param end
     */
    void ltrim(String key, int start, int end);

    /**
     * llen
     * @param key
     */
    long llen(String key);
    /**
     * Add the specified member to the set value stored at key. If member is already a member of the
     * set no operation is performed. If key does not exist a new set with the specified member as
     * sole member is created. If the key exists but does not hold a set value an error is returned.
     * <p>
     * Time complexity O(1)
     *
     * @param key
     * @param members
     * @return Integer reply, specifically: 1 if the new element was added 0 if the element was
     *         already a member of the set
     */
    Long sAdd(final String key, final String... members);

    /**
     * sIsMember
     * @param key
     * @param member
     * @return
     */
    boolean sIsMember(final String key, final String member);

    /**
     * Return the set cardinality (number of elements). If the key does not exist 0 is returned,
     * like for empty sets.
     *
     * @param key
     * @return Integer reply, specifically: the cardinality (number of elements) of the set as an
     *         integer.
     */
    Long sCard(final String key);

    /**
     * Return all the members (elements) of the set value stored at key. This is just syntax glue
     * for {@link #(String...) SINTER}.
     * <p>
     * Time complexity O(N)
     *
     * @param key
     * @return Multi bulk reply
     */
    Set<String> sMembers(String key);

    /**
     * Remove the specified member from the set value stored at key. If member was not a member of
     * the set no operation is performed. If key does not hold a set value an error is returned.
     * <p>
     * Time complexity O(1)
     *
     * @param key
     * @param members
     * @return Integer reply, specifically: 1 if the new element was removed 0 if the new element
     *         was not a member of the set
     */
    Long sRem(final String key, final String... members);

    /**
     * Return the members of a set resulting from the intersection of all the sets hold at the
     * specified keys. Like in {@link #(String, long, long) LRANGE} the result is sent to the
     * client as a multi-bulk reply (see the protocol specification for more information). If just a
     * single key is specified, then this command produces the same result as
     * {@link #(String) SMEMBERS}. Actually SMEMBERS is just syntax sugar for SINTER.
     * <p>
     * Non existing keys are considered like empty sets, so if one of the keys is missing an empty
     * set is returned (since the intersection with an empty set always is an empty set).
     * <p>
     * Time complexity O(N*M) worst case where N is the cardinality of the smallest set and M the
     * number of sets
     *
     * @param keys
     * @return Multi bulk reply, specifically the list of common elements.
     */
    Set<String> sIntersect(final String... keys);

    /**
     * returned the resulting set is sotred as dstkey.
     * <p>
     * Time complexity O(N*M) worst case where N is the cardinality of the smallest set and M the
     * number of sets
     *
     * @param dstkey
     * @param keys
     * @return Status code reply
     */
    Long sIntersectStore(final String dstkey, final String... keys);

    /**
     * Return the members of a set resulting from the union of all the sets hold at the specified
     * keys. Like in {@link #(String, long, long) LRANGE} the result is sent to the client as
     * a multi-bulk reply (see the protocol specification for more information). If just a single
     * key is specified, then this command produces the same result as {@link #(String)
     * SMEMBERS}.
     * <p>
     * Non existing keys are considered like empty sets.
     * <p>
     * Time complexity O(N) where N is the total number of elements in all the provided sets
     *
     * @param keys
     * @return Multi bulk reply, specifically the list of common elements.
     */
    Set<String> sUnion(final String... keys);

    /**
     * returned the resulting set is stored as dstkey. Any existing value in dstkey will be
     * over-written.
     * <p>
     * Time complexity O(N) where N is the total number of elements in all the provided sets
     *
     * @param dstkey
     * @param keys
     * @return Status code reply
     */
    Long sUnionStore(final String dstkey, final String... keys);

    /**
     * Return the difference between the Set stored at key1 and all the Sets key2, ..., keyN
     * <p>
     * <b>Example:</b>
     *
     * <pre>
     * key1 = [x, a, b, c]
     * key2 = [c]
     * key3 = [a, d]
     * SDIFF key1,key2,key3 =&gt; [x, b]
     * </pre>
     *
     * Non existing keys are considered like empty sets.
     * <p>
     * <b>Time complexity:</b>
     * <p>
     * O(N) with N being the total number of elements of all the sets
     *
     * @param keys
     * @return Return the members of a set resulting from the difference between the first set
     *         provided and all the successive sets.
     */
    Set<String> sDiff(final String... keys);

    /**
     * This command works exactly like {@link #(String...) SDIFF} but instead of being returned
     * the resulting set is stored in dstkey.
     *
     * @param dstkey
     * @param keys
     * @return Status code reply
     */
    Long sDiffStore(final String dstkey, final String... keys);


    /**
     * rpop
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T rpop(String key, Class<T> clazz);

    <T> T lpop(String key, Class<T> clazz);
    /**
     * lpush
     * @param key
     * @param value
     */
    void lpush(String key, Object value);

    /**
     * spop
     * @param key
     * @return
     */
    String spop(String key);

    /**
     * spop
     * @param key
     * @param count
     * @return
     */
    Set<String> spop(String key, long count);

    /**
     * eval
     * @param script
     * @param keys
     * @param args
     * @return
     */
    long eval(String script, List<String> keys, List<String> args);


    /**
     * evalsha
     * @param sha1
     * @param keys
     * @param args
     * @return
     */
    long evalsha(String sha1, List<String> keys, List<String> args);


    /**
     *
     * @param var1
     * @param var2
     * @return
     */
    String rename(String var1, String var2);


}
