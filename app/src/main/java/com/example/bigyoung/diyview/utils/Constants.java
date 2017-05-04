package com.example.bigyoung.diyview.utils;

/**
 */
public class Constants {
    /*
    LogUtils.LEVEL_ALL:打开日志(显示所有的日志输出)
    LogUtils.LEVEL_OFF:关闭日志(屏蔽所有的日志输出)
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    //file文件的有效时间
    public static final Long SavingFilePeriod=5 * 60 * 1000L;

    //tomcat 服务器地址
    public static final String HOST_URL="http://192.168.31.106:8080/GooglePlayServer";
    //home 分支
    public static final String HOME_CATE=HOST_URL+"/home";
    //game 分支
    public static final String GAME_CATE=HOST_URL+"/game";

    public static final int PAGE_SIZE=20;

}
