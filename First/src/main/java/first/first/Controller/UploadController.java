package first.first.Controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/8/16 15:25
 */
@Controller
@RequestMapping("upload")
public class UploadController {
    @ResponseBody
    @PostMapping("/image")
    public String uploadFile(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        //服务器上使用
        // String rootPath =request.getServletContext().getRealPath("/resource/uploads/");//target的目录
        //本地使用
        String rootPath = "/images/";
        //原始名称
        String originalFilename = file.getOriginalFilename();
        //新的文件名称
        String newFileName = res + originalFilename.substring(originalFilename.lastIndexOf("."));
        //新文件
        File newFile = new File(rootPath + newFileName);
        //判断目标文件所在的目录是否存在
        if (((double) file.getSize() / (1024.0 * 1024.0)) > 1.0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 1);//0表示成功，1失败
            map.put("msg", "图片大小不能超过1M");//提示消息
            return new JSONObject(map).toString();

        }
        if (!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        } else {
        }
        //将内存中的数据写入磁盘
        if (!newFile.exists()) {
            file.transferTo(newFile);
        }
        //完整的url
        String fileUrl = "/images/" + newFileName;
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map.put("code", 0);//0表示成功，1失败
        map.put("msg", "上传成功");//提示消息
        map.put("data", map2);
        map2.put("src", fileUrl);//图片url
        map2.put("title", newFileName);//图片名称，这个会显示在输入框里
        String result = new JSONObject(map).toString();
        return result;

    }


}
