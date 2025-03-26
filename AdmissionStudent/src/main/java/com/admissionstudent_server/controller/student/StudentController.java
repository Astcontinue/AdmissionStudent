package com.admissionstudent_server.controller.student;

import com.admissionstudent_common.result.Result;
import com.admissionstudent_pojo.dto.StudentDTO;
import com.admissionstudent_server.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Result addStudentInfo(@RequestBody StudentDTO studentdto) {
        log.info("新增学生信息：{}", studentdto);
        studentService.addStudentInfo(studentdto);
        return Result.success();
    }
}
