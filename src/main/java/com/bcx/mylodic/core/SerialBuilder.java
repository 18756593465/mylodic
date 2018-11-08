package com.bcx.mylodic.core;

import com.bcx.mylodic.Serial;
import com.bcx.mylodic.SerialImpl;

public class SerialBuilder {


    public static Serial  build(){
        return new SerialImpl();
    }

}
