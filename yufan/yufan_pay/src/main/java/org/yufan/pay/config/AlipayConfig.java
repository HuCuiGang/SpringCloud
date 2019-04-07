package org.yufan.pay.config;

/**
 * Created by zxd on 2019/1/18
 **/
public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091100483312";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCikTF/M1qvKU41l3UkI3zWmoBGVrNWHICUNm+TfVx1zO4Pz/znYPCHc9+fB+uHDjsq3j9mpLVfMmSXthqN17vDK+Qem6AOUzNdcGV/MdjkdVfib1fhIKSqRBLkGJEk/8QKU9UuiLJsA2ni0aNFbbdV69GndIloeLP4OwJtxzC9LgbmQw2rTdIT0drrEZYLP0q7lU3z/XwdyNcfGXb9NdHkJD88chulQ89gE7MuFfelgTp0aG4dopc99hhS6dfBprDuuJEU06ZRIjqpi8JzYuDSEtRWVApPrFjyoMdsx3+ayLKUuiVkY9x1hLY4Pmk/vyDVFD6txLfCnXpEW6rQiQbNAgMBAAECggEAVpxMZpyRn+i9xkefHVXa2OsPlGn0udgNmOk3oLiVXh2EZ+BDuXIoM8JETZIGpbmrRDeMR79Q8hsDBOD8XL2nR2BG9ResunURVKLhMFb6Wx05aWYwx7yAfkYuWl/MVI3/gBER7uzdQja/XxyZhA/kMV6mIDUyT/qex42Iybvp95+HpVrHSucfwXQndu3c/WSOOGGbrVGSeluJFVe2tN+beaI790mq0YTsY3qdpg7fYcbvIVaNZ7l/h/rjaSSpqOLqt1aOysdY0g7Nyo6oceJeiGxo8QTRoL2GVu6NJJL56ttVrooJ8gJem3fnX386dacgE00REh0WwIN1AYdvnFmH8QKBgQDgh8b6ZN6ixJAq6WFvsNy7dnBE6zEB+5RwVqi5xNWAGZOVTXzu+CByBKWdXww63crTsfpq7rXr/wiqeuQlSAFiIlViJmeTEC49zVAqWirO1hGMvj2EhU9Fy//fHSgzvO6i4Z3DGnKIgT8CP9uHTkP4E9JIsCpPnxfmBCx7oKEv4wKBgQC5WiffB81lCIxvyTkNIwhZ25idWf9coXEsF5bsWGKLu73+EB18A2RtsIu6ID39D7HR1RHYYLOLWx9v3gEpAXURIqjhFhYthVj+K4f4Z/jdyk12a3BGV/aNcX7FCVLDN1mpVTTH6Iss9uSyL4hCxr1AMvs/32jHPHacZYPYNAhNjwKBgQCO6Z1a7y1OSPLCfX3JS7eTkpVvKIwvCOS4hFAsvf5u5NitJX8p08FDEXYgrsjoBxf8msIbogklR1VKvaHNoQFQZy4VpQAz+Q6Vw8MM0guBT3woK0aYvQlyEaCxcKuQEueJwTVbY9AW1r02P1MJ0mUs1+D25zpnJ4pcm7d8kQdHpQKBgAQsp6E6R8Hyw1bxLTvCgjmj3HgOwtWPPUFE9r0z8GQ9QjYqnr9gRC7EPyFeAIdmhxa2ucQpyWFmF/spANWvlrdgFHWdTbqQukRfLpzFmrFEESKNoOW0rXTaDYTahOaopHhP1vR5pEjK9rOcpnD6kOCVRVtW6MEz92fmUWXZJia5AoGAVX0mftA0Q1NTDM8gxRTcgqgJtCzFZDRp+BWcXBBqysA9S5tKSNuOJYwEydGxxMZSFwrXfCZlbtlFjBxQguS5Ud903EbsppRPLaYbVVRCSfDbFXXmwemkNu/uQ3LEfBY8YUL9m86xT9LiVCZ/pQql26cMjD6W+gZVmCIkTRMfFTw=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAta3Q1f59n43dXfZhkV+HAGMS7ExpIx7g7awrTOYpz7leCTKLGRBwJWYabFr8gu6T+ho3CpYHsCGT5dNWv5eho7akFA3L+xJzcbqLrygDt4lqCm2I0J4iDG77AYhNJtS5RG6KLCM7oiYVx77TzSuygyaAxBGC61mJCew267DAX9c+/r2l7rXC+U6EyHhoCat0jTOAyZjjQsxy1BgTR3Jc4hufqNOisImDhjA/YrzL9WsNQd440kx+D1a0WD8CU20bArytfRFihv8hyylP8P0dpUykgIoSWhQTzBRHVukogPX2l7Rw45ZC1zh2JdnxWaLBuFxoGgBO5QJ9Fv0jxuY3PwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = " http://i7nnm5.natappfree.cc/callback/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url =" http://i7nnm5.natappfree.cc/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";

}
