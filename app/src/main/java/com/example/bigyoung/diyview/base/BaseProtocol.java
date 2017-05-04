package com.example.bigyoung.diyview.base;

import com.example.bigyoung.diyview.utils.Constants;
import com.example.bigyoung.diyview.utils.FileUtils;
import com.example.bigyoung.diyview.utils.HttpUtils;
import com.example.bigyoung.diyview.utils.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by BigYoung on 2017/4/26.
 */

public abstract class BaseProtocol<ResultBean> {
    private ResultBean mResultBean;
    private final String DIRNAME = "jsonNet";//存放从net端获取的数据的文件夹名

    public ResultBean loadData(int index) throws IOException {
/*        if (index > 0)
            throw new IOException();*/
        //try get data from memory
        mResultBean = loadDataFromMemory(index);
        if (mResultBean != null)
            return mResultBean;
        //try get data from file
        mResultBean = loadDataFromLocal(index);
        if (mResultBean != null)
            return mResultBean;
        //从server端获取数据
        return loadDataFromNet(index);
    }

    //try get data from memory
    private ResultBean loadDataFromMemory(int index) {
        MyApplication application = (MyApplication) MyApplication.getContext();
        Map<String, String> jsonStringCacheMap = application.getJsonStringCacheMap();
        String keyJson = getInterfaceKey(index);
        String jsonString = jsonStringCacheMap.get(keyJson);
        if (jsonString == null) {
            return null;
        }
        ResultBean resultBean = parseJsonString(jsonString);
        return resultBean;
    }

    /**
     * 从本地获取数据
     *
     * @param index
     * @return
     */
    private ResultBean loadDataFromLocal(int index) {
        //try get data form file
        File cacheFile = getCacheFile(index);//获得目标的文件
        if (cacheFile.exists() == false) {//文件不存在
            return null;
        }
        BufferedReader bufReader = null;
        String jsonString = null;
        try {
            bufReader = new BufferedReader(new FileReader(cacheFile));
            String oldTime = bufReader.readLine();
            Long lastTime = Long.parseLong(oldTime);//上一次写入时间
            //检测是否失效
            if ((System.currentTimeMillis() - lastTime) > Constants.SavingFilePeriod) {
                return null;
            }
            //获取存储值
            jsonString = bufReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(bufReader);
        }
        ResultBean resultBean = parseJsonString(jsonString);
        if (resultBean != null)
            saveDataInMemory(index, jsonString);
        return resultBean;
    }

    /**
     * 将目标数据优先保存在本地sd卡上
     */
    private void saveDataToLocal(int index, String responseJson) {
        File cacheFile = getCacheFile(index);//获得待保存的文件
        BufferedWriter writer = null;
        try {
            if (cacheFile.exists() == false)
                cacheFile.createNewFile();
            writer = new BufferedWriter(new FileWriter(cacheFile));
            //第一行记录当前时间
            writer.write(System.currentTimeMillis() + "");
            writer.newLine();
            //保存响应文件
            writer.write(responseJson);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    /**
     * 获取目标文件
     *
     * @param index
     * @return
     */
    private File getCacheFile(int index) {
        String dir = FileUtils.getDir(DIRNAME);
        String fileName = getInterfaceKey(index);
        return new File(dir, fileName);
    }

    /**
     * 从web端获取数据
     *
     * @param index
     * @return
     * @throws IOException
     */
    private ResultBean loadDataFromNet(int index) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //拼接要访问的URL
        String url = getUrlCategory();
        //设置参数对应map
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("index", index);
        //转为urlParams
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(mapParams);
        //拼接
        url = url + "?" + urlParamsByMap;
        //创建request请求对象
        Request request = new Request.Builder().get().url(url).build();
        //执行请求
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            //请求成功
            String jsonString = response.body().string();
            //保存在内存里
            saveDataInMemory(index, jsonString);

            //保存在file
            saveDataToLocal(index, jsonString);
            //json 解析
            mResultBean = parseJsonString(jsonString);
            return mResultBean;
        } else {
            //抛出异常
            throw new IOException();
            //return null;
        }
    }

    //保存在内存里
    private void saveDataInMemory(int index, String jsonString) {
        MyApplication application = (MyApplication) MyApplication.getContext();
        Map<String, String> jsonStringCacheMap = application.getJsonStringCacheMap();
        jsonStringCacheMap.put(getInterfaceKey(index), jsonString);
    }

    /**
     * 获得url的category部分,子类实现后基本不变
     *
     * @return
     */
    public abstract String getUrlCategory();

    /**
     * 处理jsonString，转为目标数据,子类实现后基本不变
     *
     * @param jsonString
     * @return
     */
    public abstract ResultBean parseJsonString(String jsonString);

    /**
     * 获得目标文件的文件名
     *
     * @return
     */
    public String getInterfaceKey(int index) {
        return getInterfaceKeyPre() + "_" + index;
    }

    /**
     * 获取InterfaceKey的前缀
     *
     * @return
     */
    public abstract String getInterfaceKeyPre();
}
