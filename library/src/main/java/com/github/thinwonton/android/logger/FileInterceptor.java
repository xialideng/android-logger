package com.github.thinwonton.android.logger;

import android.text.format.DateFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by hugo on 2016/3/19.
 */
public class FileInterceptor implements Interceptor {

    private Executor runner = Executors.newSingleThreadExecutor();
    private boolean append;
    private FileCreator creator;
    private int level = LogLevel.FULL;

    public FileInterceptor(FileCreator creator, int level, boolean append) {
        this.creator = creator;
        this.level = level;
        this.append = append;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void log(final int type, final String tag, final String[] caller, final String message) {
        runner.execute(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                try {
                    String filePath = creator.getDirPath() + File.separator + creator.getFileName();
                    if (tryToCreateFile(filePath)) {
                        File file = new File(filePath);
                        fos = new FileOutputStream(file, append);
                        String om = makeOutputMessage(level, tag, caller, message);
                        fos.write(om.getBytes("UTF-8"));
                        fos.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * 消息輸出的格式 <br/>
     * "2016-03-03 01:01:01.321 D/Tag callerInfos" <br/>
     * "message"
     */
    private final String FORMAT = "%s %s/%s %s" + Helper.LINE_SEPARATOR + "%s";

    protected String makeOutputMessage(int type, String tag, String[] caller, String message) {
        String time = DateFormat.format("yyyy-MM-dd HH:mm:ss.SSS", System.currentTimeMillis()).toString();
        String callerInfos = Helper.makeCallerInfos(caller);
        String levelName = getLevelName(level);
        return String.format(FORMAT, time, levelName, tag, callerInfos, message);
    }

    /**
     * 根据level输出具体的名字
     *
     * @param level
     * @return
     */
    private String getLevelName(int type) {
        String name;
        switch (type) {
            case LogLevel.VERBOSE:
                name = "V";
                break;
            case LogLevel.DEBUG:
                name = "D";
                break;
            case LogLevel.INFO:
                name = "I";
                break;
            case LogLevel.WARN:
                name = "W";
                break;
            case LogLevel.ERROR:
                name = "E";
                break;
            case LogLevel.ASSERT:
                name = "A";
                break;
            default:
                name = "UNKOWN";
                break;
        }
        return name;
    }

    private boolean tryToCreateFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return file.exists();
    }

    public static class Builder {
        private FileCreator creator;
        private int level;
        private boolean append;

        public void fileCreator(FileCreator creator) {
            this.creator = creator;
        }

        public void level(int level) {
            this.level = level;
        }

        /**
         * 是否以追加方式写文件
         *
         * @param append
         */
        public void append(boolean append) {
            this.append = append;
        }

        public FileInterceptor build() {
            return new FileInterceptor(this.creator, this.level, this.append);
        }
    }
}
