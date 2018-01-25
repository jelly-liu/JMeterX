package org.apache.jmeter.protocol.jedis.sampler;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import redis.clients.jedis.Jedis;

public class JedisSampler extends AbstractSampler implements Interruptible {
    public static final String SERVER = "jedis.ip";
    public static final String PORT = "jedis.port";
    public static final String AUTH = "jedis.auth";
    public static final String KEY = "jedis.key";

    private Jedis jedis;

    public JedisSampler() {
        if(StringUtils.isNotEmpty(getPropertyAsString(SERVER))
                && StringUtils.isNotEmpty(getPropertyAsString(PORT))
                && StringUtils.isNotEmpty(getPropertyAsString(AUTH))){
            jedis = new Jedis(getPropertyAsString(SERVER), Integer.parseInt(getPropertyAsString(PORT)));
            jedis.auth(getPropertyAsString(AUTH));
        }
    }

    private String getLabel(){
        String label = "";
        label += "server:" + getPropertyAsString(SERVER) + ", ";
        label += "server:" + getPropertyAsString(PORT) + ", ";
        label += "server:" + getPropertyAsString(AUTH);
        return label;
    }

    @Override
    public SampleResult sample(Entry entry) {
        if(jedis == null){
            jedis = new Jedis(getPropertyAsString(SERVER), Integer.parseInt(getPropertyAsString(PORT)));
            jedis.auth(getPropertyAsString(AUTH));
        }

        SampleResult res = new SampleResult();
        res.setSuccessful(true);

        try{
            res.sampleStart();

            String key = getPropertyAsString(KEY);
            String value = jedis.get(key);
            value = StringUtils.trimToEmpty(value);

            res.setSentBytes(key.length());
            if(StringUtils.isNotEmpty(value)){
                res.setBytes((long)value.getBytes().length);
            }else{
                res.setBytes(0L);
            }
        }catch (Exception e){
            e.printStackTrace();
            res.setSuccessful(false);
        }finally {
            res.sampleEnd();
        }

        return res;
    }

    @Override
    public boolean interrupt() {
        return false;
    }
}
