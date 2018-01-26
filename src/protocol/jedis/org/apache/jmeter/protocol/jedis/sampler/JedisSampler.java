package org.apache.jmeter.protocol.jedis.sampler;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;

public class JedisSampler extends AbstractSampler implements Interruptible, TestStateListener {
    private static final Logger log = LoggerFactory.getLogger(JedisSampler.class);

    public static final String SERVER = "jedis.ip";
    public static final String PORT = "jedis.port";
    public static final String CONNECT_TIMEOUT = "jedis.connectTimeout";//socket connect timeout
    public static final String SO_TIMEOUT = "jedis.soTimeout";//read timeout
    public static final String MAX_CONNECTION = "jedis.maxConnection";
    public static final String MAX_IDLE = "jedis.maxIdle";
    public static final String AUTH = "jedis.auth";

    public static final String KEY = "jedis.key";
    public static final String VALUE = "jedis.value";
    public static final String OPT_TYPE = "jedis.optType";

    private static JedisPool jedisPool;
    private static JedisOptType jedisOptType;

    public JedisSampler() {
    }

    private void performGet(Jedis jedis, SampleResult res){
        String key = getPropertyAsString(KEY);
        String value = jedis.get(key);
        value = StringUtils.trimToEmpty(value);

        res.setSentBytes(key.length());
        if(StringUtils.isNotEmpty(value)){
            res.setBytes((long)value.getBytes().length);
        }else{
            res.setBytes(0L);
        }
    }

    private void performSet(Jedis jedis, SampleResult res){
        String key = getPropertyAsString(KEY);
        String value = getPropertyAsString(VALUE);

        int expire = 60*10;
        String status = jedis.setex(key, expire, value);

        res.setSentBytes(key.length() + value.length());
        if(StringUtils.isNotEmpty(status)){
            res.setBytes((long)status.getBytes().length);
        }else{
            res.setBytes(0L);
        }
    }

    private void performDel(Jedis jedis, SampleResult res){
        String key = getPropertyAsString(KEY);

        Long rows = jedis.del(key);

        res.setSentBytes(key.length());
        if(rows != null){
            res.setBytes(8L);
        }else{
            res.setBytes(0L);
        }
    }

    @Override
    public SampleResult sample(Entry entry) {
        SampleResult res = new SampleResult();
        res.setSampleLabel("Jedis-" + jedisOptType.name());
        res.setSuccessful(true);

        Jedis jedis = null;
        try{
            res.sampleStart();
            jedis = jedisPool.getResource();

            switch (jedisOptType){
                case GET:{
                    performGet(jedis, res);
                    break;
                }
                case SET:{
                    performSet(jedis, res);
                    break;
                }
                case DEL:{
                    performDel(jedis, res);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            res.setSuccessful(false);
        }finally {
            res.sampleEnd();
            if(jedis != null)jedis.close();
        }

        return res;
    }

    @Override
    public boolean interrupt() {
        return false;
    }

    private void buildJedisPool() throws URISyntaxException {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(5000);
        jedisPoolConfig.setMaxTotal(getPropertyAsInt(MAX_CONNECTION));
        jedisPoolConfig.setMaxIdle(getPropertyAsInt(MAX_IDLE));

        String server = getPropertyAsString(SERVER);
        int port = getPropertyAsInt(PORT);
        int connectionTimeout = getPropertyAsInt(CONNECT_TIMEOUT);
        int soTimeout = getPropertyAsInt(SO_TIMEOUT);
        String auth = getPropertyAsString(AUTH);

        /**
         * an url example like follows
         * redis://:foobared@10.0.50.11:6379/4
         */
        URI uri = new URI(String.format("redis://:%s@%s:%s", auth, server, port));

        jedisPool = new JedisPool(
                jedisPoolConfig,
                uri,
                connectionTimeout,
                soTimeout);
    }

    @Override
    public void testStarted() {
        try {
            //set jedis operation type, get, set, del
            String jedisOptTypeName = getPropertyAsString(OPT_TYPE);
            synchronized (this) {
                jedisOptType = JedisOptType.from(jedisOptTypeName);
            }

            //prepare jedis pool
            if (jedisPool == null) {
                synchronized (this) {
                    if (jedisPool == null) {
                        buildJedisPool();
                    }
                }
            }else{
                synchronized (this){
                    if(jedisPool != null){
                        jedisPool.close();
                        jedisPool = null;
                    }
                    buildJedisPool();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void testStarted(String host) {

    }

    @Override
    public void testEnded() {

    }

    @Override
    public void testEnded(String host) {

    }
}
