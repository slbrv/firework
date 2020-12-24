package com.firework.Modules.System;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by slava on 05.01.17.
 */

public interface FileIO {
    InputStream loadAsset(String assetName) throws IOException;

    InputStream loadFile(String fileName) throws IOException;

    OutputStream writeFile(String fileName) throws IOException;

    String loadText(String name);
}
