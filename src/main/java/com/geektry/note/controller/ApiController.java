package com.geektry.note.controller;

import com.geektry.note.framework.AuthRequired;
import com.geektry.note.service.NoteService;
import com.geektry.note.util.DateTimeConverter;
import com.geektry.note.vo.GroupVO;
import com.geektry.note.vo.PvOfIdVO;
import com.geektry.note.vo.PvOfTimeVO;
import com.geektry.note.vo.NoteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Chaohang Fu
 */
@AuthRequired
@RestController
public class ApiController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/api/notes")
    public List<NoteVO> listNotes(@RequestParam(name = "groupId", required = false) Long groupId) {
        return noteService.listNotes(groupId);
    }

    @GetMapping("/api/notes/{noteId}")
    public NoteVO getNote(@PathVariable("noteId") Long noteId) {
        return noteService.getNote(noteId);
    }

    @PostMapping("/api/notes/")
    public Long postNote(@RequestBody NoteVO noteVO) {
        return noteService.insertNote(noteVO);
    }

    @PutMapping("/api/notes/{noteId}")
    public void putNote(@RequestBody NoteVO noteVO,
                        @PathVariable("noteId") Long noteId) {
        noteService.updateNote(noteVO, noteId);
    }

    @DeleteMapping("/api/notes/{noteId}")
    public void deleteNote(@PathVariable("noteId") Long noteId) {
        noteService.deleteNote(noteId);
    }

    @GetMapping("/api/statistics/pv_of_time")
    public List<PvOfTimeVO> countPvOfTime(@RequestParam(name = "noteId", required = false) Long noteId,
                                          @RequestParam("startTime") String startTimeStr,
                                          @RequestParam("endTime") String endTimeStr) {
        LocalDateTime startTime = DateTimeConverter.toDateTime(startTimeStr);
        LocalDateTime endTime = DateTimeConverter.toDateTime(endTimeStr);
        return noteService.countPvOfTime(noteId, startTime, endTime);
    }

    @GetMapping("/api/statistics/pv_of_id")
    public List<PvOfIdVO> countPvOfId(@RequestParam(name = "samplingTime", required = false) String samplingTimeStr) {
        LocalDateTime samplingTime = DateTimeConverter.toDateTime(samplingTimeStr);
        return noteService.countPvOfId(samplingTime);
    }

    @GetMapping("/api/groups")
    public List<GroupVO> listGroups() {
        return noteService.listGroups();
    }

    @GetMapping("/api/groups/{groupId}")
    public GroupVO getGroup(@PathVariable("groupId") Long groupId) {
        return noteService.getGroup(groupId);
    }

    @PostMapping("/api/groups/")
    public Long postGroup(@RequestBody GroupVO groupVO) {
        return noteService.insertGroup(groupVO);
    }

    @PutMapping("/api/groups/{groupId}")
    public void putGroup(@RequestBody GroupVO groupVO,
                         @PathVariable("groupId") Long groupId) {
        noteService.updateGroup(groupVO, groupId);
    }

    @DeleteMapping("/api/groups/{groupId}")
    public void deleteGroup(@PathVariable("groupId") Long groupId) {
        noteService.deleteGroup(groupId);
    }
}
