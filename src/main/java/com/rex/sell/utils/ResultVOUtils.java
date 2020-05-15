package com.rex.sell.utils;

import com.rex.sell.VO.ResultVO;

/**
 * @ClassName ResultVOUtils
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/15 23:00
 * @Version 1.0
 */
public class ResultVOUtils {
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg("成功");
        resultVO.setCode(0);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(message);
        resultVO.setCode(code);
        return resultVO;
    }
}
