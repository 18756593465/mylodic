package com.bcx.mylodic.io;

import com.bcx.mylodic.constant.SerialOrder;
import com.bcx.mylodic.core.Configuration;
import com.bcx.mylodic.core.Node;
import com.bcx.mylodic.core.SerialConfig;
import com.bcx.mylodic.exception.SerialException;
import com.bcx.mylodic.helper.TimeHelper;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class Resource {

    private static Logger log = Logger.getLogger(Resource.class.getName());

    private static final String SEPARATOR = System.getProperty("file.separator");

    /**
     * 保存目录
     */
    private static final String PATH = SEPARATOR+"serialNumber";


    private static final String ZERO = "0";


    /**
     * 最高位数
     */
    private static final int MAX_NUMBER_DIGIT = 12;

    /**
     * 后缀
     */
    private static final String FILE_TYPE = ".config";

    private static final String DEFAULT_NUM = "0001";

    /**
     * 通过序列代码获取保存的序列号规则历史
     * 从指定的系统目录中获取
     *
     * @param serialCode  序列号代码
     * @return            文件
     */
    private static File  getSerialFile(String serialCode){
        File file = null;
        //系统目录
        String sysPath = System.getProperty("java.io.tmpdir");
        File f = new File(sysPath+PATH+SEPARATOR+serialCode+FILE_TYPE);
        if(f.exists()){
            return f;
        }
        File dir = new File(sysPath+PATH);
        if(!dir.exists()){
            //创建文件夹
            boolean mk = dir.mkdir();
            //创建文件
            if(mk) {
                 file = createFile(serialCode,sysPath);
            }else{
                throw new SerialException("create dir serialNumber error");
            }
        }else if(dir.exists() && dir.isDirectory()){
            file = new File(sysPath+PATH+SEPARATOR+serialCode + FILE_TYPE);
            if(!file.exists()){
                file = createFile(serialCode,sysPath);
            }
        }

        if(file==null){
            throw new SerialException("create dir serialNumber or create file "+serialCode+".config error!");
        }
        return file;
    }



    private static File createFile(String serialCode,String sysPath){
        File file = new File(sysPath+PATH+SEPARATOR+serialCode + FILE_TYPE);
        try {
            boolean create = file.createNewFile();
            if(!create){
                throw new SerialException("create file "+serialCode+".config file error");
            }
        } catch (IOException e) {
            throw new SerialException("create file "+serialCode+".config file error");
        }
        return file;
    }



    public static SerialConfig  deserialize(String serialCode){
        SerialConfig config = null;
        ObjectInputStream inputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(getSerialFile(serialCode));
            inputStream = new ObjectInputStream(fileInputStream);
            config = (SerialConfig)inputStream.readObject();
        } catch (Exception e) {
             log.info(e.getMessage());
        }finally {
            try {
                if(inputStream!=null)
                   inputStream.close();
                if(fileInputStream!=null)
                   fileInputStream.close();
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
        return config;
    }



    public static void  serialize(SerialConfig config){
        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(getSerialFile(config.getCode()));
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(config);
        } catch (IOException e) {
            log.info(e.getMessage());
        }finally {
            try {
                if(objectOutputStream!=null)
                   objectOutputStream.close();
                if(fileOutputStream!=null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
    }


    /**
     * DMS2018
     * 获取下一个序列号
     *
     * @param configuration   序列号规则
     */
    public static String  getNextSerialID(Configuration configuration){
        List<Node> nodes = configuration.getNodes();
        StringBuilder builder = new StringBuilder();
        for(Node node : nodes){
            Node.NodeValue value = node.getValue();
            Node.NumberRule numberRule = node.getNumberRule();
            Node.TimeRule timeRule = node.getTimeRule();
            if(value!=null){
                builder.append(value.getValue());
            }
            if(timeRule!=null){
                builder.append(nextTime(timeRule.getTimeType()));
            }
            if(numberRule!=null){
                String lastNum = numberRule.getStartNumber();
                if(lastNum==null || "".equals(lastNum)){
                    lastNum = DEFAULT_NUM;
                }
                String nextNumber = nextNumber(lastNum,numberRule.getStartDigit(),numberRule.getIter(),numberRule.getOrder());
                builder.append(nextNumber);
                numberRule.setStartNumber(nextNumber);
            }
        }
        return builder.toString();
    }



    /**
     * 通过当前数字，获取下一个数字
     *
     * @param number     当前数字
     * @param digit      初始位数
     * @param addNumber  添加或者减少数字
     * @param order      正序或者倒序
     * @return           返回数字
     */
    public static String  nextNumber(String number, int digit, double addNumber, SerialOrder order){
        //当前数字
        BigDecimal num = BigDecimal.valueOf(Double.parseDouble(number));
        //下一个数字
        BigDecimal nextNum = order.equals(SerialOrder.ASC) ? BigDecimal.valueOf(num.add(BigDecimal.valueOf(addNumber)).doubleValue()) : BigDecimal.valueOf(num.multiply(BigDecimal.valueOf(addNumber)).doubleValue());
        //是否为整数
        String next = BigDecimal.valueOf(nextNum.intValue()).compareTo(nextNum)==0? Integer.toString(nextNum.intValue()) : nextNum.toString();
        //下一个数字位数
        int nowDigit = next.length();
        if(nowDigit<digit){
           StringBuilder builder = new StringBuilder();
           if(next.indexOf('.')==-1) {
               for (int i = 0; i < digit - nowDigit; i++) {
                   builder.append(ZERO);
               }
           }
           builder.append(next);
           return builder.toString();
        }else{
           return next;
        }
    }



    /**
     * 根据初始位数   获取初始数字值
     *
     * @param digit  初始位数
     * @return       初始数字值
     */
    public static String startNumber(int digit){
        checkNumberDigit(digit);
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<digit-1;i++){
            builder.append(ZERO);
        }
        builder.append("1");
        return builder.toString();
    }


    /**
     * 校验digit 过大
     *
     * @param digit  位数
     */
    private static void checkNumberDigit(int digit){
        if(digit > MAX_NUMBER_DIGIT){
            throw new SerialException("digit is too large! ");
        }
    }


    /**
     * 通过当前时间序列  获取下一个时间序列号
     *
     * @param model   时间模板格式
     * @return        返回时间序列
     */
    public static String nextTime(String model){
        return TimeHelper.getNowTime(model);
    }

}
