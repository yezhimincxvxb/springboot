package com.yzm.utils.excel;

import com.google.common.collect.Lists;
import com.yzm.utils.excel.entity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("easyPoi")
public class EasyPoiController {

    @GetMapping(path = "excel")
    public void excel() {
        Student student = new Student("100", "13232502998", "黎明", 1, 9999.9999, new Date(), "公司A", "公司地址A", 50);
        Student student2 = new Student("200", "13232102998", "黄昏", 1, 399.9999, new Date(), "公司B", "公司地址B", 20);
        Student student3 = new Student("330", "13652502998", "宁夏", 0, 998.9999, new Date(), "公司C", "公司地址C", 150);
        System.out.println(EasyPoiUtils.INSTANCE.exportExcel(Student.class, Lists.newArrayList(student, student2, student3)));
    }

    @GetMapping(path = "excelCollection")
    public void excelCollection() {
        Dog dog = new Dog("旺财", 2);
        Dog dog2 = new Dog("小强", 1);
        Dog dog3 = new Dog("狗子", 2);
        StudentA student = new StudentA("100", "鲍不平", Lists.newArrayList(dog, dog3));
        StudentA studentA = new StudentA("220", "晓红", Lists.newArrayList(dog2, dog3));
        StudentA studentB = new StudentA("220", "张三", Lists.newArrayList(dog));
        System.out.println(EasyPoiUtils.INSTANCE.exportExcel(StudentA.class, Lists.newArrayList(student, studentA, studentB)));
    }

    @GetMapping(path = "excelEntity")
    public void excelEntity() {
        StudentB student = new StudentB("001", "张三", new Cat("小米", 2));
        StudentB student2 = new StudentB("011", "李四", new Cat("公举", 1));
        StudentB student3 = new StudentB("023", "王五", new Cat("喵喵", 1));
        System.out.println(EasyPoiUtils.INSTANCE.exportExcel(StudentB.class, Lists.newArrayList(student, student2, student3)));
    }

    @GetMapping(path = "excelTarget")
    public void excelTarget() {
        UserA userA = new UserA("张三", 26);
        System.out.println(EasyPoiUtils.INSTANCE.exportExcel(UserA.class, Lists.newArrayList(userA)));
    }

    @PostMapping("importExcel")
    public void importExcel(HttpServletRequest request, MultipartFile file) {
        List<Student> students = EasyPoiUtils.INSTANCE.importExcel(file, 1, 2, Student.class);
        students.forEach(System.out::println);
    }

}
