package wmlove.bistu.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/17.
 */
public class URLFactory {

    /**
     * 为BaseUrl加上指定的Action
     * @param baseURL 域名
     * @param actionURL actionName
     * @return URL
     */
    public static String appendAction(String baseURL,String actionURL)
    {
        return String.format("%s/%s",baseURL,actionURL);
    }

    /**
     * 为url加上参数
     * @param url URL
     * @param map Map<String,String> 参数
     * @return URL
     */
    public static String appendParams(String url, Map<String,String> map)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        sb.append("?");

        int length = map.size();

        for(Map.Entry<String,String> entry : map.entrySet())
        {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            if (length > 1)
            {
                sb.append("&");
            }
            length--;
        }
        return sb.toString();
    }
}
