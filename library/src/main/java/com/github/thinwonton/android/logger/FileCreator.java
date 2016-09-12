package com.github.thinwonton.android.logger;

/**
 * Created by hugo on 2016/3/19.
 */
public interface FileCreator {
    /**
     * 获取文件名
     * @return
     */
    String getFileName();

    /**
     * 获取日志文件存放的路径
     *
     * @return
     */
    String getDirPath();
}
