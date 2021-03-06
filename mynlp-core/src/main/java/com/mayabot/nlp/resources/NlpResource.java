/*
 * Copyright 2018 mayabot.com authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mayabot.nlp.resources;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.mayabot.nlp.utils.CharSourceLineReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 读取的模型是基于文本的。一般一行一个数据。
 * 项目中和外部系统驳接，比如数据库、HDSF
 * @author jimichan
 */
public interface NlpResource {

    InputStream openInputStream() throws IOException;

//    CharSourceLineReader openLineReader() throws IOException;

    default CharSourceLineReader openLineReader() throws IOException {
        return new CharSourceLineReader(new BufferedReader(new InputStreamReader(openInputStream())));
    }

    /**
     * 有很多实现办法。要么对文件或数据进行计算，还有他同名文件 abc.txt 对应一个文件 abc.txt.hash 进行记录
     *
     * @return
     */
    default String hash() {
        ByteSource byteSource = new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return openInputStream();
            }
        };

        try {
            return byteSource.hash(Hashing.murmur3_32()).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
