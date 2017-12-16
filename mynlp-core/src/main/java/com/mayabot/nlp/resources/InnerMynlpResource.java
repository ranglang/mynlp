package com.mayabot.nlp.resources;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.mayabot.nlp.utils.CharSourceLineReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

public class InnerMynlpResource implements MynlpResource {

    MynlpResource proxy;

    public InnerMynlpResource(Path dataDir, String path) {
        //http://wer
        proxy = load(dataDir, path + ".zip"
        );

        if (proxy == null) {
            proxy = load(dataDir, path);
        }

        Preconditions.checkNotNull(proxy, "Not found " + path);

    }

    private MynlpResource load(Path dataDir, String path) {
        path = path.replace('/', File.separatorChar);
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        File file = dataDir.resolve(path).toFile();

        if (file.exists()) {
            return new FileMynlpResource(file);
        }

        URL urlinclass = null;
        String temp = "maya_data/" + path;
        try {
            urlinclass = MynlpResource.class.getClassLoader().getResource(temp);
        } catch (Exception e) {
        }

        if (urlinclass != null) {
            return new URLMynlpResource(urlinclass, Charsets.UTF_8);
        }

        return null;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return proxy.openInputStream();
    }

    @Override
    public CharSourceLineReader openLineReader() throws IOException {
        return proxy.openLineReader();
    }

    @Override
    public String hash() {
        return proxy.hash();
    }

    @Override
    public String toString() {
        return proxy.toString();
    }
}
