package wmlove.icollege;


import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wmlove on 2016/10/24.
 */
public class TestRegex {
    @Test
    public void RegexTest()
    {
        String str = "周三第3,4,5节{第1-8周}";
        String timePattern = null;
            timePattern = "周(.*)第(.*)节.第(.*)-(.*)周(.*)}";

        Pattern r = Pattern.compile(timePattern);
        Matcher matcher = r.matcher(str);
        if (matcher.find())
        {
            for (int i = 1 ; i <= 5 ; i++)
            {
                String strs = matcher.group(i);
                if (strs.length() == 0)
                    strs = "?";
                System.out.println(strs);
            }
        }
    }
}
