package com.flowable.web;

import com.flowable.modules.expense.domain.Expense;
import com.flowable.modules.user.domain.User;
import com.flowable.service.PreviewService;
import org.apache.ibatis.javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: liping.zheng
 * @Date: 2018/8/13
 */
@RestController
@RequestMapping("/public")
public class PreviewController {

    @Autowired
    PreviewService previewService;

    /**
     * 模拟预览审批流程
     * @param processKey
     * @param expense
     * @return
     * @throws ObjectNotFoundException
     */
    @PostMapping(value = "/preview", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> previewProcess(@RequestParam String processKey,
                                                     @RequestBody Expense expense) throws ObjectNotFoundException {
        return ResponseEntity.ok(previewService.getPreviewProcessByDmnKey(processKey, expense));
    }
}
