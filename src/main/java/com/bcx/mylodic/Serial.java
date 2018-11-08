package com.bcx.mylodic;

import com.bcx.mylodic.constant.SerialOrder;
import com.bcx.mylodic.core.Configuration;

/**
 * 序列号接口引擎
 */
public interface Serial {


    /**
     * 获取最普通的序列号  实例： 0001  0002  00005，在达到五位数时，改变成5位数
     * 如果序列号代码存在，则自动获取该代码规则生成的序列号
     *
     * @param serialCode 序列号代码  区分不同的业务序列号
     * @return           最新的序列号
     */
    String   serial(String serialCode);



    /**
     * 自定义复杂序列号
     *
     * @param serialCode     序列号代码
     * @param configuration  序列号配置数据
     * @return               序列号
     */
    String  serial(String serialCode, Configuration configuration);



    /**
     * 自定义序列号     实体：  DMSabc00001   DMSabc00002   DMS2018080300001   DMS2018083100002;
     *
     * @param serialCode   序列号代码
     * @param number       是否循环数字
     * @param time         是否循环时间
     * @param strs         常量字符串
     * @return             生成的序列号
     */
    String  serial(String serialCode,boolean number,boolean time,String ...strs);



    /**
     * 根据参数 生成序列号
     *
     * @param serialCode   序列号代码
     * @param timeType     实现类型  yyyyMMdd
     * @param digit        数字初始长度
     * @param strs         头值
     * @return             生成的序列号
     */
    String  serial(String serialCode,String timeType,int digit,String...strs);


    /**
     * 根据参数 生成序列号
     *
     * @param serialCode  序列号代码
     * @param timeType    时间类型
     * @param number      初始数字
     * @param digit       数字初始长度
     * @param add         自增
     * @param strs        头值
     * @return            生成的序列号
     */
    String  serial(String serialCode,String timeType,String number,int digit,String add,String...strs);



    /**
     * 小数序列号
     *
     * @param serialCode  序列号代码
     * @param number      开始数字
     * @param add         迭代
     * @param order       正序or倒序
     * @param digit       最初位数
     * @return            序列号
     */
    String  serial(String serialCode, Double number, Double add,int digit, SerialOrder order);



    /**
     * 删除不需要的序列号
     *
     * @param serialCode   序列号代码
     * @return             boolean
     */
    boolean  remove(String  serialCode);



}
