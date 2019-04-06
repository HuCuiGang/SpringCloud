package org.yufan.pay.config;

/**
 * Created by zxd on 2019/1/18
 **/
public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091100483312";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDDSIevwwY0eIE81Auh3l6Ge27STac0ExUmLmrjWvReIdMZuRYCt/pE04jA4JvsWzS676TOYZH5EYpmki/txJz0VHnfeWWApNYs3VkiLcI4GUBVrH0JUtaHM911I1FUHxyYqznFM7LiXtHjT5JAlmGSOOTf5OZ/cXnXUOk4rwevYYsZXcX1NtQFbvJG6JbjOqp6i0utcWJxQl+FkltRc+T3xW1D6+B7KepnLlwJUmsxOOXRjOxUMOO/LhRt9PrXNkl0MfpuiMGqunvVkpt+WGnYNJ36xWRT5oYwoKPrpeSz2iTeYQtmRcOhJsYknjiN7NP5th+66GXpghrlk6ZrC+6vAgMBAAECggEBAJ5wVkYwAPTJYHGtyNJHzIFn2/4cqrABW4Ni3a4guWs2HkQcetMx2YfF6Io0sc0OIrYYgKpBV2Gw7Y1Sxjgu8Pj7IPZDVCsPkgVMgWjNn2pMcnoQPFI4N33ygpbrVKzfoR/srC5nR5eKPtgiUxK77+Xm6ysMWEbGFoivSMmImL+dBNF9KHHFRQU56Oe9cW5/IYpUmdZW2I/vHUkR0IaxJUcl+lblo+8S1fht8lq6fxGpSlvco1o46FoGFMKsZlZdMi90VHsALjcUQBqq1nGT2y8OXximLfcxrFgHAyTAK3wYPNmjVDPulvNA5xKh0Ds4+S64wYs87WNaCoeYyHhU6sECgYEA7xDpY8qLlzrNCsCy5amTJXy7iSutzxhDo6UNK88QyTSld5txJ8AAI1UlSj+zO/hy3EAJqBDUlSnUhr4qFATJy0+xRGBTw0+XIOfqkMIIrbTBaFqp2yGNbEm7pjjtFHup0wR5+1HhUD+HZGVQb79kdJIkRLSfpbLTA3VkUJrBBikCgYEA0R2v3OeaeCcYssSYXa7lW4pn4UR2LpM23ae2dbQG4Xw9IPJL3zSmc1bOm3Yr9CL9osgTg8hHoWYnxtdwk1fnr2bugLaeBbJshW/AQ3L79TS3mrxpOvt2uPx3gwYwxZtevzDGlaOkH7bv92fKQYgjUplrh8VpFRpH4KP+MBTTeRcCgYEAy2ML7IZJNCQmhVtj19fpBB+WPSEYAfSWtwaYqgjEMynDDHsGTdcfRvANhZmkFwVMUu14Sa/n6+9mjCwFCHr6YyotmgqJuMRhwvELspQhZgc99FYN5AwrkVE5gDU2HhMHbCuGy7XhhrBFs7guMqQWBsa+bGVu3T3CIFMjQSAnz+ECgYADHg8uGuHJosOSfq3Kz72plLGhwRSF8znhKgvlS07C88gxgosxA6brl0KzPJrBI/hmAdeQol63AD/djf6mo6vXiJBLqO1IJnXJs3XJS6CH8kY+b0qeT3tgnJrngCMf/wX1kbOULYFX27FOHeT8/xuPsqvQtVewsdiad7dlXhoc9wKBgHOfL1i3QCkVkTFbfIyaLZfRC5qcIoEOPrY1PpHTN2qrw3dq3MizIrykUwCXNFGD6mN+6+j3hPgCfF1x9gbpXYzY0weLcAAqb7kQRj26fpLYAEklRm6rQm/7RP2YtxKtuBWKC2l2ZtpZU31IQBWEVhssIkaA3MuVE6EJDjyjXmQg";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAta3Q1f59n43dXfZhkV+HAGMS7ExpIx7g7awrTOYpz7leCTKLGRBwJWYabFr8gu6T+ho3CpYHsCGT5dNWv5eho7akFA3L+xJzcbqLrygDt4lqCm2I0J4iDG77AYhNJtS5RG6KLCM7oiYVx77TzSuygyaAxBGC61mJCew267DAX9c+/r2l7rXC+U6EyHhoCat0jTOAyZjjQsxy1BgTR3Jc4hufqNOisImDhjA/YrzL9WsNQd440kx+D1a0WD8CU20bArytfRFihv8hyylP8P0dpUykgIoSWhQTzBRHVukogPX2l7Rw45ZC1zh2JdnxWaLBuFxoGgBO5QJ9Fv0jxuY3PwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://jgcgzy.natappfree.cc/callback/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url ="http://jgcgzy.natappfree.cc/callback/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";

}
