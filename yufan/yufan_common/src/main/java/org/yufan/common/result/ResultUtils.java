package org.yufan.common.result;

public class ResultUtils {

    /**
     * 成功
     * @return
     */
    public static Result buildSuccess(){
      return buildSuccess(ResultEnum.OK,null);
    }

    public static Result buildSuccess(Object data){
        return buildSuccess(ResultEnum.OK,data);
    }

    /**
     * 请求成功
     * @param resultEnum 成功枚举
     * @param data 成功数据
     * @return
     */
    public static Result buildSuccess(ResultEnum resultEnum,Object data){
        Result result=new Result(true,resultEnum.getCode(),resultEnum.getMsg(),data);
        return result;
    }

    /**
     * 构建失败
     * @param resultEnum
     * @return
     */
    public static Result buildFail(ResultEnum resultEnum){
        return new Result(false,resultEnum.getCode(),resultEnum.getMsg());
    }



}
