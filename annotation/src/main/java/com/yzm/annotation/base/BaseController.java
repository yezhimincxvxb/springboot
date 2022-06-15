package com.yzm.annotation.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.RequestWrapper;

@Service
public class BaseController {

    @JsonProperty
    private String name;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public void hello(@RequestBody int id) {

    }
}
