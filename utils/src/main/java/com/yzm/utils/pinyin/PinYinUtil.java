package com.yzm.utils.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：拼音工具类
 */
public class PinYinUtil {

    private static Map<String, String> dictionary = new HashMap<>();

    // 加载多音字词典
    static {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    PinYinUtil.class.getResourceAsStream("/data/PinYinTable.txt"), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split("#");
                if (!StringUtils.isEmpty(arr[1])) {
                    String[] sems = arr[1].split("/");
                    for (String sem : sems) {
                        if (!StringUtils.isEmpty(sem)) {
                            dictionary.put(sem, arr[0]);
                        }
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param src 汉字串
     * @return 汉语拼音
     *     
     */
    public static String getFullSpell(String src) {
        if (StringUtils.isEmpty(src)) return "";

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写格式
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 有无音标
        char[] cs = src.toCharArray();
        String[] ss;
        StringBuilder sb = new StringBuilder();
        try {
            for (char c : cs) {
                // 判断是否为汉字字符
                // if(c >= 32 && c <= 125) //ASCII码表范围内直接返回
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                    //如果是单个汉字，不处理，随机分配拼音
                    // 转化为拼音
                    ss = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    // 首字母大写
                    sb.append(ss[0].substring(0, 1).toUpperCase()).append(ss[0].substring(1));
                } else {
                    // 不是汉字不处理
                    sb.append(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 多音字拼音
     *
     * @param src
     * @return
     */
    public static String getPinyin(String src) {
        if (StringUtils.isEmpty(src)) return "";

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] cs = src.toCharArray();
        String[] ss;
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < cs.length; i++) {
                // 判断是否为汉字字符
                // if(t1[i] >= 32 && t1[i] <= 125)//ASCII码表范围内直接返回
                if (String.valueOf(cs[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    // 转化为拼音，多音字有多个拼音
                    ss = PinyinHelper.toHanyuPinyinStringArray(cs[i], format);
                    // 如果是单个汉字，不处理，随机分配拼音
                    if (cs.length == 1) {
                        sb.append(ss[0].substring(0, 1).toUpperCase()).append(ss[0].substring(1));
                        break;
                    }

                    String dic;
                    if (i == cs.length - 1) {
                        dic = String.valueOf(cs[i - 1]) + String.valueOf(cs[i]);
                    } else {
                        dic = String.valueOf(cs[i]) + String.valueOf(cs[i + 1]);
                    }

                    for (String py : ss) {
                        if (py.equals(dictionary.get(dic))) {
                            ss[0] = py;
                            break;
                        }
                    }

                    // 首字母大写
                    sb.append(ss[0].substring(0, 1).toUpperCase()).append(ss[0].substring(1));
                } else {
                    // 不是汉字不处理
                    sb.append(cs[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        dictionary.forEach((key, value) -> System.out.println(key + "=" + value));
        System.out.println(getPinyin("干一行行一行，一行行行行行，一行不行行行不行"));
    }

}
