package com.resume.service;

import java.util.List;

import com.resume.model.Resume;

public interface ResumeService {
	
	public Resume findById(long id);
    public void createResume(Resume resume);
    public void updateResume(Resume resume);
    public void deleteResumeById(long id);
    public List<Resume> findAllResumes(); 
    public boolean isResumeExist(Resume resume);
	 
}
