package com.mayabot.nlp.segment.utils;

import com.google.common.base.Joiner;
import com.mayabot.nlp.segment.MynlpTokenizer;
import org.junit.Assert;

public class TokenizerTestHelp {

    /**
     * 测试分词器
     * 输入文本的格式  你好|世界
     * 输入分词器是会把|去除掉
     *
     * @param tokenizer
     * @param text
     * @return
     */
    public static void test(
            MynlpTokenizer tokenizer,
            String text) {

        text = text.trim();

        String input = text.replace("|", "");

        String out = Joiner.on("|").join(tokenizer.parse(input).asStringList());

        Assert.assertTrue("Out is " + out + " ,Input " + text, text.equalsIgnoreCase(out));
    }
}
