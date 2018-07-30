package com.bridgeit.fundoonotes.notes.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundoonotes.notes.model.Note;
import com.bridgeit.fundoonotes.notes.service.NoteService;

@RestController
public class NotesController 
{

	@Autowired
	private NoteService noteservice;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createnote(HttpServletRequest request ,@RequestBody Note note)
	{
		System.out.println();
       String token  = request.getHeader("key");
       System.out.println(token);
		Note note2 = noteservice.save(token, note);
		System.out.println(note2);
        if(note2!=null)
        {
        	return new ResponseEntity<String>("New Note is created :", HttpStatus.OK);
        }
        else
        {
        	return new ResponseEntity<String>("note is not created:", HttpStatus.OK);
        }
	}
	

   @RequestMapping(value="/delete/{idNote}",method=RequestMethod.DELETE)
   public ResponseEntity<String> deletenote(@PathVariable("idNote") int id,HttpServletRequest request)
   
   {
	   String token  = request.getHeader("key");
	   
       System.out.println(token);
       if(noteservice.delete(token, id))
    	   return new ResponseEntity<String>("note deleted sucessfully",HttpStatus.OK);
       return new ResponseEntity<String>("note not deleted",HttpStatus.CONFLICT);
       
   }
   
   @RequestMapping(value="/get",method=RequestMethod.GET)
   public ResponseEntity<List<Note>> getallnote(HttpServletRequest request)
   {
	   String token  = request.getHeader("key");
	   System.out.println(token);
	   List<Note> list  =noteservice.getUser(token);
	   
	   return new ResponseEntity<List<Note>>(list,HttpStatus.OK);

   }
   
   @RequestMapping(value="/update",method=RequestMethod.PUT)
   public ResponseEntity<String> updatenote(@RequestBody Note note,HttpServletRequest request)
   {
	   String token = request.getHeader("key");
	   System.out.println(token);
	   if(noteservice.update(token,note))
	   {
		  System.out.println(note.isTrash());
		   return new ResponseEntity<String>("note updated successfully ",HttpStatus.OK);
	   }
	   else
	   {
	   return new ResponseEntity<String>("note not updated sucessfully",HttpStatus.CONFLICT);
	   }
   }
   
}
