package GlobalTools;


import java.io.InputStream;

/**
 * 配置类，用于描述配置文件的存储的位置信息，并返回流文件
 */
public interface Perproties {

    InputStream getSimpleComponentDefineFile();

    void registerToGlobalManager();
}
