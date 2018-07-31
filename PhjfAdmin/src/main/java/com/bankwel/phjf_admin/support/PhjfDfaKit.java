package com.bankwel.phjf_admin.support;

import com.bankwel.framework.core.F;
import com.bankwel.framework.core.kit.DFA;
import com.bankwel.framework.core.util.DataLoader;
import com.bankwel.framework.support.codis.RedisClient;
import com.bankwel.phjf_admin.common.constants.admin.AdminConstants;
import com.bankwel.phjf_admin.common.model.core.SysSensitivewordsLibrary;
import com.bankwel.phjf_baseModel.common.model.phjf.enumKey.AdminSysSensitiveWordsLibraryEnum;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2018/1/31.
 */
public class PhjfDfaKit {
    private static DFA dfa = DFA.getInstance();

    private static DFA refleshDFA(final String language){
        return RedisClient.getClient().get(F.strKit.f(AdminSysSensitiveWordsLibraryEnum.CK_Phjf_Model_SysSensitiveWordsLibrary_KEY1.getKey(), language, AdminConstants.PLATFORM)
                , AdminSysSensitiveWordsLibraryEnum.CK_Phjf_Model_SysSensitiveWordsLibrary_KEY1.getTime()
                , new DataLoader(){
                    public Object load() throws  Exception{
                        List<Record> list = SysSensitivewordsLibrary.dao.getAllSensitiveWords(language);
                        Set<String> data = new HashSet<String>();
                        for (Record r : list) {
                            data.add("" + r.get("words"));
                        }
                        dfa.refleshData(data);
                        return dfa;
                    }
                });
    }
    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt 文字
     * @return 若包含返回true，否则返回false
     */
    public static boolean contains(String language, String txt) {
        return refleshDFA(language).contains(txt);


    }

    /**
     * 判断文字是否包含敏感字符,返回敏感字符
     *
     * @param txt 文字
     * @return 若包含返回true和敏感字符，否则返回false
     */
    public static Pair<Boolean, Set<String>> checkSensitiveWord(String language, String txt){
        dfa = refleshDFA(language);
        boolean bool = dfa.contains(txt);
        Set<String> words = null;
        if (bool){
            words = dfa.getSensitiveWord(txt);
        }
        return Pair.of(bool, words);
    }

    public static void main(String[] args){
        String txt = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节。"
                + "然后我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
                + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个贱人一杯红酒一部电影在夜 深人静的晚上，关上电话静静的发呆着。";
        Pair<Boolean, Set<String>> pair = PhjfDfaKit.checkSensitiveWord("ZH_SIMP", txt);
        System.out.println(pair.getLeft()+"       "+pair.getRight());
    }


}
