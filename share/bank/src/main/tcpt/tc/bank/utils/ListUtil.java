package tc.bank.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.agree.afa.svc.javaengine.AppLogger;

/**
 * List工具类
 * 
 * @author AlanMa
 * 
 */
public class ListUtil {

    /**
     * List是否为空
     * 
     * @param outputParams
     * @return
     */
    public static Boolean isNotEmpty(List<?> outputParams) {
        if (outputParams != null && outputParams.size() > 0) {
            if (outputParams.get(0) != null) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }


    /**
     * 打印层级
     * 
     * @param layer
     * @param logInfo
     * @return
     */
    public static String printLayer(int layer, String logInfo) {
        StringBuffer strBuf = new StringBuffer();
        if (0 == layer) {
            return "";
        }
        for (int i = 0; i < layer; i++) {
            strBuf.append("    ");
        }
        strBuf.append("|" + "\n");
        for (int i = 0; i < layer; i++) {
            strBuf.append("    ");
        }
        strBuf.append("+----");
        return strBuf.toString();
    }

}
