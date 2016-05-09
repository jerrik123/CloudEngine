package com.mangocity.ce.util;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;

import com.mangocity.ce.deploy.ConfigManage;
/**
* @ClassName: RedisUtils 
* @Description: Redis操作工具类
* @author YangJie
* @date 2015年11月17日 下午2:50:54 
 */
public class RedisUtils {
	
	public static byte set = 0;
	
	public static Logger log = Logger.getLogger(RedisUtils.class.getName());
	
	/**redis池对象*/
	private static JedisPool pool = null;
	
	/**IP*/
	private static final String IP;
	
	/**端口*/
	private static final Integer PORT;
	
	/**认证密码*/
	private static final String AUTH;
	
	/**最大激活数*/
	private static final Integer MAX_TOTAL;
	
	/**最大等待毫秒数*/
	private static final Integer MAX_WAIT;
	
	/**最大空闲数*/
	private static final Integer MAX_IDLE;
	
	/**redis实例是否可用*/
	private static final Boolean TEST_ON_BORROW;
	
	/**超时*/
	private static final Integer TIME_OUT;
	
	
	static{
		try {
			if(log.isDebugEnabled()){
				log.debug("初始化redis pool begin()...");
			}
			IP = ConfigManage.instance().getSysConfig("redis.ip");
			PORT = Integer.valueOf(ConfigManage.instance().getSysConfig("redis.port"));
			AUTH = ConfigManage.instance().getSysConfig("redis.auth");
			MAX_TOTAL = Integer.valueOf(ConfigManage.instance().getSysConfig("redis.pool.maxActive"));
			MAX_WAIT = Integer.valueOf(ConfigManage.instance().getSysConfig("redis.pool.maxWait"));
			MAX_IDLE = Integer.valueOf(ConfigManage.instance().getSysConfig("redis.pool.maxIdle"));
			TEST_ON_BORROW = Boolean.parseBoolean(ConfigManage.instance().getSysConfig("redis.pool.testOnBorrow"));
			TIME_OUT = Integer.valueOf(ConfigManage.instance().getSysConfig("redis.timeout"));
			if(log.isDebugEnabled()){
				log.debug("初始化redis pool end()...");
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("初始化Redis pool参数失败..." + e);
		}
	}

	static {
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_TOTAL);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			pool = new JedisPool(config, IP,
					PORT,TIME_OUT);
		}

	}
	
	public static void main(String[] args) {
		lpush("abc", "wx");
		lpush("abc", "1");
		System.out.println(lrange("abc", 0, 0));
	}

	/**
	 * <pre>
	 * 获取jedis连接资源，如果使该方法获jedis，需要在finally中return回池里面
	 * such as :
	 * Jedis j = null;
	 * try {
	 *     Jedis j = JedisUtil.getResource();
	 *     j.set("key","value");
	 *     ...
	 * } catch (Exception ex) {
	 * 
	 * } finally {
	 *     JedisUtil.returnResource(j);
	 * }
	 */
	public static Jedis getResource() {
		return pool.getResource();
	}
	
	public static Set<String> keys(String pattern){
		Jedis jedis = null;
		Set<String> set = null;
		try {
			jedis = getResource();
			set =  jedis.keys(pattern);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.keys falid", e);
		}
		return set;
	}
	
	/**
	 * 归还jedis连接资源给池
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		try {
			pool.returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("reids sentinel returnResource falid.", e);
		}
	}

	/**
	 * 归还不可用的Jedis给池
	 * 
	 * @param jedis
	 */
	private static void returnBrokenResource(Jedis jedis) {
		try {
			pool.returnBrokenResource(jedis);
		} catch (Exception e) {
			log.error("reids sentinel returnBrokenResource falid", e);
		}
	}

	public static long del(String key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.del(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.del falid", e);
		}
		return result;
	}

	/**
	 * 以秒为单位，返回给定 key 的剩余生存时TTL, time to live)
	 * 返回值： key 不存在时，返-2 key 存在但没有设置剩余生存时间时，返-1 否则，以秒为单位，返key 的剩余生存时间
	 * @param key
	 * @return
	 */
	public static long ttl(String key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.ttl(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.ttl falid", e);
		}
		return result;
	}

	/**
	 * 给定key是否存在
	 * 返回值： key 存在，返1 ，否则返0
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.exists falid", e);
		}
		return result;
	}

	/**
	 * 给定key是否存在
	 * 返回值： key 存在，返1 ，否则返0
	 * @param key
	 * @return
	 */
	public static boolean exists(byte[] key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.exists falid", e);
		}
		return result;
	}

	/**
	 * 返回key值的类型
	 * 返回值： none (key不存 string (字符 list (列表) set (集合) zset (有序 hash (哈希
	 * @param key
	 * @return
	 */
	public static String type(String key) {
		String result = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.type(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.type falid", e);
		}
		return result;
	}

	/**
	 * 返回 key值的类型
	 * 返回值： none (key不存 string (字符 list (列表) set (集合) zset (有序 hash (哈希
	 * @param key
	 * @return
	 */
	public static String type(byte[] key) {
		String result = "";
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.type(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.type falid", e);
		}
		return result;
	}

	/**
	 * 为给key 设置或更新生存时间，key 过期生存时间0 )，它会被自动删除
	 * 返回值： 设置成功返回 1 key 不存在或者不能为 key 设置生存时间比如在低2.1.3 版本Redis 中你尝试更新 key 的生存时，返0
	 * @param key
	 * @param seconds
	 * @return
	 */
	public static long expire(String key, int seconds) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.expire(key, seconds);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.expire falid", e);
		}
		return result;
	}

	/**
	 * 为给key 设置或更新生存时间，key 过期生存时间0 )，它会被自动删除
	 * 返回值： 设置成功返回 1 key 不存在或者不能为 key 设置生存时间比如在低2.1.3 版本Redis 中你尝试更新 key 的生存时，返0
	 * @param key
	 * @param seconds
	 * @return
	 */
	public static long expire(byte[] key, int seconds) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.expire(key, seconds);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.expire falid", e);
		}
		return result;
	}

	/**
	 * 为给key 设置或更新过期时间；
	 * 返回值： 如果生存时间设置成功，返1 key 不存在或没办法设置生存时间，返回 0
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public static long expireAt(String key, Date expiry) {
		long result = -10000;
		Jedis jedis = null;
		long unixTime = expiry.getTime() / 1000;
		try {
			jedis = getResource();
			result = jedis.expireAt(key, unixTime);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.expireAt falid", e);
		}
		return result;
	}

	/**
	 * 为给key 设置或更新过期时间；
	 * 返回值： 如果生存时间设置成功，返1 key 不存在或没办法设置生存时间，返回 0
	 * @param key
	 * @param unixTime
	 * @return
	 */
	public static long expireAt(byte[] key, Date expiry) {
		long result = -10000;
		Jedis jedis = null;
		long unixTime = expiry.getTime() / 1000;
		try {
			jedis = getResource();
			result = jedis.expireAt(key, unixTime);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.expireAt falid", e);
		}
		return result;
	}

	/**
	 * 将字符串value 关联key 如果 key 已经持有其他值， SET 就覆写旧值，无视类型 对于某个原本带有生存时间（TTL）的键来说， SET
	 * 命令成功在这个键上执行时这个键原有的 TTL 将被清除
	 * 返回 OK
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key, String value) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.set(key, value);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.set falid", e);
		}
		return result;
	}

	/**
	 * 将字符串value 关联key 如果 key 已经持有其他值， SET 就覆写旧值，无视类型 对于某个原本带有生存时间（TTL）的键来说， SET
	 * 命令成功在这个键上执行时这个键原有的 TTL 将被清除
	 * 返回 OK
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(byte[] key, byte[] value) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.set(key, value);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.set falid", e);
		}
		return result;
	}

	/**
	 * key 的设为 value ，当且仅key 不存在 若给定的 key 已经存在，则 SETNX 不做任何动作SETNX 是SET if Not
	 * eXists如果不存在，SET)的简写
	 * 返回值： 设置成功，返1 设置失败，返0
	 * @param key
	 * @param value
	 * @return
	 */
	public static long setnx(String key, String value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.setnx(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.setnx falid", e);
		}
		return result;
	}

	/**
	 * key 的设为 value ，当且仅key 不存在 若给定的 key 已经存在，则 SETNX 不做任何动作SETNX 是SET if Not
	 * eXists如果不存在，SET)的简写
	 * 返回值： 设置成功，返1 设置失败，返0
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long setnx(byte[] key, byte[] value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.setnx(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.setnx falid", e);
		}
		return result;
	}

	/**
	 * 将 value 关联key ，并key 的生存时间设seconds (以秒为单如果 key 已经存在SETEX 命令将覆写旧值
	 * 返回值： 设置成功时返OK seconds 参数不合法时，返回一个错误
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public static boolean setex(String key, int seconds, String value) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.setex(key, seconds, value);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.setex falid", e);
		}
		return result;
	}

	/**
	 * 将 value 关联key ，并key 的生存时间设seconds (以秒为单如果 key 已经存在SETEX 命令将覆写旧值
	 * 返回值： 设置成功时返OK seconds 参数不合法时，返回一个错误
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public static boolean setexSolo(String key, int seconds, String value, Jedis jedis) {
		boolean result = false;
		try {
			String status = jedis.setex(key, seconds, value);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
		} catch (Exception e) {
			log.error("JedisCache.setex falid", e);
		}
		return result;
	}

	/**
	 * 将 value 关联key ，并key 的生存时间设seconds (以秒为单如果 key 已经存在SETEX 命令将覆写旧值
	 * 返回值： 设置成功时返OK seconds 参数不合法时，返回一个错误
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public static boolean setex(byte[] key, int seconds, byte[] value) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.setex(key, seconds, value);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.setex falid", e);
		}
		return result;
	}

	/**
	 * value 参数覆写(overwrite)给定 key 存的字符串，从偏移offset 不存在的 key 当作空白字符串处理
	 * 返回值： SETRANGE 修改之后，字符串的长度
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */

	/**
	 * 如果 key 已经存在并且是一个字符串APPEND 命令value 追加key 原来的的末尾 如果 key 不存在， APPEND
	 * 就简单地将给key 设为 value ，就像执SET key value 
	 * 返回值： 追加 value 之后key 中字符串的长度
	 * @param key
	 * @param value
	 * @return
	 */
	public static long append(String key, String value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.append(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.append falid", e);
		}
		return result;
	}

	/**
	 * 如果 key 已经存在并且是一个字符串APPEND 命令value 追加key 原来的的末尾 如果 key 不存在， APPEND
	 * 就简单地将给key 设为 value ，就像执SET
	 * 返回值： 追加 value 之后key 中字符串的长度
	 * @param key
	 * @param value
	 * @return
	 */
	public static long append(byte[] key, byte[] value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.append(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.append falid", e);
		}
		return result;
	}

	/**
	 * 返回 key联的字符串如果 key 不存在那么返回特殊 nil 假如 key 储存的不是字符串类型，返回错误，因GET
	 * 只能用于处理字符串
	 * 返回值： key 不存在时，返nil ，否则，返回 key 的如果 key 不是字符串类型，那么返回错误
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.get(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.get falid", e);
		}
		return result;
	}

	/**
	 * 返回 key 联的字符串如果 key 不存在那么返回特殊 nil 假如 key 储存的不是字符串类型，返回错误，因GET
	 * 只能用于处理字符串
	 * 
	 * 返回值： key 不存在时，返nil ，否则，返回 key 的如果 key 不是字符串类型，那么返回错误
	 * 
	 * @param key
	 * @return
	 */
	public static String getSolo(String key, Jedis jedis) {
		String result = null;

		try {
			jedis = getResource();
			result = jedis.get(key);

		} catch (Exception e) {

			log.error("JedisCache.get falid", e);
		}
		return result;
	}

	/**
	 * 返回 key 联的字符串如果 key 不存在那么返回特殊 nil 假如 key 储存的不是字符串类型，返回错误，因GET
	 * 只能用于处理字符串
	 * 
	 * 返回值： key 不存在时，返nil ，否则，返回 key 的如果 key 不是字符串类型，那么返回错误
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.get(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.get falid", e);
		}
		return result;
	}

	/**
	 * 返回 key 中字符串值的子字符串，字符串的截取范围由 start end 两个偏移量决包括 start end 在内)
	 * 负数偏移量表示从字符串最后开始计数， -1 表示字符-2 表示倒数第二个，以此类推
	 * 
	 * 返回值： 截取得出的子字符串
	 * 
	 * @param key
	 * @param startOffset
	 * @param endOffset
	 * @return
	 */

	/**
	 * 将给key 的设为 value ，并返回 key 的旧old value)key 存在但不是字符串类型时，返回错误
	 * 
	 * 返回值： 返回给定 key 的旧值 key 没有旧时，也即是， key 不存在时，返nil
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getSet(String key, String value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.getSet(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.getSet falid", e);
		}
		return result;
	}

	/**
	 * 将给key 的设为 value ，并返回 key 的旧old value)key 存在但不是字符串类型时，返回错误
	 * 
	 * 返回值： 返回给定 key 的旧值 key 没有旧时，也即是， key 不存在时，返nil
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static byte[] getSet(byte[] key, byte[] value) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.getSet(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.getSet falid", e);
		}
		return result;
	}

	/**
	 * key 中储存的数字值减? 如果 key 不存在，那么 key 的会先被初始化0 ，然后再执行 DECR 操作
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 本操作的值限制在 64 bit)有符号数字表示之内
	 * 
	 * 返回值： 执行 DECR 命令之后 key 的
	 * 
	 * @param key
	 * @return
	 */
	public static long decr(String key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.decr(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.decr falid", e);
		}
		return result;
	}

	/**
	 * key 中储存的数字值减? 如果 key 不存在，那么 key 的会先被初始化0 ，然后再执行 DECR 操作
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 本操作的值限制在 64 bit)有符号数字表示之内
	 * 
	 * 返回值： 执行 DECR 命令之后 key 的
	 * 
	 * @param key
	 * @return
	 */
	public static long decr(byte[] key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.decr(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.decr falid", e);
		}
		return result;
	}

	/**
	 * key 存的值减去减decrement 如果 key 不存在，那么 key 的会先被初始化0 ，然后再执行 DECRBY
	 * 操作如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 本操作的值限制在 64 bit)有符号数字表示之内
	 * 
	 * 返回值： 减去 decrement 之后key 的
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public static long decrBy(String key, long integer) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.decrBy(key, integer);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.decrBy falid", e);
		}
		return result;
	}

	/**
	 * key 存的值减去减decrement 如果 key 不存在，那么 key 的会先被初始化0 ，然后再执行 DECRBY
	 * 操作如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 本操作的值限制在 64 bit)有符号数字表示之内
	 * 
	 * 返回值： 减去 decrement 之后key 的
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public static long decrBy(byte[] key, long integer) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.decrBy(key, integer);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.decrBy falid", e);
		}
		return result;
	}

	/**
	 * key 中储存的数字值增? 如果 key 不存在，那么 key 的会先被初始化0 ，然后再执行 INCR 操作
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 本操作的值限制在 64 bit)有符号数字表示之内
	 * 
	 * 返回值： 执行 INCR 命令之后 key 的
	 * 
	 * @param key
	 * @return
	 */
	public static long incr(String key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.incr(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.incr falid", e);
		}
		return result;
	}

	/**
	 * key 中储存的数字值增? 如果 key 不存在，那么 key 的会先被初始化0 ，然后再执行 INCR 操作
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 本操作的值限制在 64 bit)有符号数字表示之内
	 * 
	 * 返回值： 执行 INCR 命令之后 key 的
	 * 
	 * @param key
	 * @return
	 */
	public static long incr(byte[] key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.incr(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.incr falid", e);
		}
		return result;
	}

	/**
	 * key 存的值加上增increment 如果 key 不存在，那么 key 的会先被初始化0 ，然后再执行 INCRBY
	 * 命令如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 本操作的值限制在 64 bit)有符号数字表示之内
	 * 
	 * 返回值： 加上 increment 之后key 的
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public static long incrBy(String key, long integer) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.incrBy(key, integer);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.incrBy falid", e);
		}
		return result;
	}

	/**
	 * key 存的值加上增increment 如果 key 不存在，那么 key 的会先被初始化0 ，然后再执行 INCRBY
	 * 命令如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 本操作的值限制在 64 bit)有符号数字表示之内
	 * 
	 * 返回值： 加上 increment 之后key 的
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public static long incrBy(byte[] key, long integer) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.incrBy(key, integer);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.incrBy falid", e);
		}
		return result;
	}

	/**
	 * 排序，排序默认以数字作为对象，被解释为双精度浮点数，然后进行比较；
	 * 
	 * 返回键从小到大排序的结
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> sort(String key) {
		List<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sort(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.sort falid", e);
		}
		return result;
	}

	/**
	 * 排序，排序默认以数字作为对象，被解释为双精度浮点数，然后进行比较；
	 * 
	 * 返回键从小到大排序的结
	 * 
	 * @param key
	 * @return
	 */
	public static List<byte[]> sort(byte[] key) {
		List<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sort(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.sort falid", e);
		}
		return result;
	}

	/**
	 * 排序，按SortingParams中的规则排序
	 * 
	 * 返回排序后的结果
	 * 
	 * @param key
	 * @param sortingParameters
	 * @return
	 */
	public static List<String> sort(String key, SortingParams sortingParameters) {
		List<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sort(key, sortingParameters);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.sort falid", e);
		}
		return result;
	}

	/**
	 * 排序，按SortingParams中的规则排序
	 * 
	 * 返回排序后的结果
	 * 
	 * @param key
	 * @param sortingParameters
	 * @return
	 */
	public static List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		List<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sort(key, sortingParameters);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.sort falid", e);
		}
		return result;
	}

	/**
	 * 将哈希表 key 中的field 的设为 value 如果 key 不存在，新的哈希表被创建并进HSET 操作如果 field
	 * 已经存在于哈希表中，旧将被覆盖
	 * 
	 * 返回值： 如果 field 是哈希表中的新建域，并且值设置成功，返回 1 如果哈希表中field 已经存在且旧值已被新值覆盖，返回 0
	 * 
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hset(String key, String field, String value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hset(key, field, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hset falid", e);
		}
		return result;
	}
	
	/**
	 * 将哈希表 key 中的field 的设为 value 如果 key 不存在，新的哈希表被创建并进HSET 操作如果 field
	 * 已经存在于哈希表中，旧将被覆盖
	 * 
	 * 返回值： 如果 field 是哈希表中的新建域，并且值设置成功，返回 1 如果哈希表中field 已经存在且旧值已被新值覆盖，返回 0
	 * 
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hset(byte[] key, byte[] field, byte[] value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hset(key, field, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hset falid", e);
		}
		return result;
	}

	/**
	 * 将哈希表 key 中的field 的设置value ，当且仅当域 field 不存在 若域 field 已经存在，该操作无效如果 key
	 * 不存在，新哈希表被创建并执行 HSETNX 命令
	 * 
	 * 返回值： 设置成功，返1 如果给定域已经存在且没有操作被执行，返回 0
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hsetnx(String key, String field, String value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hsetnx(key, field, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hsetnx falid", e);
		}
		return result;
	}

	/**
	 * 将哈希表 key 中的field 的设置value ，当且仅当域 field 不存在 若域 field 已经存在，该操作无效如果 key
	 * 不存在，新哈希表被创建并执行 HSETNX 命令
	 * 
	 * 返回值： 设置成功，返1 如果给定域已经存在且没有操作被执行，返回 0
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hsetnx(byte[] key, byte[] field, byte[] value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hsetnx(key, field, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hsetnx falid", e);
		}
		return result;
	}

	/**
	 * 同时将多field-value (对设置到哈希key 中 此命令会覆盖哈希表中已存在的域 如果 key 不存在，空哈希表被创建并执行
	 * HMSET 操作
	 * 
	 * 返回值： 如果命令执行成功，返OK key 不是哈希hash)类型时，返回错误
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public static boolean hmset(String key, Map<String, String> hash) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.hmset(key, hash);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hmset falid", e);
		}
		return result;
	}

	/**
	 * 同时将多field-value (对设置到哈希key 中 此命令会覆盖哈希表中已存在的域 如果 key 不存在，空哈希表被创建并执行
	 * HMSET 操作
	 * 
	 * 返回值： 如果命令执行成功，返OK key 不是哈希hash)类型时，返回错误
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public static boolean hmset(byte[] key, Map<byte[], byte[]> hash) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.hmset(key, hash);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hmset falid", e);
		}
		return result;
	}

	/**
	 * 获取哈希key 中给定域 field 的
	 * 
	 * 返回值： 给定域的值 当给定域不存在或是给key 不存在时，返nil
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static String hget(String key, String field) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hget(key, field);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hget falid", e);
		}
		return result;
	}

	/**
	 * 获取哈希key 中给定域 field 的
	 * 
	 * 返回值： 给定域的值 当给定域不存在或是给key 不存在时，返nil
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static byte[] hget(byte[] key, byte[] field) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hget(key, field);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hget falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中，或多个给定域的如果给定的域不存在于哈希表，那么返回 nil 值 因为不存在的 key
	 * 被当作一个空哈希表来处理，所以对不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表
	 * 
	 * 返回值： 包含多个给定域的关联值的表，表的排列顺序和给定域参数的请求顺序
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<String> hmget(String key, String... fields) {
		List<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hmget(key, fields);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hmget falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中，或多个给定域的如果给定的域不存在于哈希表，那么返回 nil 值 因为不存在的 key
	 * 被当作一个空哈希表来处理，所以对不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表
	 * 
	 * 返回值： 包含多个给定域的关联值的表，表的排列顺序和给定域参数的请求顺序
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<byte[]> hmget(byte[] key, byte[]... fields) {
		List<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hmget(key, fields);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hmget falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中，的域和在返回里，紧跟每个域名(field name)之后是域的(value)，所以返回的长度是哈希表大小的两
	 * 
	 * 返回值： 以列表形式返回哈希表的域和域的key 不存在，返回空列表
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> hgetAll(String key) {
		Map<String, String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hgetAll(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hgetAll falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中，的域和在返回里，紧跟每个域名(field name)之后是域的(value)，所以返回的长度是哈希表大小的两
	 * 
	 * 返回值： 以列表形式返回哈希表的域和域的key 不存在，返回空列表
	 * 
	 * @param key
	 * @return
	 */
	public static Map<byte[], byte[]> hgetAll(byte[] key) {
		Map<byte[], byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hgetAll(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hgetAll falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中域的数量
	 * 
	 * 返回值： 哈希表中域的数量key 不存在时，返0
	 * 
	 * @param key
	 * @return
	 */
	public static long hlen(String key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hlen(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hlen falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中域的数量
	 * 
	 * 返回值： 哈希表中域的数量key 不存在时，返0
	 * 
	 * @param key
	 * @return
	 */
	public static long hlen(byte[] key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hlen(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hlen falid", e);
		}
		return result;
	}

	/**
	 * 查看哈希key 中，给定field 是否存在
	 * 
	 * 返回值： 如果哈希表含有给定域，返1 如果哈希表不含有给定域，key 不存在，返回 0
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static boolean hexists(String key, String field) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(key, field);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hexists falid", e);
		}
		return result;
	}

	/**
	 * 查看哈希key 中，给定field 是否存在
	 * 
	 * 返回值： 如果哈希表含有给定域，返1 如果哈希表不含有给定域，key 不存在，返回 0
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static boolean hexists(byte[] key, byte[] field) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(key, field);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hexists falid", e);
		}
		return result;
	}

	/**
	 * 为哈希表 key 中的field 的加上增量 increment 增量也可以为负数，相当于对给定域进行减法操作如果 key
	 * 不存在，新的哈希表被创建并执HINCRBY 命令如果field 不存在，那么在执行命令前，域的被初始化0 对一个储存字符串值的field 执行
	 * HINCRBY 命令将成一个错误 本操作的值被限制64 bit)有符号数字表示之内
	 * 
	 * 返回值： 执行 HINCRBY 命令之后，哈希表 key 中域 field 的
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hincrBy(String key, String field, long value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hincrBy(key, field, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hincrBy falid", e);
		}
		return result;
	}

	/**
	 * 为哈希表 key 中的field 的加上增量 increment 增量也可以为负数，相当于对给定域进行减法操作如果 key
	 * 不存在，新的哈希表被创建并执HINCRBY 命令如果field 不存在，那么在执行命令前，域的被初始化0 对一个储存字符串值的field 执行
	 * HINCRBY 命令将成一个错误 本操作的值被限制64 bit)有符号数字表示之内
	 * 
	 * 返回值： 执行 HINCRBY 命令之后，哈希表 key 中域 field 的
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static long hincrBy(byte[] key, byte[] field, long value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hincrBy(key, field, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hincrBy falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中的域
	 * 
	 * 返回值： 包含哈希表中域的表 key 不存在时，返回一个空表
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> hkeys(String key) {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hkeys(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hkeys falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中的域
	 * 
	 * 返回值： 包含哈希表中域的表 key 不存在时，返回一个空表
	 * 
	 * @param key
	 * @return
	 */
	public static Set<byte[]> hkeys(byte[] key) {
		Set<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hkeys(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hkeys falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中所有域的
	 * 
	 * 返回值： 包含哈希表中值的表 key 不存在时，返回一个空表
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> hvals(String key) {
		List<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hvals(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hvals falid", e);
		}
		return result;
	}

	/**
	 * 返回哈希key 中所有域的
	 * 
	 * 返回值： 包含哈希表中值的表 key 不存在时，返回一个空表
	 * 
	 * @param key
	 * @return
	 */
	public static Collection<byte[]> hvals(byte[] key) {
		Collection<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hvals(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hvals falid", e);
		}
		return result;
	}

	/**
	 * 返回集合 key 中的成员不存在的 key 被视为空集合
	 * 
	 * 返回 集合中的成员
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.smembers(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.smembers falid", e);
		}
		return result;
	}

	/**
	 * 返回集合 key 中的成员不存在的 key 被视为空集合
	 * 
	 * 返回 集合中的成员
	 * 
	 * @param key
	 * @return
	 */
	public static Set<byte[]> smembers(byte[] key) {
		Set<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.smembers(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.smembers falid", e);
		}
		return result;
	}

	/**
	 * 判断 member 元素是否集合 key 的成员
	 * 
	 * 返回 如果 member 元素是集合的成员，返1 如果 member 元素不是集合的成员，key 不存在，返回 0
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static boolean sismember(String key, String member) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sismember(key, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.sismember falid", e);
		}
		return result;
	}

	/**
	 * 判断 member 元素是否集合 key 的成员
	 * 
	 * 返回 如果 member 元素是集合的成员，返1 如果 member 元素不是集合的成员，key 不存在，返回 0
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static boolean sismember(byte[] key, byte[] member) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sismember(key, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.sismember falid", e);
		}
		return result;
	}

	/**
	 * 返回集合 key 的基集合中元素的数量)
	 * 
	 * 返回值： 集合的基数 key 不存在时，返0
	 * 
	 * @param key
	 * @return
	 */
	public static long scard(String key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.scard(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.scard falid", e);
		}
		return result;
	}

	/**
	 * 返回集合 key 的基集合中元素的数量)
	 * 
	 * 返回值： 集合的基数 key 不存在时，返0
	 * 
	 * @param key
	 * @return
	 */
	public static long scard(byte[] key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.scard(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.scard falid", e);
		}
		return result;
	}

	/**
	 * 移除并返回集合中的一个随机元素
	 * 
	 * 返回 被移除的随机元素key 不存在或 key 是空集时，返nil
	 * 
	 * @param key
	 * @return
	 */
	public static String spop(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.spop(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.spop falid", e);
		}
		return result;
	}

	/**
	 * 移除并返回集合中的一个随机元素
	 * 
	 * 返回 被移除的随机元素key 不存在或 key 是空集时，返nil
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] spop(byte[] key) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.spop(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.spop falid", e);
		}
		return result;
	}

	/**
	 * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，
	 * SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动
	 * 
	 * 返回 只提key 参数时，返回元素；如果集合为空，返回 nil 如果提供count 参数，那么返回一个数组；如果集合为空，返回空数组
	 * 
	 * @param key
	 * @return
	 */
	public static String srandmember(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.srandmember(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.srandmember falid", e);
		}
		return result;
	}

	/**
	 * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，
	 * SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动
	 * 
	 * 返回 只提key 参数时，返回元素；如果集合为空，返回 nil 如果提供count 参数，那么返回一个数组；如果集合为空，返回空数组
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] srandmember(byte[] key) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.srandmember(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.srandmember falid", e);
		}
		return result;
	}

	/**
	 * 将 value 插入到列key 的表头，当且仅当 key 存在并且是一个列表key 不存在时LPUSHX 命令也不做
	 * 
	 * 返回值： LPUSHX 命令执行之后，表的长度
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static long lpushx(String key, String value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lpushx(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lpushx falid", e);
		}
		return result;
	}
	
	/**
	 * 插入一个值到list顶部
	 * 
	 * 返回值： LPUSHX 命令执行之后，表的长度
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static long lpush(String key, String value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lpush(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lpush falid", e);
		}
		return result;
	}
	
	
	
	/**
	 * values是一个列表
	 * 
	 * 返回值： LPUSHX 命令执行之后，表的长度
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static long lpush(String key, String ... values) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lpush(key, values);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lpush falid", e);
		}
		return result;
	}
	
	/**
	 * 取指定范围的列表值
	 * 
	 * 返回值： LPUSHX 命令执行之后，表的长度
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static List<String> lrange(String key, long start,long end) {
		List<String> list = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			list = jedis.lrange(key, start, end);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lrange falid", e);
		}
		return list;
	}

	/**
	 * 将 value 插入到列key 的表头，当且仅当 key 存在并且是一个列表key 不存在时LPUSHX 命令也不做
	 * 
	 * 返回值： LPUSHX 命令执行之后，表的长度
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static long lpushx(byte[] key, byte[] value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lpushx(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lpushx falid", e);
		}
		return result;
	}

	/**
	 * 将 value 插入到列key 的表尾，当且仅当 key 存在并且是一个列表 RPUSH 命令相反，当 key 不存在时 RPUSHX
	 * 命令也不做
	 * 
	 * 返回值： RPUSHX 命令执行之后，表的长度
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static long rpushx(String key, String value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.rpushx(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.rpushx falid", e);
		}
		return result;
	}

	/**
	 * 将 value 插入到列key 的表尾，当且仅当 key 存在并且是一个列表 RPUSH 命令相反，当 key 不存在时 RPUSHX
	 * 命令也不做
	 * 
	 * 返回值： RPUSHX 命令执行之后，表的长度
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static long rpushx(byte[] key, byte[] value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.rpushx(key, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.rpushx falid", e);
		}
		return result;
	}

	/**
	 * 移除并返回列key 的头元素
	 * 
	 * 返回值： 列表的头元素key 不存在时，返nil
	 * 
	 * @param key
	 * @return
	 */
	public static String lpop(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lpop(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lpop falid", e);
		}
		return result;
	}

	/**
	 * 移除并返回列key 的头元素
	 * 
	 * 返回值： 列表的头元素key 不存在时，返nil
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] lpop(byte[] key) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lpop(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lpop falid", e);
		}
		return result;
	}

	/**
	 * 移除并返回列key 的尾元素
	 * 
	 * 返回值： 列表的尾元素key 不存在时，返nil
	 * 
	 * @param key
	 * @return
	 */
	public static String rpop(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.rpop(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.rpop falid", e);
		}
		return result;
	}

	/**
	 * 移除并返回列key 的尾元素
	 * 
	 * 返回值： 列表的尾元素key 不存在时，返nil
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] rpop(byte[] key) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.rpop(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.rpop falid", e);
		}
		return result;
	}

	/**
	 * 返回列表 key 的长度 如果 key 不存在，key 被解释为空列表，返回 0 . 如果 key 不是列表类型，返回一个错误
	 * 
	 * 返回值： 列表 key 的长度
	 * 
	 * @param key
	 * @return
	 */
	public static long llen(String key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.llen(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.llen falid", e);
		}
		return result;
	}

	/**
	 * 返回列表 key 的长度 如果 key 不存在，key 被解释为空列表，返回 0 . 如果 key 不是列表类型，返回一个错误
	 * 
	 * 返回值： 列表 key 的长度
	 * 
	 * @param key
	 * @return
	 */
	public static long llen(byte[] key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.llen(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.llen falid", e);
		}
		return result;
	}


	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start stop 指定
	 * 
	 * 下标(index)参数 start stop 都以 0 为底，也就是说，0 表示列表的第元素，以 1 表示列表的第二个元素，以此类推
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的数第二个元素，以此类推 stop 下标也在 LRANGE
	 * 命令的取值范围之闭区。超出范围的下标值不会引起错误
	 * 
	 * 返回 列表，包含指定区间内的元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<byte[]> lrange(byte[] key, int start, int end) {
		List<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lrange(key, start, end);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lrange falid", e);
		}
		return result;
	}


	/**
	 * 根据参数 count 的，移除列表中与参value 相等的元素
	 * 
	 * count 的可以是以下几种： count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量count count < 0
	 * : 从表尾开始向表头搜索，移除与 value 相等的元素，数量count 的绝对count = 0 : 移除表中 value 相等的
	 * 
	 * 返回值： 被移除元素的数量因为不存在的 key 被视作空empty list)，所以当 key 不存在时LREM 命令总是返回 0
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public static long lrem(byte[] key, int count, byte[] value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lrem(key, count, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lrem falid", e);
		}
		return result;
	}

	/**
	 * 将列key 下标index 的元素的值设置为 value
	 * 
	 * index 参数超出范围，或对一个空列表( key 不存进行 LSET 时，返回错误
	 * 
	 * 返回值： 操作成功返回 ok ，否则返回错误信息
	 * 
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public static boolean lset(byte[] key, int index, byte[] value) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.lset(key, index, value);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lset falid", e);
		}
		return result;
	}

	/**
	 * 对一个列表进行修trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除举个例子，执行命LTRIM list 0 2
	 * ，表示只保留列表 list 的前三个元素，其余元素全部删除
	 * 
	 * 下标(index)参数 start stop 都以 0 为底，也就是说，0 表示列表的第元素，以 1 表示列表的第二个元素，以此类推
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的数第二个元素，以此类推 key
	 * 不是列表类型时，返回错误。超出范围的下标值不会引起错误
	 * 
	 * 返回 命令执行成功时，返回 ok
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean ltrim(byte[] key, int start, int end) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.ltrim(key, start, end);
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.ltrim falid", e);
		}
		return result;
	}

	/**
	 * 返回列表 key 中，下标index 的元素
	 * 
	 * 下标(index)参数 start stop 都以 0 为底，也就是说，0 表示列表的第元素，以 1 表示列表的第二个元素，以此类推
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的数第二个元素，以此类推 如果 key 不是列表类型，返回一个错误
	 * 
	 * 返回 列表中下标为 index 的元素 如果 index 参数的不在列表的区间范围内(out of range)，返nil
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public static byte[] lindex(byte[] key, int index) {
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.lindex(key, index);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.lindex falid", e);
		}
		return result;
	}

	/**
	 * 将 value 插入到列key 当中，位于 pivot 之前或之后
	 * 
	 * pivot 不存在于列表 key 时，不执行任何操作 key 不存在时key 被视为空列表，不执行任何操作如果 key 不是列表类型，返回一个错误
	 * 
	 * 返回 如果命令执行成功，返回插入操作完成之后，列表的长度 如果没有找到 pivot ，返-1 如果 key 不存在或为空列表，返 0
	 * 
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @return
	 */
	public static long linsert(String key, BinaryClient.LIST_POSITION where, String pivot,
			String value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.linsert(key, where, pivot, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.linsert falid", e);
		}
		return result;
	}

	/**
	 * 将 value 插入到列key 当中，位于 pivot 之前或之后
	 * 
	 * pivot 不存在于列表 key 时，不执行任何操作 key 不存在时key 被视为空列表，不执行任何操作如果 key 不是列表类型，返回一个错误
	 * 
	 * 返回 如果命令执行成功，返回插入操作完成之后，列表的长度 如果没有找到 pivot ，返-1 如果 key 不存在或为空列表，返 0
	 * 
	 * @param key
	 * @param where
	 * @param pivot
	 * @param value
	 * @return
	 */
	public static long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot,
			byte[] value) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.linsert(key, where, pivot, value);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.linsert falid", e);
		}
		return result;
	}

	/**
	 * 将一个member 元素及其 score 值加入到有序key 当中
	 * 
	 * 如果某个 member 已经是有序集的成员，那么更新这个 member score 值，并过重新插入这member 元素，来保证 member
	 * 在正确的位置上 score 值可以是整数值或双精度浮点数如果 key 不存在，则创建一个空的有序集并执ZADD 操作 key
	 * 存在但不是有序集类型时，返回错误
	 * 
	 * 返回 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static long zadd(String key, double score, String member) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zadd(key, score, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zadd falid", e);
		}
		return result;
	}

	/**
	 * 将一个member 元素及其 score 值加入到有序key 当中
	 * 
	 * 如果某个 member 已经是有序集的成员，那么更新这个 member score 值，并过重新插入这member 元素，来保证 member
	 * 在正确的位置上 score 值可以是整数值或双精度浮点数如果 key 不存在，则创建一个空的有序集并执ZADD 操作 key
	 * 存在但不是有序集类型时，返回错误
	 * 
	 * 返回 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static long zadd(byte[] key, double score, byte[] member) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zadd(key, score, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zadd falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 的集合中的数量
	 * 返回 key 存在且是有序集类型时，返回有序集的基数 key 不存在时，返0
	 * 
	 * @param key
	 * @return
	 */
	public static long zcard(String key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zcard(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zcard falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 的集合中的数量
	 * 返回 key 存在且是有序集类型时，返回有序集的基数 key 不存在时，返0
	 * 
	 * @param key
	 * @return
	 */
	public static long zcard(byte[] key) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zcard(key);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zcard falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中， score 值在 min max 之间(默认包括 score 值等min max )的成员的数量
	 * 返回 score 值在 min max 之间的成员的数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static long zcount(String key, double min, double max) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zcount(key, min, max);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zcount falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中， score 值在 min max 之间(默认包括 score 值等min max )的成员的数量
	 * 返回 score 值在 min max 之间的成员的数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static long zcount(byte[] key, double min, double max) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zcount(key, min, max);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zcount falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中，成员 member score 值如果 member 元素不是有序key 的成员，key 不存在，返回 nil
	 * 返回 member 成员score 值，以字符串形式表示
	 * @param key
	 * @param member
	 * @return
	 */
	public static double zscore(String key, String member) {
		double result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zscore(key, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zscore falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中，成员 member score 值如果 member 元素不是有序key 的成员，key 不存在，返回 nil
	 * 返回 member 成员score 值，以字符串形式表示
	 * @param key
	 * @param member
	 * @return
	 */
	public static double zscore(byte[] key, byte[] member) {
		double result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zscore(key, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zscore falid", e);
		}
		return result;
	}

	/**
	 * 为有序集 key 的成member score 值加上增increment
	 * 可以通过传负数increment ，让 score 减去相应的，比ZINCRBY key -5 member ，就是让 member
	 * score 值减5 key 不存在，member 不是 key 的成员时ZINCRBY key increment member 等同ZADD
	 * key increment member key 不是有序集类型时，返回一个错误 score 值可以是整数值或双精度浮点数
	 * 返回 member 成员的新 score 值，以字符串形式表示
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static double zincrby(String key, double score, String member) {
		double result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zincrby(key, score, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zincrby falid", e);
		}
		return result;
	}

	/**
	 * 为有序集 key 的成member score 值加上增increment
	 * 可以通过传负数increment ，让 score 减去相应的，比ZINCRBY key -5 member ，就是让 member
	 * score 值减5 key 不存在，member 不是 key 的成员时ZINCRBY key increment member 等同ZADD
	 * key increment member key 不是有序集类型时，返回一个错误 score 值可以是整数值或双精度浮点数
	 * 返回 member 成员的新 score 值，以字符串形式表示
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static double zincrby(byte[] key, double score, byte[] member) {
		double result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zincrby(key, score, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zincrby falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中，指定区间内的成员其中成员的位置按 score 值从小到大)来排序 具有相同 score
	 * 值的成员按字典序(lexicographical order )来排列如果你需要成员按 score 值从大到小)来排列，请使 ZREVRANGE
	 * 命令
	 * 下标参数 start stop 都以 0 为底，也就是说，0 表示有序集第成员，以 1 表示有序集第二个成员，以此类推
	 * 你也可以使用负数下标，以 -1 表示成员-2 表示倒数第二个成员，以此类推超出范围的下标并不会引起错误
	 * 返回 指定区间内，带有 score 可)的有序集成员的列表
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<byte[]> zrange(byte[] key, int start, int end) {
		Set<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrange(key, start, end);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrange falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中， score 值介min max 之间(包括等于 min max )的成员有序集成员按 score
	 * 值从小到大)次序排列具有相同 score 值的成员按字典序(lexicographical order)来排该属性是有序集提供的，不额外的计
	 * 返回 指定区间内，带有 score 可)的有序集成员的列表
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public static Set<String> zrangeByScore(String key, double min, double max) {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrangeByScore(key, min, max);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrangeByScore falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中， score 值介min max 之间(包括等于 min max )的成员有序集成员按 score
	 * 值从小到大)次序排列具有相同 score 值的成员按字典序(lexicographical order)来排该属性是有序集提供的，不额外的计
	 * 返回 指定区间内，带有 score 可)的有序集成员的列表
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		Set<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrangeByScore(key, min, max);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrangeByScore falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中， score 值介min max 之间(包括等于 min max )的成员有序集成员按 score
	 * 值从小到大)次序排列具有相同 score 值的成员按字典序(lexicographical order)来排该属性是有序集提供的，不额外的计
	 * 可LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset
	 * 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程复杂度为 O(N) 时间
	 * 返回 指定区间内，带有 score 可)的有序集成员的列表
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public static Set<String> zrangeByScore(String key, double min, double max, int offset,
			int count) {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrangeByScore(key, min, max, offset, count);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrangeByScore falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中， score 值介min max 之间(包括等于 min max )的成员有序集成员按 score
	 * 值从小到大)次序排列具有相同 score 值的成员按字典序(lexicographical order)来排该属性是有序集提供的，不额外的计
	 * 可LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset
	 * 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程复杂度为 O(N) 时间
	 * 返回 指定区间内，带有 score 可)的有序集成员的列表
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return
	 */
	public static Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset,
			int count) {
		Set<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrangeByScore(key, min, max, offset, count);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrangeByScore falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中， member的score 值介minmember maxmember 的score 值之包括等于 minmember
	 * maxmember 的score )的成员有序集成员按 score 值从小到大)次序排列具有相同 score
	 * 值的成员按字典序(lexicographical order)来排该属性是有序集提供的，不额外的计
	 * 返回 指定区间内，带有 score 可)的有序集成员的列表
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static Set<String> zrangeByScore(String key, String minmember, String maxmember) {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrangeByScore(key, minmember, maxmember);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrangeByScore falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中成member 的排名其中有序集成员按 score 值从小到大)顺序排列排名0 为底，也就是说， score 值最小的成员排名0
	 * 返回 如果 member 是有序集 key 的成员，返回 member 的排名 如果 member 不是有序key 的成员，返回 nil
	 * @param key
	 * @param member
	 * @return
	 */
	public static long zrank(String key, String member) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrank(key, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrank falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中成member 的排名其中有序集成员按 score 值从小到大)顺序排列排名0 为底，也就是说， score 值最小的成员排名0
	 * 返回 如果 member 是有序集 key 的成员，返回 member 的排名 如果 member 不是有序key 的成员，返回 nil
	 * @param key
	 * @param member
	 * @return
	 */
	public static long zrank(byte[] key, byte[] member) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrank(key, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrank falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中成member 的排名其中有序集成员按 score 值从大到小)排序排名0 为底，也就是说， score 值最大的成员排名0
	 * 返回 如果 member 是有序集 key 的成员，返回 member 的排名 如果 member 不是有序key 的成员，返回 nil
	 * @param key
	 * @param member
	 * @return
	 */
	public static long zrevrank(String key, String member) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrevrank(key, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrevrank falid", e);
		}
		return result;
	}

	/**
	 * 返回有序key 中成member 的排名其中有序集成员按 score 值从大到小)排序排名0 为底，也就是说， score 值最大的成员排名0
	 * 返回 如果 member 是有序集 key 的成员，返回 member 的排名 如果 member 不是有序key 的成员，返回 nil
	 * @param key
	 * @param member
	 * @return
	 */
	public static long zrevrank(byte[] key, byte[] member) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zrevrank(key, member);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zrevrank falid", e);
		}
		return result;
	}

	/**
	 * 移除有序key 中，指定排名(rank)区间内的成员
	 * 
	 * 区间分别以下标参start stop 指出，包start stop 在内下标参数 start stop 都以 0 为底，也就是说，0
	 * 表示有序集第成员，以 1 表示有序集第二个成员，以此类推 你也可以使用负数下标，以 -1 表示成员-2 表示倒数第二个成员，以此类推
	 * 
	 * 返回 被移除成员的数量
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static long zremrangeByRank(byte[] key, int start, int end) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zremrangeByRank(key, start, end);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zremrangeByRank falid", e);
		}
		return result;
	}

	/**
	 * 移除有序key 中， score 值介min max 之间(包括等于 min max )的成员
	 * 
	 * 返回 被移除成员的数量
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static long zremrangeByScore(String key, double start, double end) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zremrangeByScore(key, start, end);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zremrangeByScore falid", e);
		}
		return result;
	}

	/**
	 * 移除有序key 中，score 值介min max 之间(包括等于 min max )的成员
	 * 
	 * 返回 被移除成员的数量
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static long zremrangeByScore(byte[] key, double start, double end) {
		long result = -10000;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.zremrangeByScore(key, start, end);
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.zremrangeByScore falid", e);
		}
		return result;
	}

	/**
	 * 清空
	 * 
	 * 返回 被成功移除的域的数量，不包括被忽略的域
	 * 
	 * @return
	 */
	public static boolean flush() {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			String status = jedis.flushAll();
			jedis.flushDB();
			if ("OK".equalsIgnoreCase(status)) {
				result = true;
			}
			returnResource(jedis);
		} catch (Exception e) {
			returnBrokenResource(jedis);
			log.error("JedisCache.hdel falid", e);
		}
		return result;
	}
}
