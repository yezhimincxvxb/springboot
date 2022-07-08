package com.yzm.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


@Slf4j
public class HttpRequestUtil {

    private static final String CHARSET = "UTF-8";

    /**
     * 将Map的key<-->value拼接成字符串
     * key1=value1&key2=value2
     */
    public static String joinMapValue(Map<String, Object> map, char connector) {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            buffer.append(entry.getKey());
            buffer.append('=');
            if (entry.getValue() != null) {
                buffer.append(entry.getValue());
            }
            buffer.append(connector);
        }
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param requestUrl 发送请求的URL
     * @param charset    编码格式
     * @param params     请求参数
     * @return 远程资源的响应结果
     */
    public static String sendGet(String requestUrl, String params, String charset) {
        if (!StringUtils.hasLength(requestUrl)) throw new IllegalArgumentException("Request Url Empty");
        if (StringUtils.hasLength(params)) requestUrl += "?" + params;
        if (!StringUtils.hasLength(charset)) charset = CHARSET;

        String result = "";
        try {
            URL url = new URL(requestUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求属性
            connection.setRequestMethod("GET");// 设置请求方式为post,默认GET请求
            connection.setConnectTimeout(5000);// 连接主机的超时时间
            connection.setReadTimeout(5000);// 从主机读取数据的超时时间
            // 设置通用属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setRequestProperty("Charset", "UTF-8");
            // 建立TCP连接
            connection.connect();

            result = getResult(connection, charset);
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param requestUrl 发送请求的URL
     * @param params     请求参数,请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 远程资源的响应结果
     */
    public static String sendPost(String requestUrl, String params, String charset) {
        return post(requestUrl, params, charset, false);
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param requestUrl 发送请求的URL
     * @param params     请求参数为json格式
     * @return 远程资源的响应结果
     */
    public static String jsonPost(String requestUrl, String params, String charset) {
        return post(requestUrl, params, charset, true);
    }

    private static String post(String requestUrl, String params, String charset, boolean isJson) {
        if (StringUtils.isEmpty(requestUrl)) throw new IllegalArgumentException("Request Url Empty");
        if (StringUtils.isEmpty(charset)) charset = CHARSET;

        String result = "";
        try {
            URL url = new URL(requestUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求属性
            connection.setRequestMethod("POST");// 设置请求方式为post
            connection.setConnectTimeout(5000);// 连接主机的超时时间
            connection.setReadTimeout(5000);// 从主机读取数据的超时时间
            connection.setDoOutput(true);// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,默认情况下是false
            connection.setDoInput(true); // 设置是否从connection读入，默认情况下是true;
            // 设置请求通用属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setRequestProperty("Charset", "UTF-8");
            if (isJson) {
                connection.setRequestProperty("Content-Type", "application/json");
            } else {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            // 建立TCP连接
            connection.connect();

            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            out.close();

            result = getResult(connection, charset);
        } catch (Exception e) {
            log.info("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        return result;
    }

    private static String getResult(HttpURLConnection connection, String charset) throws IOException {
        StringBuffer result = new StringBuffer();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        }
        // 断开与远程地址url的连接
        connection.disconnect();
        return result.toString();
    }

}
