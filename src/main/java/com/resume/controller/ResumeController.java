package com.resume.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.resume.model.Resume;
import com.resume.service.ResumeService;
 
@RestController
@EnableAutoConfiguration 
@ComponentScan({"com.resume.service"})
public class ResumeController {
 
    @Autowired
    ResumeService resumeService;  
    
    @RequestMapping(value = "/resume/", method = RequestMethod.POST)
    public ResponseEntity<Void> createResume(@RequestBody Resume resume, UriComponentsBuilder ucBuilder) {
 
        if (resumeService.isResumeExist(resume)) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        resumeService.createResume(resume);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/resume/{id}").buildAndExpand(resume.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    @RequestMapping(value = "/resume/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Resume> updateResume(@PathVariable("id") long id, @RequestBody Resume resume) {
         
        Resume currentResume = resumeService.findById(id);
         
        if (currentResume==null) {
            return new ResponseEntity<Resume>(HttpStatus.NOT_FOUND);
        }
 
        currentResume.setFirstName(resume.getFirstName());
        currentResume.setSecondName(resume.getSecondName());
        currentResume.setAdress(resume.getAdress());
        currentResume.setEmail(resume.getEmail());
        currentResume.setNote(resume.getNote());
        
        resumeService.updateResume(currentResume);
        return new ResponseEntity<Resume>(currentResume, HttpStatus.OK);
    }
 
    @RequestMapping(value = "/resume/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Resume> deleteResume(@PathVariable("id") long id) {
 
        Resume resume = resumeService.findById(id);
        if (resume == null) {
            return new ResponseEntity<Resume>(HttpStatus.NOT_FOUND);
        }
 
        resumeService.deleteResumeById(id);
        return new ResponseEntity<Resume>(HttpStatus.NO_CONTENT);
    }

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ResumeController.class, args);
	}

}
