package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.DAO.NoteDAO;

public class NoteServiceImpl {
	@Autowired
	NoteDAO noteDao;
}
