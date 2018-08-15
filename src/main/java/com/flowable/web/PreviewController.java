package com.flowable.web;

import com.flowable.modules.expense.domain.Expense;
import com.flowable.modules.user.domain.User;
import com.flowable.modules.user.dto.UserDTO;
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
     * /public/preview?processKey=Test_Expense_4
     * {
     * 	"userId":33,
     * 	"money":1500
     * }
     * @return
     * @throws ObjectNotFoundException
     */
    @PostMapping(value = "/preview", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> previewProcess(@RequestParam String processKey,
                                                     @RequestBody Expense expense) throws ObjectNotFoundException {
        return ResponseEntity.ok(previewService.getPreviewProcessByDmnKey(processKey, expense));
    }

    /**
     * 模拟预览进行中流程
     * @param processKey
     * @param expense
     * /public/preview/running?processKey=Test_Expense_4
     * {
     * 	"userId":33,
     * 	"money":1500
     * }
     * @return
     */
    @PostMapping(value = "/preview/running", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> runningPreviewProcess(@RequestParam String processKey,
                                                               @RequestBody Expense expense) throws ObjectNotFoundException {
        return ResponseEntity.ok(previewService.getRunningPreviewProcessByDmnKey(processKey, expense));
    }
}
