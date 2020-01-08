package com.baizhi.chz.controller;

import com.baizhi.chz.entity.Guru;
import com.baizhi.chz.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    @RequestMapping("showAllGuru")
    public List<Guru> showAllGuru(){
        List<Guru> gurus = guruService.selectAll();
        return gurus;
    }
}
