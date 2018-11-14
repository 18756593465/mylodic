package com.bcx.mylodic;

import com.bcx.mylodic.constant.SerialOrder;
import com.bcx.mylodic.core.*;
import com.bcx.mylodic.exception.SerialException;
import com.bcx.mylodic.helper.StringHelper;
import com.bcx.mylodic.helper.TimeHelper;
import com.bcx.mylodic.io.Resource;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class SerialImpl implements Serial {

    private static final String DEFAULT_NUMBER = "0001";
    private static final String SEPARATOR = System.getProperty("file.separator");
    /**
     * 保存目录
     */
    private static final String PATH = SEPARATOR+"serialNumber";


    public String serial(String serialCode) {
         checkCode(serialCode);
         return serial(serialCode,null);
    }



    public String serial(String serialCode, Configuration configuration) {
        checkCode(serialCode);
        String value ;
        //反序列化 实体
        SerialConfig config = null;
        synchronized (SerialImpl.class) {
            config = Resource.deserialize(serialCode);
            if (config != null) {
                Configuration con = config.getConfiguration();
                List<Node> nodeList = con.getNodes();
                nodeList.forEach(node->{
                    Iterator<Node> iterator = configuration.getNodes().iterator();
                    while(iterator.hasNext()){
                        Node n = iterator.next();
                        if(!StringHelper.isEmpty(n.getValue())) {
                            node.setValue(n.getValue().getValue());
                            iterator.remove();
                            break;
                        }
                        iterator.remove();
                        break;
                    }
                });
                value = Resource.getNextSerialID(con);
                config.setValue(value);
                config.setLastUpdateTime(TimeHelper.getNow());
                config.setCode(serialCode);
                config.setConfiguration(con);
                //序列化
                Resource.serialize(config);
            } else {
                if (configuration == null) {
                    value = DEFAULT_NUMBER;
                    //默认使用最简单的序列号递增
                    Node node = new Node();
                    node.createNumberRule();
                    node.setNumberRule(new Node.NumberRule().setStartNumber(value));
                    Configuration c = ConfigurationBuilder.build().addNode(node);
                    config = new SerialConfig().setConfiguration(c).setLastUpdateTime(TimeHelper.getNow()).setCode(serialCode).setValue(value);
                    //序列化
                    Resource.serialize(config);
                } else {
                    value = Resource.getNextSerialID(configuration);
                    config = new SerialConfig().setConfiguration(configuration).setLastUpdateTime(TimeHelper.getNow()).setCode(serialCode).setValue(value);
                    //序列化
                    Resource.serialize(config);
                }
            }
        }
        return value;
    }

    public String serial(String serialCode, boolean number, boolean time, String... strs) {
        checkCode(serialCode);
        StringBuilder value = new StringBuilder();
        if(strs!=null && strs.length>0){
            for(String str : strs){
                value.append(str);
            }
        }
        Configuration configuration = new Configuration();
        Node node = new Node();
        node.setValue(new Node.NodeValue().setValue(value.toString()));
        if(number){
            node.createNumberRule();
        }
        if(time){
            node.setTimeType("yyyyMMdd");
        }
        configuration.addNode(node);
        return serial(serialCode,configuration);
    }

    @Override
    public String serial(String serialCode, String timeType, int digit, String... strs) {
        checkCode(serialCode);
        Configuration configuration = new Configuration();
        Node node = new Node();
        node.setTimeType(timeType);
        node.setNumberDigit(digit);
        StringBuilder builder = new StringBuilder();
        if(strs!=null && strs.length>0){
            for(String str : strs){
                builder.append(str);
            }
        }
        node.setValue(builder.toString());
        configuration.addNode(node);
        return serial(serialCode,configuration);
    }



    @Override
    public String serial(String serialCode, String timeType, String number, int digit, String add, String... strs) {
        checkCode(serialCode);
        StringBuilder builder = new StringBuilder();
        if(strs!=null && strs.length>0){
            for(String str : strs){
                builder.append(str);
            }
        }
        Configuration configuration = new Configuration();
        Node node = new Node();
        if(!StringHelper.isEmpty(timeType)){
            node.setTimeType(timeType);
        }
        if (!StringHelper.isEmpty(number)) {
            node.setStartNumber(number);
        }
        if(!StringHelper.isEmpty(digit)){
            node.setNumberDigit(digit);
        }
        if(!StringHelper.isEmpty(add)){
            node.setNumberAdd(Double.parseDouble(add));
        }
        configuration.addNode(node);
        return serial(serialCode,configuration);
    }

    @Override
    public String serial(String serialCode, Double number, Double add, int digit, SerialOrder order) {
        checkCode(serialCode);
        Configuration configuration = new Configuration();
        Node node = new Node();
        node.setNumberDigit(digit);
        node.setNumberOrder(order);
        node.setNumberAdd(add);
        node.setStartNumber(Double.toString(number));
        configuration.addNode(node);
        return serial(serialCode,configuration);
    }

    public boolean remove(String serialCode) {
        checkCode(serialCode);
        String configName = serialCode+".config";
        String fileName = System.getProperty("java.io.tmpdir")+PATH+SEPARATOR+configName;
        File file = new File(fileName);
        boolean ret = false;
        if(file.exists()){
            ret = file.delete();
            if(!ret)
                throw new SerialException("fail to delete "+serialCode+".config file");
        }
        return ret;
    }


    private void checkCode(String serialCode){
        if(serialCode==null || serialCode.equals("")){
            throw new SerialException("serialCode  must not be null !");
        }
    }


	public void push(){
	
	}



}
