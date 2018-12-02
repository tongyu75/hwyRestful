package com.czz.hwy.action.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.version.App;
import com.czz.hwy.service.version.VersionService;
import com.czz.hwy.utils.ConstantUtil;
/**
 * 通过此Action里的接口，与系统进行数据交互
 * 
 * @author 佟士儒
 * @version V1.0
 */
@Controller
public class SystemAction{
    Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private VersionService versionService;

    /**
     * 获取领导APP当前客户端的最新版本信息
     * @param versionCode 当前版本号
     */
    @RequestMapping(value = "/leaderAppVerCode/{version}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> leaderAppVerCode(@PathVariable(value="version") String version, HttpServletRequest request, 
            HttpServletResponse response) {
        // 定义返回结果
        Map<String, Object> map = new HashMap<String, Object>();
        if(version==null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESPONSE_ERR);
            return map;
        }
        // 获得后台最新更新系统版本
        App resultApp=versionService.getLeaderAppVerCode();
        if(resultApp!=null) {
            //判断从移动端获取的版本号是否为最新版本
            if(Integer.parseInt(version) < resultApp.getVersionCode()) {
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", resultApp);
            } else {
                map.put("result", ConstantUtil.FAIL);
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESPONSE_ERR);
        }
        return map;
    }

    
    /**
     * 下载领导APP最新客户端的APP
     * @param versionCode 当前版本号     
     */
    @RequestMapping(value = "/downLeaderApp/{newVersion}", method = RequestMethod.GET)    
    public void downLeaderApp(@PathVariable(value="newVersion") String newVersion, HttpServletRequest request, HttpServletResponse response) throws IOException {    
        OutputStream os = null;
        FileInputStream fis = null;
        String ctxPath = request.getSession().getServletContext().getRealPath("/");
        String downLoadPath = ctxPath + "public/leaderApp/"+"demo2.apk";
        long fileSize = new File(downLoadPath).length();
        if (fileSize > 0) {
            try {
                fis = new FileInputStream(new File(downLoadPath));
                response.setHeader("Accept-Ranges", "bytes");
                String range = request.getHeader("Range");
                // 返回的状态码，默认200，首次下载
                int status = HttpServletResponse.SC_OK;
                // 如果range下载区域为空，则首次下载，
                if (range == null) {
                    range = "bytes=0-";
                } else {
                    // 通过下载区域下载，使用206状态码支持断点续传
                    status = HttpServletResponse.SC_PARTIAL_CONTENT;
                }

                long start = 0, end = 0;
                if (null != range && range.startsWith("bytes=")) {
                    String[] values = range.split("=")[1].split("-");
                    start = Integer.parseInt(values[0]);
                    // 如果服务器端没有设置end结尾，默认取下载全部
                    if (values.length == 1) {
                        end = fileSize;
                    } else {
                        end = Integer.parseInt(values[1]);
                    }

                }
                // 此次数据响应大小
                long responseSize = 0;
                if (end != 0 && end > start) {
                    responseSize = end - start;
                    // 返回当前连接下载的数据大小,也就是此次数据传输大小
                    response.addHeader("Content-Length", "" + (responseSize));
                } else {
                    responseSize = Integer.MAX_VALUE;
                }

                byte[] buffer = new byte[4096];
                // 设置响应状态码
                response.setStatus(status);
                if (status == HttpServletResponse.SC_PARTIAL_CONTENT) {
                    // 设置断点续传的Content-Range传输字节和总字节
                    response.addHeader("Content-Range", "bytes " + start + "-"
                            + (fileSize - 1) + "/" + fileSize);
                }
                // 设置响应客户端内容类型
                response.setContentType("application/x-download");
                // 设置响应客户端头
                String appName = "zwshwy.apk";
                response.addHeader(
                        "Content-Disposition",
                        "attachment;filename="
                                + new String(appName.getBytes("utf-8"),
                                        "ISO8859-1"));
                // 当前需要下载文件的大小
                int needSize = (int) responseSize;
                if (start != 0) {
                    // 跳已经传输过的字节
                    fis.skip(start);
                }
                os = response.getOutputStream();
                while (needSize > 0) {
                    int len = fis.read(buffer);
                    if (needSize < buffer.length) {
                        os.write(buffer, 0, needSize);
                    } else {
                        os.write(buffer, 0, len);
                        // 如果读取文件大小小于缓冲字节大小，表示已写入完，直接跳出
                        if (len < buffer.length) {
                            break;
                        }
                    }
                    // 不断更新当前可下载文件大小
                    needSize -= buffer.length;
                }
            } catch (IOException e) {
                // 不打印下载过程中客户端中断的错误信息
                String simplename = e.getClass().getSimpleName();
                if (!"ClientAbortException".equals(simplename)) {
                    e.printStackTrace();
                }
                return;
            } finally {
                if (fis != null)
                    fis.close();
                if (os != null)
                    os.close();
            }
        }
    }
}
