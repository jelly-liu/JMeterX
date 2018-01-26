package org.apache.jmeter.protocol.jedis.sampler;

public enum JedisOptType {
    GET, SET, DEL;

    public static JedisOptType from(String name){
        for(JedisOptType jedisOptType : JedisOptType.values()){
            if(JedisOptType.GET.name().equals(name)){
                return JedisOptType.GET;
            }else if(JedisOptType.SET.name().equals(name)){
                return JedisOptType.SET;
            }else if(JedisOptType.DEL.name().equals(name)){
                return JedisOptType.DEL;
            }
        }
        throw new RuntimeException("the type of name should be in GET, SET, DEL");
    }
}
