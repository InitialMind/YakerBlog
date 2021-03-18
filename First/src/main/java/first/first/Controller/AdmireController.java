package first.first.Controller;

import first.first.Enum.AdmireStatusEnum;
import first.first.Enum.AdmireTypeEnum;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.AdmireForm;
import first.first.Service.AdmireService;
import first.first.VO.AdmireVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 14:41
 */
@Controller
@RequestMapping("admire")
public class AdmireController {
    @Autowired
    private AdmireService admireService;

    @GetMapping(value = "/article/index")
    public String article_index() {
        return "thymeleaf/admire/article_index";
    }

    @GetMapping(value = "/comment/index")
    public String comment_index() {
        return "thymeleaf/admire/comment_index";
    }

    @GetMapping(value = "/reply/index")
    public String reply_index() {
        return "thymeleaf/admire/reply_index";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        String type = httpServletRequest.getParameter("type");
        PageRequest request = PageRequest.of(pageNum - 1, size);
        if (type.equals("article")) {
            return AdmireVO.getResult(admireService.findByTypeAndStatus(AdmireTypeEnum.ARTICLE.getType(), AdmireStatusEnum.YES.getStatus(), request));
        } else if (type.equals("comment")) {
            return AdmireVO.getResult(admireService.findByTypeAndStatus(AdmireTypeEnum.COMMENT.getType(), AdmireStatusEnum.YES.getStatus(), request));
        } else {
            return AdmireVO.getResult(admireService.findByTypeAndStatus(AdmireTypeEnum.REPLY.getType(), AdmireStatusEnum.YES.getStatus(), request));
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    @Transactional
    public boolean del(@RequestParam("id") Integer id) {
        admireService.deleteByAdmireId(id);
        return true;
    }

    @RequestMapping("/choose")
    @ResponseBody
    @Transactional
    public boolean choose(@RequestParam("idList") String idlist) {
        String ids = idlist.substring(1, idlist.length() - 1);
        for (String id : ids.split(",")) {
            admireService.deleteByAdmireId(Integer.parseInt(id));
        }
        return true;
    }
}
