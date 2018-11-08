package com.bcx.mylodic.Test;

import com.bcx.mylodic.Serial;
import com.bcx.mylodic.core.Configuration;
import com.bcx.mylodic.core.Node;
import com.bcx.mylodic.core.SerialBuilder;
import com.bcx.mylodic.helper.TimeHelper;

public class SerialImplTest {


    public static void main(String[] args) {
        Serial serial = SerialBuilder.build();
//        String id = serial.serial("double",1.2d,0.1,2,SerialOrder.ASC);
//        String ids = serial.serial("defaultId");
//        String id1 = serial.serial("DMS_CODE",true,true,"DMS");
//        String id2 = serial.serial("DMS_ID","yyyy-MM-dd HH:mm:ss",5,"Eqms");



        System.out.println(TimeHelper.getNow());
        boolean end = false;
        for(int i=0;i<100;i++){
            int a= i;
            new Thread(()->{

                Configuration configuration = new Configuration();
                Node node = new Node();
                node.setValue("TMS");
                Node node1 = new Node();
                node1.setValue("black");
                Node node2 = new Node();
                node2.setValue("excel");
                Node node3 = new Node();
                node3.createNumberRule();
                configuration.addNode(node);
                configuration.addNode(node1);
                configuration.addNode(node2);
                configuration.addNode(node3);

                String id = serial.serial("dms_code",configuration);
                System.out.println(id);

            }).start();
        }
    }
}
