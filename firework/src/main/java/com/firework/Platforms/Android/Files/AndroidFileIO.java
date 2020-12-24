package com.firework.Platforms.Android.Files;

import android.content.res.AssetManager;
import android.os.Environment;

import com.firework.Modules.System.FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by slava on 11.01.17.
 */

/**
 * Имплементирующий класс для загрузки ассетов и файлов и их сохранения
 */

public class AndroidFileIO implements FileIO {

    private static AssetManager assetManager = null;
    private String storagePath;

    /**
     * Конструктор
     * @param assetManager - менеджер ассетов
     */

    public AndroidFileIO(AssetManager assetManager) {
        this.storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        AndroidFileIO.assetManager = assetManager;
    }

    /**
     * Возращение менежджера ассетов
     * @return - менеджер ассетов
     */

    public static AssetManager getAssetManager()
    {
        return assetManager;
    }

    /**
     * Загрузка ассетов из папки ассетов
     * @param assetName - название ассета, а также его расположение
     * @return - Входящий поток
     * @throws IOException - может кинуть исключение
     */

    @Override
    public InputStream loadAsset(String assetName) throws IOException {
        return assetManager.open(assetName);
    }

    /**
     * Загрузка файла не из папки с ассетами, а из папки проекта
     * @param fileName - название файла
     * @return - Входящий поток
     * @throws IOException - Может кинуть исключение
     */

    @Override
    public InputStream loadFile(String fileName) throws IOException {
        return new FileInputStream(storagePath + fileName);
    }

    /**
     * Запись файла не в папку ассетов, а в папку проекта
     * @param fileName - название файла
     * @return - выходной поток
     * @throws IOException
     */

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(storagePath + fileName);
    }

    /**
     * Загрузка текстового файла из файла в папке ассетов
     * @param textPath - путь к файлу
     * @return - форматированый текст
     */

    @Override
    public String loadText(String textPath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = loadAsset(textPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
                stringBuilder.append("\r\n");
            }
            if(bufferedReader != null)
                bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
