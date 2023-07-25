package com.khaling.rest.crud.restfulcrudservices.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {

//    @RequestMapping(method = RequestMethod.GET,path = "/hello-world")
//    public String helloWorld(){
//        return "Hello World";
//    }
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }
    //Spring Boot automatically converts the HelloWorldBean object into a JSON
    // response since the method is annotated with @RestController,
    // indicating that the return value should be treated as the response body.

        // hello-world/path-variable/khaling
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s",name));
    }

}
