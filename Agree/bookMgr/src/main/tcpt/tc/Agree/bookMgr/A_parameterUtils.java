package tc.Agree.bookMgr;

import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;
import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Returns;
import java.util.List;
import java.util.Map;

/**
 * 测试
 * 
 * @date 2018-07-17 18:41:34
 */
@ComponentGroup(level = "应用", groupName = "参数处理工具", projectName = "Agree", appName = "bookMgr")
public class A_parameterUtils {

	/**
	 * @category 按渠道交易码提取提示信息
	 * @param channelNo
	 *            入参|渠道编号|{@link java.lang.String}
	 * @param channelTradeCode
	 *            入参|渠道交易码|{@link java.lang.String}
	 * @param reminderInfo
	 *            出参|提示信息|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@galaxy.ide.tech.cpt.Param(name = "channelNo", comment = "渠道编号", type = String.class),
			@galaxy.ide.tech.cpt.Param(name = "channelTradeCode", comment = "渠道交易码", type = String.class) })
	@OutParams(param = { @galaxy.ide.tech.cpt.Param(name = "reminderInfo", comment = "提示信息", type = String.class) })
	@Returns(returns = { @galaxy.ide.tech.cpt.Return(id = "0", desp = "失败"),
			@galaxy.ide.tech.cpt.Return(id = "1", desp = "成功") })
	@Component(label = "按渠道交易码缓存提取提示信息", style = "判断型", type = "同步组件", date = "2018-12-08 09:52:40")
	public static TCResult A_getReminderInfoCacheByTradeCode(String channelNo,
			String channelTradeCode) {
		String reminderInfo = "";
		try {
			String[] remindinfokeys = { "REMINDERINFO" };
			TCResult remindinfoTcr = CacheConvertToListMap(
					"rm_trade_reminderinfo", channelNo + channelTradeCode,
					remindinfokeys);
			if (remindinfoTcr.getStatus() == 0) {
				return TCResult.newFailureResult("RISK006", "该交易未配置提示信息");
			}
			JavaDict remindinfoDict = (JavaDict) remindinfoTcr
					.getOutputParams().get(2);
			reminderInfo = remindinfoDict.getStringItem("REMINDERINFO");

		} catch (Exception e) {
			e.printStackTrace();
			return TCResult.newFailureResult("RISK001",
					"处理失败." + e.getMessage());
		}
		return TCResult.newSuccessResult(reminderInfo);
	}
	/**
	 * 缓存信息转换为List<Map>机构
	 * @param cacheId 缓存Id
	 * @param cachekey 缓存键值
	 * @param keys	    缓存key值
	 * @return		  Dict，List<Map>,Dict(单记录)
	 */
	public static TCResult CacheConvertToListMap(String cacheId,
			String cachekey, String[] keys) {
//		TCResult tc = B_ParamMemory.B_GetCacheData(cacheId, cachekey);
//		if (tc.getStatus() == 0) {
//			return tc;
//		}
//		JavaList keyList = new JavaList();
//		String[] arrayOfString;
//		int j = (arrayOfString = keys).length;
//		for (int i = 0; i < j; i++) {
//			String key = arrayOfString[i];
//			keyList.add(key);
//		}
//		TCResult cacheTCR = B_DataConversion.B_ListListdTOListMap((JavaList) tc
//				.getOutputParams().get(0), keyList);
//
//		Object cacheListMap = (List) cacheTCR.getOutputParams().get(0);
//
//		JavaList dictList = new JavaList();
//		for (Map map : (List<Map>) cacheListMap) {
//			JavaDict dict = new JavaDict();
//			for (Object key : keyList) {
//				dict.put((String) key, map.get((String) key));
//			}
//			dictList.add(dict);
//		}
//		JavaDict singleDict = null;
//		if ((dictList != null) && (dictList.size() == 1)) {
//			singleDict = dictList.getDictItem(0);
//		}
		return TCResult.newSuccessResult();
	}
}
