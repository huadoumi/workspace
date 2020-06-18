package bc;

import cn.com.agree.afa.jcomponent.GlobalErrorHolder;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.AppLogger.LogLevel;
import cn.com.agree.afa.svc.javaengine.BCScript;
import cn.com.agree.afa.svc.javaengine.EndNode;
import cn.com.agree.afa.svc.javaengine.INode;
import cn.com.agree.afa.svc.javaengine.JScript;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaContext;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;
import cn.com.agree.afa.util.ExceptionUtils;
import cn.com.agree.afa.util.future.IFuture;
import cn.com.agree.afa.util.future.IFutureListener;
import java.util.ArrayList;
import java.util.List;
import static cn.com.agree.afa.jcomponent.GlobalErrorHolder.setGlobalError;
import tc.bank.B_Packages;
import tc.platform.P_Communition;
import tc.platform.P_Dict;
import tc.platform.P_HttpCodec;
import tc.platform.P_Json;
import tc.platform.P_JudgmentStatement;
import tc.platform.P_Logger;
import tc.platform.P_Object;

/**
 * 业务组件包名称：BankTrade <br/>
 *
 * 业务组件包描述：BankTrade <br/>
 *
 * @author AFA Compiler <br/>
 * @version 1.0 <br/>
 *
 */
public class BankTrade {

public static class ExceptionHandle extends BCScript {
        private INode startNode;
    
        public ExceptionHandle(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
    
        public ExceptionHandle(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__, JavaDict __CFG__) {
            super(__REQ__, __RSP__, __BUILTIN__, __CFG__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  异常返回处理");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
    
        private class Node1 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ExceptionHandle_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ExceptionHandle_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node3());
                log(LogLevel.valueOf(2), "将默认异常委托到Node3节点");
                return new Node4();
            }    
        }
        
        private class Node3 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ExceptionHandle_Node3 失败结束");
                setExceptionHandler(null);
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node4 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ExceptionHandle_Node4 取全局错误到容器");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:\u88C5\u8F7D\u6240\u8981\u83B7\u53D6\u7684\u5168\u5C40\u9519\u8BEF\u7684JavaDict\u5BB9\u5668=__RSP__");
                    String _arg1_ = "retType";
                    logVar(LogLevel.valueOf(4), "入参1:\u9519\u8BEF\u7C7B\u578B\u5BF9\u5E94\u7684\u5173\u952E\u5B57Key", _arg1_);
                    String _arg2_ = "retCode";
                    logVar(LogLevel.valueOf(4), "入参2:\u9519\u8BEF\u4EE3\u7801\u5BF9\u5E94\u7684\u5173\u952E\u5B57Key", _arg2_);
                    String _arg3_ = "retMsg";
                    logVar(LogLevel.valueOf(4), "入参3:\u9519\u8BEF\u4FE1\u606F\u5BF9\u5E94\u7684\u5173\u952E\u5B57Key", _arg3_);
                    TCResult result = GlobalErrorHolder.putGlobalErrorToDict(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ExceptionHandle_Node4", "取全局错误到容器", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ExceptionHandle_Node4", "取全局错误到容器", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ExceptionHandle_Node4", "取全局错误到容器", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node5 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ExceptionHandle_Node5 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
    
    }
    public static class ServiceStart extends BCScript {
        private INode startNode;
    
        public ServiceStart(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
    
        public ServiceStart(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__, JavaDict __CFG__) {
            super(__REQ__, __RSP__, __BUILTIN__, __CFG__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  服务开始");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
    
        private class Node1 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node12());
                log(LogLevel.valueOf(2), "将默认异常委托到Node12节点");
                return new Node8();
            }    
        }
        
        private class Node4 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node4 容器变量删除");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0:\u8BF7\u6C42\u5BB9\u5668=__REQ__");
                    JavaList _arg1_ = new JavaList("__RCVPCK__");
                    logVar(LogLevel.valueOf(4), "入参1:\u5220\u9664\u5BB9\u5668\u4E2D\u53D8\u91CF\u7684\u952E\u5217\u8868", _arg1_);
                    TCResult result = P_Dict.delete(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceStart_Node4", "容器变量删除", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceStart_Node4", "容器变量删除", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceStart_Node4", "容器变量删除", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node5 流水号赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:\u8BF7\u6C42\u5BB9\u5668=__RSP__");
                    JavaList _arg1_ = new JavaList(new JavaList("交易流水号", __REQ__.getItem("__TRACEID__")));
                    logVar(LogLevel.valueOf(4), "入参1:\u53D8\u91CF\u8D4B\u503C\u5217\u8868\uFF0C\u5982\uFF1A[[\"key1\",value1],[\"key2\",value2],[\"key3\",value3]...]", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceStart_Node5", "流水号赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceStart_Node5", "流水号赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceStart_Node5", "流水号赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node6 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node6 请求容器信息日志");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0:\u65E5\u5FD7\u4FE1\u606F(JavaList/String)=__REQ__");
                    TCResult result = P_Logger.info(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceStart_Node6", "请求容器信息日志", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceStart_Node6", "请求容器信息日志", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceStart_Node6", "请求容器信息日志", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node7 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node7 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node8 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node8 报文类型判断");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("__PT__");
                    logVar(LogLevel.valueOf(4), "入参0:\u6E90\u5339\u914D\u5B57\u7B26\u4E32", _arg0_);
                    String _arg1_ = "SDK";
                    logVar(LogLevel.valueOf(4), "入参1:\u5339\u914D\u6A21\u5F0F1", _arg1_);
                    String _arg2_ = "NATP";
                    logVar(LogLevel.valueOf(4), "入参2:\u5339\u914D\u6A21\u5F0F2", _arg2_);
                    String _arg3_ = "HTTP";
                    logVar(LogLevel.valueOf(4), "入参3:\u5339\u914D\u6A21\u5F0F3", _arg3_);
                    String _arg4_ = "RPC";
                    logVar(LogLevel.valueOf(4), "入参4:\u5339\u914D\u6A21\u5F0F4", _arg4_);
                    String _arg5_ = null;
                    logVar(LogLevel.valueOf(4), "入参5:\u5339\u914D\u6A21\u5F0F5", _arg5_);
                    String _arg6_ = null;
                    logVar(LogLevel.valueOf(4), "入参6:\u5339\u914D\u6A21\u5F0F6", _arg6_);
                    String _arg7_ = null;
                    logVar(LogLevel.valueOf(4), "入参7:\u5339\u914D\u6A21\u5F0F7", _arg7_);
                    TCResult result = P_JudgmentStatement.switchCaseFrame(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceStart_Node8", "报文类型判断", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceStart_Node8", "报文类型判断", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node9();
                    case 2:
                        return new Node10();
                    case 3:
                        return new Node11();
                    case 4:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceStart_Node8", "报文类型判断", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node9 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node9 SDK拆包");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __REQ__.getItem("__RCVPCK__");
                    logVar(LogLevel.valueOf(4), "入参0:\u6E90\u6570\u636E", _arg0_);
                    JavaDict _arg1_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参1:\u5BB9\u5668=__REQ__");
                    TCResult result = B_Packages.A_parseKV(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceStart_Node9", "SDK拆包", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceStart_Node9", "SDK拆包", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceStart_Node9", "SDK拆包", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node10 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node10 NATP拆包");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0:\u8BF7\u6C42\u5BB9\u5668=__REQ__");
                    String _arg1_ = "UTF-8";
                    logVar(LogLevel.valueOf(4), "入参1:\u5B57\u7B26\u7F16\u7801", _arg1_);
                    TCResult result = P_Communition.natpUnPack(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceStart_Node10", "NATP拆包", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceStart_Node10", "NATP拆包", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceStart_Node10", "NATP拆包", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node11 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node11 HTTP请求报文体拆包");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0:\u8BF7\u6C42\u5BB9\u5668=__REQ__");
                    String _arg1_ = "UTF-8";
                    logVar(LogLevel.valueOf(4), "入参1:\u7F16\u7801\u65B9\u5F0F", _arg1_);
                    TCResult result = P_HttpCodec.unpackRequestBody(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceStart_Node11", "HTTP请求报文体拆包", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node12());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceStart_Node11", "HTTP请求报文体拆包", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node4();
                    default:
                        return getExceptionHandler(new Node12());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceStart_Node11", "HTTP请求报文体拆包", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node12());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node12 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceStart_Node12 失败结束");
                setExceptionHandler(null);
                return EndNode.EXCEPTION_END;
            }    
        }
        
    
    }
    public static class ServiceEnd extends BCScript {
        private INode startNode;
    
        public ServiceEnd(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
    
        public ServiceEnd(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__, JavaDict __CFG__) {
            super(__REQ__, __RSP__, __BUILTIN__, __CFG__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  服务结束");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
    
        private class Node1 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node3());
                log(LogLevel.valueOf(2), "将默认异常委托到Node3节点");
                return new Node4();
            }    
        }
        
        private class Node3 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node3 失败结束");
                setExceptionHandler(null);
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node4 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node4 报文类型判断");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = __REQ__.getItem("__PT__");
                    logVar(LogLevel.valueOf(4), "入参0:\u6E90\u5339\u914D\u5B57\u7B26\u4E32", _arg0_);
                    String _arg1_ = "SDK";
                    logVar(LogLevel.valueOf(4), "入参1:\u5339\u914D\u6A21\u5F0F1", _arg1_);
                    String _arg2_ = "NATP";
                    logVar(LogLevel.valueOf(4), "入参2:\u5339\u914D\u6A21\u5F0F2", _arg2_);
                    String _arg3_ = "HTTP";
                    logVar(LogLevel.valueOf(4), "入参3:\u5339\u914D\u6A21\u5F0F3", _arg3_);
                    String _arg4_ = "RPC";
                    logVar(LogLevel.valueOf(4), "入参4:\u5339\u914D\u6A21\u5F0F4", _arg4_);
                    String _arg5_ = null;
                    logVar(LogLevel.valueOf(4), "入参5:\u5339\u914D\u6A21\u5F0F5", _arg5_);
                    String _arg6_ = null;
                    logVar(LogLevel.valueOf(4), "入参6:\u5339\u914D\u6A21\u5F0F6", _arg6_);
                    String _arg7_ = null;
                    logVar(LogLevel.valueOf(4), "入参7:\u5339\u914D\u6A21\u5F0F7", _arg7_);
                    TCResult result = P_JudgmentStatement.switchCaseFrame(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_, _arg5_, _arg6_, _arg7_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node4", "报文类型判断", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceEnd_Node4", "报文类型判断", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    case 2:
                        return new Node9();
                    case 3:
                        return new Node11();
                    case 4:
                        return new Node14();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node4", "报文类型判断", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node5 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node5 javaDict转换格式化json串");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:\u5B57\u5178=__RSP__");
                    TCResult result = P_Json.dictToFormattedStr(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node5", "javaDict转换格式化json串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("ServiceEnd_Node5", "javaDict转换格式化json串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node3());
                        }
                        __RSP__.setItem("jsonStr", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0:\u683C\u5F0F\u4F18\u7F8E\u7684json\u5B57\u7B26\u4E32", outputParams.get(0));
                    }
                	gatherStat("ServiceEnd_Node5", "javaDict转换格式化json串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node6();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node5", "javaDict转换格式化json串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node6 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node6 SDK拼包");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:\u8BF7\u6C42\u5BB9\u5668=__RSP__");
                    JavaList _arg1_ = new JavaList(new JavaList("__SNDPCK__", __RSP__.getItem("jsonStr")));
                    logVar(LogLevel.valueOf(4), "入参1:\u53D8\u91CF\u8D4B\u503C\u5217\u8868\uFF0C\u5982\uFF1A[[\"key1\",value1],[\"key2\",value2],[\"key3\",value3]...]", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node6", "SDK拼包", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceEnd_Node6", "SDK拼包", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node6", "SDK拼包", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node7 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node7 返回容器信息日志");
                startTime = System.currentTimeMillis();
                try {
                    Object _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:\u65E5\u5FD7\u4FE1\u606F(JavaList/String)=__RSP__");
                    TCResult result = P_Logger.info(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node7", "返回容器信息日志", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceEnd_Node7", "返回容器信息日志", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node8();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node7", "返回容器信息日志", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node8 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node8 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
        private class Node9 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node9 NATP拼包");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:natp\u62FC\u5305\u5BB9\u5668=__RSP__");
                    String _arg1_ = __REQ__.getItem("__MC__");
                    logVar(LogLevel.valueOf(4), "入参1:\u5E94\u7528\u4EE3\u7801", _arg1_);
                    String _arg2_ = __REQ__.getItem("__TC__");
                    logVar(LogLevel.valueOf(4), "入参2:\u4EA4\u6613\u4EE3\u7801", _arg2_);
                    String _arg3_ = "UTF-8";
                    logVar(LogLevel.valueOf(4), "入参3:\u5B57\u7B26\u7F16\u7801", _arg3_);
                    TCResult result = P_Communition.natpPack(_arg0_, _arg1_, _arg2_, _arg3_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node9", "NATP拼包", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceEnd_Node9", "NATP拼包", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node9", "NATP拼包", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node10 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node10 HTTP响应报文拼包");
                startTime = System.currentTimeMillis();
                try {
                    String _arg0_ = "200";
                    logVar(LogLevel.valueOf(4), "入参0:\u54CD\u5E94\u72B6\u6001\u4EE3\u7801 \uFF0C\u5982\"200\",\"502\"", _arg0_);
                    JavaDict _arg1_ = __REQ__.getItem("httpHeader");
                    logVar(LogLevel.valueOf(4), "入参1:\u5934\u90E8\u4FE1\u606F\u5B57\u5178", _arg1_);
                    String _arg2_ = __RSP__.getItem("jsonStr");
                    logVar(LogLevel.valueOf(4), "入参2:\u62A5\u6587\u5185\u5BB9", _arg2_);
                    String _arg3_ = "UTF-8";
                    logVar(LogLevel.valueOf(4), "入参3:\u7F16\u7801,\u9ED8\u8BA4\u4F7F\u7528UTF-8", _arg3_);
                    String _arg4_ = "HTTP/1.1";
                    logVar(LogLevel.valueOf(4), "入参4:http\u62A5\u6587\u7248\u672C", _arg4_);
                    TCResult result = P_HttpCodec.packResponse(_arg0_, _arg1_, _arg2_, _arg3_, _arg4_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node10", "HTTP响应报文拼包", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("ServiceEnd_Node10", "HTTP响应报文拼包", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node3());
                        }
                        __RSP__.setItem("__SNDPCK__", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0:HTTP\u54CD\u5E94\u62A5\u6587\uFF0C\u8BF7\u8BBE\u7F6E\u5230 __RSP__[\"__SNDPCK__\"]\uFF0C\u4EE5\u4FBF\u8FD4\u56DE\u7ED9\u5BA2\u6237\u7AEF\u3002", outputParams.get(0));
                    }
                	gatherStat("ServiceEnd_Node10", "HTTP响应报文拼包", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node10", "HTTP响应报文拼包", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node11 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node11 javaDict转换格式化json串");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:\u5B57\u5178=__RSP__");
                    TCResult result = P_Json.dictToFormattedStr(_arg0_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node11", "javaDict转换格式化json串", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                    List<?> outputParams = result.getOutputParams();
                    if (outputParams != null) {
                        if (outputParams.size() != 1) {
                            log(LogLevel.ERROR, "出参的实参个数与形参个数不一致");
                        	gatherStat("ServiceEnd_Node11", "javaDict转换格式化json串", startTime, "出参的实参个数与形参个数不一致");
                            return getExceptionHandler(new Node3());
                        }
                        __RSP__.setItem("jsonStr", outputParams.get(0));
                        logVar(LogLevel.valueOf(4), "出参0:\u683C\u5F0F\u4F18\u7F8E\u7684json\u5B57\u7B26\u4E32", outputParams.get(0));
                    }
                	gatherStat("ServiceEnd_Node11", "javaDict转换格式化json串", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node13();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node11", "javaDict转换格式化json串", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node12 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node12 HTTP头字典赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__.getItem("httpHeader");
                    logVar(LogLevel.valueOf(4), "入参0:\u8BF7\u6C42\u5BB9\u5668", _arg0_);
                    JavaList _arg1_ = new JavaList(new JavaList("Content-Type", "text/html;charset=UTF-8"), new JavaList("Content-Language", "zh-CN"));
                    logVar(LogLevel.valueOf(4), "入参1:\u53D8\u91CF\u8D4B\u503C\u5217\u8868\uFF0C\u5982\uFF1A[[\"key1\",value1],[\"key2\",value2],[\"key3\",value3]...]", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node12", "HTTP头字典赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceEnd_Node12", "HTTP头字典赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node10();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node12", "HTTP头字典赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node13 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node13 创建HTTP请求头字典");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __REQ__;
                    log(LogLevel.valueOf(4), "入参0:\u6570\u636E\u5B57\u5178\u5BB9\u5668=__REQ__");
                    JavaList _arg1_ = new JavaList(new JavaList("httpHeader", new JavaDict()));
                    logVar(LogLevel.valueOf(4), "入参1:\u6570\u636E\u5BF9\u8C61\u63CF\u8FF0list", _arg1_);
                    TCResult result = P_Object.createObject(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node13", "创建HTTP请求头字典", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceEnd_Node13", "创建HTTP请求头字典", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node12();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node13", "创建HTTP请求头字典", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node14 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "ServiceEnd_Node14 RPC拼包");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:\u8BF7\u6C42\u5BB9\u5668=__RSP__");
                    JavaList _arg1_ = new JavaList(new JavaList("__SNDPCK__", __RSP__));
                    logVar(LogLevel.valueOf(4), "入参1:\u53D8\u91CF\u8D4B\u503C\u5217\u8868\uFF0C\u5982\uFF1A[[\"key1\",value1],[\"key2\",value2],[\"key3\",value3]...]", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("ServiceEnd_Node14", "RPC拼包", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("ServiceEnd_Node14", "RPC拼包", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node7();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("ServiceEnd_Node14", "RPC拼包", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
    
    }
    public static class SuccessHandle extends BCScript {
        private INode startNode;
    
        public SuccessHandle(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__) {
            super(__REQ__, __RSP__, __BUILTIN__);
        }
    
        public SuccessHandle(JavaDict __REQ__, JavaDict __RSP__, JavaDict __BUILTIN__, JavaDict __CFG__) {
            super(__REQ__, __RSP__, __BUILTIN__, __CFG__);
        }
        
        @Override
        public INode execute() {
            if (startNode == null) {
                startNode = new Node1();
                log(LogLevel.INFO, "开始运行业务组件  正常返回处理");
            }
            
            INode node = startNode;
            while (canExecute(node)) {
                node = node.execute();
            }
            
            return node;
        }
    
        private class Node1 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "SuccessHandle_Node1 开始");
                return new Node2();
            }    
        }
        
        private class Node2 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "SuccessHandle_Node2 默认逻辑错误委托");
                setExceptionHandler(new Node3());
                log(LogLevel.valueOf(2), "将默认异常委托到Node3节点");
                return new Node4();
            }    
        }
        
        private class Node3 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "SuccessHandle_Node3 失败结束");
                setExceptionHandler(null);
                return EndNode.EXCEPTION_END;
            }    
        }
        
        private class Node4 implements INode {
            private long startTime;
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "SuccessHandle_Node4 成功消息赋值");
                startTime = System.currentTimeMillis();
                try {
                    JavaDict _arg0_ = __RSP__;
                    log(LogLevel.valueOf(4), "入参0:\u8BF7\u6C42\u5BB9\u5668=__RSP__");
                    JavaList _arg1_ = new JavaList(new JavaList("retType", "SUCCESS"), new JavaList("retCode", "00000"), new JavaList("retMsg", "交易成功！"));
                    logVar(LogLevel.valueOf(4), "入参1:\u53D8\u91CF\u8D4B\u503C\u5217\u8868\uFF0C\u5982\uFF1A[[\"key1\",value1],[\"key2\",value2],[\"key3\",value3]...]", _arg1_);
                    TCResult result = P_Dict.setValue(_arg0_, _arg1_);
                    if (result == null) {
                        log(LogLevel.ERROR, "技术组件返回值不能为空");
                    	gatherStat("SuccessHandle_Node4", "成功消息赋值", startTime, "技术组件返回值不能为空");
                        return getExceptionHandler(new Node3());
                    }
                    
                    int status = result.getStatus();
                    log(LogLevel.valueOf(2), "逻辑返回值=" + status);
                    
                    if (result.getErrorCode() != null || result.getErrorMsg() != null) {
                        setGlobalError("D", result.getErrorCode(), result.getErrorMsg());
                    }
                
                	gatherStat("SuccessHandle_Node4", "成功消息赋值", status, startTime);
                    switch (status) {
                    case 1:
                        return new Node5();
                    default:
                        return getExceptionHandler(new Node3());
                    }
                } catch (Throwable e) {
                	gatherStat("SuccessHandle_Node4", "成功消息赋值", startTime, ExceptionUtils.toDetailString(e));
                    setGlobalError("E", "ACMP0E001", e.toString());
                    log(LogLevel.ERROR, e);
                    INode exceptionHandler = getExceptionHandler(new Node3());
                    if (exceptionHandler == null) {
                    	throw new RuntimeException(e.getMessage(), e);
                    }
                    return exceptionHandler;
                }
            }    
        }
        
        private class Node5 implements INode {
        
            @Override
            public INode execute() {
                log(LogLevel.valueOf(2), "SuccessHandle_Node5 正常结束");
                return EndNode.NORMAL_END;
            }    
        }
        
    
    }

}     

