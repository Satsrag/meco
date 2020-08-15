package com.zvvnmod.meco.rule.delehi;

import com.zvvnmod.meco.translate.domain.MglUnicodeBlock;
import com.zvvnmod.meco.translate.domain.Nature;
import com.zvvnmod.meco.translate.domain.TranslateRule;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * AUTHOR: zorigt
 * DATE  : 2020/8/10
 * WEEK  : 周一
 * TIME  : 19:32
 */
public class DelehiRule implements TranslateRule {

    @Override
    public String getMapperCode(List<Character> pre, String s, Nature nature) {
        String ii = resolveDevsgerI(pre, s);
        if (ii != null) {
            return ii;
        }
        String s1 = DlhConvertDrsCodeMapper.mapper.get(s);
        if (s1 != null) {
            return s1;
        }
        if (nature.equals(Nature.CHAGH)) {
            return DlhConvertDrsCodeMapper.chaghMapper.get(s);
        } else {
            return DlhConvertDrsCodeMapper.hundiiMapper.get(s);
        }
    }

    private String resolveDevsgerI(List<Character> pre, String s) {
        if (!s.equals("\u1822")) {
            return null;
        }
        if (CollectionUtils.isEmpty(pre)) {
            return null;
        }
        Character c = pre.get(pre.size() - 1);
        for (char c1 : DlhConvertDrsCodeMapper.doubleIEhishig) {
            if (c1 == c) {
                return "\ue006\ue006";
            }
        }
        return null;
    }

    @Override
    public boolean contains(String s) {
        if (DlhConvertDrsCodeMapper.mapper.containsKey(s)) {
            return true;
        }
        return DlhConvertDrsCodeMapper.chaghMapper.containsKey(s);
    }

    @Override
    public Nature getCodeNature(char c) {
        return MglUnicodeBlock.getCodeNature(c);
    }

    @Override
    public boolean isTranslateCodePoint(char c) {
        return MglUnicodeBlock.isNormalLetter(c) || MglUnicodeBlock.isFreeVariationSelector(c) ||
                MglUnicodeBlock.isVowelSeparator(c) || DelehiCodeBlock.isWordConnector(c);
    }

    @Override
    public boolean isMongolianCodePoint(char c) {
        return MglUnicodeBlock.isNormalLetter(c) || MglUnicodeBlock.isFreeVariationSelector(c) ||
                MglUnicodeBlock.isVowelSeparator(c);
    }
}
