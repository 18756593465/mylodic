package com.bcx.mylodic.core;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 序列号配置
 *
 * @author zhanglei
 */
public class Configuration implements Serializable {


    /**
     * 序列规则数组
     */
    private List<Node> nodes = new LinkedList<>();

    /**
     * 序列规则排列序号
     */
    private int index;

    public Configuration(){}


    public Configuration addNode(Node node){
        this.nodes.add(index,node);
        this.index ++;
        return this;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
