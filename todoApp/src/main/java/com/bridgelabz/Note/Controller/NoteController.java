package com.bridgelabz.Note.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.Note.Service.NoteService;
import com.bridgelabz.Note.model.NoteCollaborate;
import com.bridgelabz.Note.model.NoteDetails;
import com.bridgelabz.Note.model.NoteLabel;
import com.bridgelabz.User.Service.UserService;
import com.bridgelabz.User.model.UserDetails;
import com.bridgelabz.Util.response.CustomResponse;
import com.bridgelabz.Util.response.Response;
import com.bridgelabz.Util.token.VerifyToken;

/**
 * @author Pooja todoApp
 *
 */
@RestController
@RequestMapping(value = "/user")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	@Autowired
	NoteUrlService nUrl;

	/**
	 * @param noteDetails
	 * @param session
	 * @param request
	 * @return customResponse
	 * @throws Exception
	 *             creating notes
	 */
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ResponseEntity<Response> createNote(@RequestBody NoteDetails noteDetails, HttpSession session,
			HttpServletRequest request) throws Exception {
		// UserDetails user = (UserDetails) session.getAttribute("user");
		// NoteUrl noteUrl=(NoteUrl)nUrl.checkUrl(noteDetails);
		int userId = (int) request.getAttribute("userId");

		UserDetails user = userService.getUserById(userId);

		noteDetails.setUser(user);
		if (user != null) {
			Date date = new Date();
			noteDetails.setCreateddate(date);
			noteDetails.setModifiedDate(date);
			// noteDetails.setNoteUrls(nUrl);
			NoteDetails note = noteService.createNote(noteDetails);

			nUrl.createNoteUrls(note);
			System.out.println("Note created!!");
			CustomResponse customResponse = new CustomResponse();
			customResponse.setMessage("Note create successfully");
			return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
		}
		Response response = new Response();
		response.setMessage("Please login first");
		return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
	}

	/**
	 * @param noteDetails
	 * @return update the notes
	 */
	@RequestMapping(value = "/updateNote", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateNote(@RequestBody NoteDetails noteDetails) {

		System.out.println("Note status: " + noteDetails.isPin());
		int id = noteDetails.getId();

		NoteDetails note = noteService.getNoteById(id);

		Date current = note.getCreateddate();
		noteDetails.setCreateddate(current);

		UserDetails user = note.getUser();
		noteDetails.setUser(user);

		System.out.println("note sid " + id + " current :" + noteDetails.getCreateddate() + " user : "
				+ noteDetails.getUser() + "note pin " + noteDetails.isPin());
		noteService.updateNote(noteDetails);
		System.out.println("note is updated");

		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage("Note updated successfully...");
		return ResponseEntity.ok(customResponse);

	}

	/**
	 * @param noteId
	 * @return delete the notes with id
	 */
	@RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable("id") int noteId) {
		NoteDetails note = new NoteDetails();
		note.setId(noteId);
		System.out.println("id : " + noteId);
		noteService.deleteNote(noteId);
		System.out.println("note is deleted");
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage("Note deleted successfully!!!");
		return ResponseEntity.ok(customResponse);
	}

	/**
	 * @return delete all notes
	 */
	@RequestMapping(value = "/delAllNotes", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteAllNote() {
		NoteDetails note = new NoteDetails();
		noteService.deleteAllNote();
		System.out.println("All notes are deleted");
		CustomResponse customResponse = new CustomResponse();
		customResponse.setMessage("Notes deleted successfully!!!");
		return ResponseEntity.ok(customResponse);
	}

	/**
	 * @param request
	 * @return get all notes
	 */
	@RequestMapping(value = "/getAllNotes", method = RequestMethod.GET)
	public List<NoteDetails> getAllNotes(HttpServletRequest request) {
		System.out.println("Getting alll notes");
		int id = (int) request.getAttribute("userId");

		UserDetails user = userService.getUserById(id);
		System.out.println("id---------------------------->" + id);

		List<NoteDetails> notes = noteService.getAllNotes(user);

		List<NoteDetails> noteCollabortor = noteService.getCollboratedNotes(user.getId());

		List<NoteDetails> noteList = new ArrayList<>();
		for (int i = 0; i < notes.size(); i++) {
			noteList.add(notes.get(i));
		}

		for (int i = 0; i < noteCollabortor.size(); i++) {
			noteList.add(noteCollabortor.get(i));
		}

		// System.out.println("all notes : " + notes);
		return noteList;
	}

	/**
	 * @param collborator
	 * @param request
	 * @return adding the owner as well as sharer of the notes
	 */
	@RequestMapping(value = "/collaborate", method = RequestMethod.POST)
	public ResponseEntity<List<UserDetails>> getNotes(@RequestBody NoteCollaborate collborator,
			HttpServletRequest request) {
		List<UserDetails> userList = new ArrayList<UserDetails>();

		NoteCollaborate collaborate = new NoteCollaborate();

		NoteDetails note = (NoteDetails) collborator.getNoteId();
		UserDetails shareUser = (UserDetails) collborator.getShareId();
		UserDetails owner = (UserDetails) collborator.getOwnerId();

		shareUser = userService.emailValidation(shareUser.getEmail());

		String accessToken = request.getHeader("token");
		UserDetails user = userService.getUserById(VerifyToken.verifyAccessToken(accessToken));

		userList = noteService.getListOfUser(note.getId());

		if (user != null) {
			if (shareUser != null && shareUser.getId() != owner.getId()) {
				int i = 0;
				int variable = 0;
				while (userList.size() > i) {
					if (userList.get(i).getId() == shareUser.getId()) {
						variable = 1;
					}
					i++;
				}
				if (variable == 0) {
					collaborate.setNoteId(note);
					collaborate.setOwnerId(owner);
					collaborate.setShareId(shareUser);
					if (noteService.saveCollborator(collaborate) > 0) {
						userList.add(shareUser);
					} else {
						ResponseEntity.ok(userList);
					}
				}
			}
		}
		return ResponseEntity.ok(userList);
	}

	/**
	 * @param note
	 * @param request
	 * @return get owner of the note in collaborator
	 */
	@RequestMapping(value = "/getOwner", method = RequestMethod.POST)
	public ResponseEntity<UserDetails> getOwner(@RequestBody NoteDetails note, HttpServletRequest request) {

		String accessToken = request.getHeader("token");
		UserDetails user = userService.getUserById(VerifyToken.verifyAccessToken(accessToken));

		if (user != null) {
			NoteDetails noteComplete = noteService.getNoteById(note.getId());
			UserDetails owner = noteComplete.getUser();
			return ResponseEntity.ok(owner);
		} else {
			return ResponseEntity.ok(null);
		}
	}

	/**
	 * @param collborator
	 * @param request
	 * @return remove sharer of the note in collaborator
	 */
	@RequestMapping(value = "/removeCollborator", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> removeCollborator(@RequestBody NoteCollaborate collborator,
			HttpServletRequest request) {

		CustomResponse response = new CustomResponse();
		int shareWith = collborator.getShareId().getId();
		int noteId = collborator.getNoteId().getId();
		NoteDetails note = noteService.getNoteById(noteId);

		UserDetails owner = note.getUser();
		String token = request.getHeader("token");

		UserDetails user = userService.getUserById(VerifyToken.verifyAccessToken(token));
		if (user != null) {
			if (owner.getId() != shareWith) {
				if (noteService.removeCollborator(shareWith, noteId) > 0) {
					response.setMessage("Collborator removed");
					return ResponseEntity.ok(response);

				} else {
					response.setMessage("database problem");
					return ResponseEntity.ok(response);
				}
			}

			else {
				response.setMessage("Can't remove owner");
				return ResponseEntity.ok(response);
			}
		}

		else {
			response.setMessage("Token expired");
			return ResponseEntity.ok(response);
		}
	}

	/* LABEL NOTES */

	/**
	 * @param labels
	 * @param session
	 * @param request
	 * @return create and save the label
	 */
	@RequestMapping(value = "/saveLabel", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> saveLabel(@RequestBody NoteLabel labels, HttpSession session,
			HttpServletRequest request) {
		System.out.println("save label");
		CustomResponse response = new CustomResponse();
		try {
			if (!(labels.getLabelName() == "" || labels.getLabelName() == null)) {
				NoteLabel objLabel = noteService.getLabelByName(labels.getLabelName());
				if (objLabel == null) {
					int id = (int) request.getAttribute("userId");

					UserDetails user = userService.getUserById(id);
					labels.setUser(user);
					noteService.saveLabel(labels);
					response.setMessage("label save successfully:-");
					return ResponseEntity.ok(response);
				} else {
					response.setMessage("your label is already exist:-");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			}
			response.setMessage("label can't be empty");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
		}
	}

	/**
	 * @param label
	 * @param request
	 * @return get the label notes using label names
	 */
	@RequestMapping(value = "getLabelNotes/{label}", method = RequestMethod.GET)
	public List<NoteLabel> getLabels(@PathVariable String label, HttpServletRequest request) {
		int id = (int) request.getAttribute("userId");

		UserDetails user = userService.getUserById(id);
		List<NoteLabel> alNotes = noteService.getLabels(user);
		// System.out.println("list of note label "+alNotes);
		return alNotes;
	}

	/**
	 * @param id
	 * @return delete the label using id
	 */
	@RequestMapping(value = "/deleteLabels/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CustomResponse> deleteLabel(@PathVariable int id) {
		CustomResponse response = new CustomResponse();
		boolean isDeleted = noteService.deleteLabelById(id);
		if (isDeleted) {
			response.setMessage("deleted successfully");
			return ResponseEntity.ok(response);
		} else {
			response.setMessage("unable to delete");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}

	/**
	 * @param label
	 * @param request
	 * @return edit the label uding userId
	 */
	@RequestMapping(value = "/editLabel", method = RequestMethod.POST)
	public ResponseEntity<CustomResponse> editNotes(@RequestBody NoteLabel label, HttpServletRequest request) {

		CustomResponse response = new CustomResponse();
		int id = (int) request.getAttribute("userId");
		UserDetails user = userService.getUserById(id);
		label.setUser(user);
		System.out.println("edited label name " + label.getLabelName());
		boolean isEdited;
		Date resetDate = new Date();
		isEdited = noteService.editLabel(label);
		if (isEdited) {
			response.setMessage("editing notes are successfull");
			return ResponseEntity.ok(response);
		} else {
			response.setMessage("edition is not possible");
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
		}
	}

}
