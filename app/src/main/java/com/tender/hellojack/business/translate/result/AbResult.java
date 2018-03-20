package com.tender.hellojack.business.translate.result;

import com.tender.hellojack.model.translate.Result;

/**
 * Created by boyu on 2018/1/31.
 */

public abstract class AbResult implements IResult {
    public Result getResult(){
        return new Result(this);
    }
}
