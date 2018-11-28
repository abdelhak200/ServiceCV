package com.resume.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.resume.model.Resume;
 
@Component
@Transactional
public class ResumeServiceImpl implements ResumeService{
     
    private static final AtomicLong counter = new AtomicLong();
     
    private static List<Resume> resumes;
     
    static{
        resumes= populateResumes();
    }
 
    public List<Resume> findAllResumes() {
        return resumes;
    }
     
    public Resume findById(long id) {
        for(Resume resume : resumes){
            if(resume.getId() == id){
                return resume;
            }
        }
        return null;
    }
     
    public Resume findByName(String name) {
        for(Resume resume : resumes){
            if(resume.getFirstName().equalsIgnoreCase(name)){
                return resume;
            }
        }
        return null;
    }
     
    public void createResume(Resume resume) {
        resume.setId(counter.incrementAndGet());
        resumes.add(resume);
    }
 
    public void updateResume(Resume resume) {
        int index = resumes.indexOf(resume);
        resumes.set(index, resume);
    }
 
    public void deleteResumeById(long id) {
         
        for (Iterator<Resume> iterator = resumes.iterator(); iterator.hasNext(); ) {
            Resume resume = iterator.next();
            if (resume.getId() == id) {
                iterator.remove();
            }
        }
    }
 
	private static List<Resume> populateResumes() {
		List<Resume> rems = new ArrayList<Resume>();
		rems.add(new Resume(1, "Ali", "Hassan", "Harvard Street,2", "ali.hassan@gmail.com", "java developer"));
		rems.add(new Resume(2, "Mark", "Jhon", "NY Street,22", "mark.jhon@gmail.com", "java developer"));
		return rems;
	}

	public boolean isResumeExist(Resume resume) {
		return findById(resume.getId())!=null;
	}

 
}
