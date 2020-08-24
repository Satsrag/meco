package com.zvvnmod.meco.translate.letter.from.delehi;

import com.zvvnmod.meco.translate.annotation.Rule;
import com.zvvnmod.meco.translate.enumeration.CodeType;
import com.zvvnmod.meco.translate.letter.from.LetterTranslateRuleFrom;
import com.zvvnmod.meco.translate.word.MglUnicodeBlock;
import com.zvvnmod.meco.translate.word.Nature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * AUTHOR: zorigt
 * DATE  : 2020/8/10
 * WEEK  : 周一
 * TIME  : 19:32
 */
@Component
@Rule(CodeType.Delehi)
public class DelehiTranslateRuleFrom implements LetterTranslateRuleFrom {

    @Override
    public String getMapperCode(List<Character> pre, String s, Nature nature) {
        String result = resolveDevsgerI(pre, s);
        if (result != null) {
            return result;
        }
        result = FromDelehiCodeMapper.mapper.get(s);
        if (result != null) {
            return result;
        }
        if (nature.equals(Nature.CHAGH)) {
            return FromDelehiCodeMapper.chaghMapper.get(s);
        } else {
            return FromDelehiCodeMapper.hundiiMapper.get(s);
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
        for (char c1 : FromDelehiCodeMapper.doubleIEhishig) {
            if (c1 == c) {
                return "\ue006\ue006";
            }
        }
        return null;
    }

    @Override
    public boolean contains(String s) {
        if (FromDelehiCodeMapper.mapper.containsKey(s)) {
            return true;
        }
        return FromDelehiCodeMapper.chaghMapper.containsKey(s);
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