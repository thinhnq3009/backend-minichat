package com.eco.beminichat.response;

import com.eco.beminichat.response.base.ResponseEnable;
import org.apache.el.parser.BooleanNode;

public class BooleanResponse  implements ResponseEnable {
    boolean value;
    public BooleanResponse(boolean value){
        this.value = value;
    }

    public static BooleanResponse of(boolean value){
        return new BooleanResponse(value);
    }

    public boolean getValue(){
        return value;
    }
}
