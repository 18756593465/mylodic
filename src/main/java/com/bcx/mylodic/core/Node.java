package com.bcx.mylodic.core;


import com.bcx.mylodic.constant.SerialOrder;

import java.io.Serializable;

/**
 * 序列组装节点规则
 *
 * @author zhanglei
 */
public class Node implements Serializable {

    /**
     * 值
     */
    private NodeValue value;

    /**
     * 时间规则
     */
    private TimeRule timeRule;


    /**
     * 数字规则
     */
    private NumberRule numberRule;


    public Node(){}


    public Node(NodeValue value,TimeRule timeRule){
        this.timeRule = timeRule;
        this.value =value;
    }

    public Node(NodeValue value,TimeRule timeRule,NumberRule numberRule){
        this(value,timeRule);
        this.numberRule = numberRule;
    }


    public Node setValue(String value){
        if(this.value==null){
            this.value = new NodeValue();
            this.value.setValue(value);
        }else{
            this.value.setValue(value);
        }
        return this;
    }

    public Node setTimeType(String timeType){
        if(this.timeRule==null){
            this.timeRule = new TimeRule(timeType);
        }else{
            this.timeRule.setTimeType(timeType);
        }
        return this;
    }




    public Node setNumberAdd(double add){
        if(numberRule==null){
            this.numberRule = new NumberRule();
            this.numberRule.setIter(add);
        }else{
            this.numberRule.setIter(add);
        }
        return this;
    }


    public Node setNumberOrder(SerialOrder numberOrder){
        if(numberRule==null){
            this.numberRule = new NumberRule();
            this.numberRule.setOrder(numberOrder);
        }else{
            this.numberRule.setOrder(numberOrder);
        }
        return this;
    }

    public Node setStartNumber(String startNumber){
        if(numberRule==null){
            this.numberRule = new NumberRule();
            this.numberRule.setStartNumber(startNumber);
        }else{
            this.numberRule.setStartNumber(startNumber);
        }
        return this;
    }

    public Node setNumberDigit(int digit){
        if(numberRule==null){
            this.numberRule = new NumberRule();
            this.numberRule.setStartDigit(digit);
        }else{
            this.numberRule.setStartDigit(digit);
        }
        return this;
    }

    public Node createNumberRule(){
        this.numberRule = new NumberRule();
        return this;
    }

    public static class NodeValue implements Serializable{

        /**
         * 值
         */
        private String value;


        public String getValue() {
            return value;
        }

        public NodeValue setValue(String value) {
            this.value = value;
            return this;
        }


    }

    public static class TimeRule implements Serializable{

        /**
         * 时间规则
         */
        private String timeType;

        public TimeRule(){}

        public TimeRule(String timeType){
            this.timeType = timeType;
        }

        public String getTimeType() {
            return timeType;
        }

        public TimeRule setTimeType(String timeType) {
            this.timeType = timeType;
            return this;
        }

    }


    public static class NumberRule implements Serializable{



        /**
         * 递增或者递减数字
         */
        private double  iter = 1;

        /**
         * 数字顺序   默认正序
         */
        private SerialOrder order = SerialOrder.ASC;


        /**
         * 上一次修改的数字
         */
        private String startNumber = "0001";

        /**
         * 初始数位  4位
         */
        private int startDigit = 4;


        public double getIter() {
            return iter;
        }

        public NumberRule setIter(double iter) {
            this.iter = iter;
            return this;
        }

        public SerialOrder getOrder() {
            return order;
        }

        public NumberRule setOrder(SerialOrder order) {
            this.order = order;
            return this;
        }

        public int getStartDigit() {
            return startDigit;
        }

        public NumberRule setStartDigit(int startDigit) {
            this.startDigit = startDigit;
            return this;
        }

        public String getStartNumber() {
            return startNumber;
        }

        public NumberRule setStartNumber(String startNumber) {
            this.startNumber = startNumber;
            return this;
        }
    }


    public NodeValue getValue() {
        return value;
    }

    public void setValue(NodeValue value) {
        this.value = value;
    }

    public TimeRule getTimeRule() {
        return timeRule;
    }

    public Node setTimeRule(TimeRule timeRule) {
        this.timeRule = timeRule;
        return this;
    }

    public NumberRule getNumberRule() {
        return numberRule;
    }

    public Node setNumberRule(NumberRule numberRule) {
        this.numberRule = numberRule;
        return this;
    }
}
