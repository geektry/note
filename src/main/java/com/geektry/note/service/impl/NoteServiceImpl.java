package com.geektry.note.service.impl;

import com.geektry.note.entity.Group;
import com.geektry.note.entity.Note;
import com.geektry.note.entity.AccessRecord;
import com.geektry.note.framework.RuntimeExceptionMessage;
import com.geektry.note.framework.ServiceRuntimeException;
import com.geektry.note.repository.NoteRepository;
import com.geektry.note.service.NoteService;
import com.geektry.note.util.TimeUtil;
import com.geektry.note.vo.GroupVO;
import com.geektry.note.vo.PvOfIdVO;
import com.geektry.note.vo.PvOfTimeVO;
import com.geektry.note.vo.NoteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chaohang Fu
 */
@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public NoteVO getNoteByPath(String path) {

        return this.toNoteVO(noteRepository.getNoteByPath(path));
    }

    @Override
    public void insertAccessRecord(AccessRecord accessRecord) {
        noteRepository.insertAccessRecord(accessRecord);
        noteRepository.increasePv(accessRecord.getNoteId());
    }

    @Override
    public List<NoteVO> listNotes(Long groupId) {

        List<NoteVO> noteVOs = new ArrayList<>();
        List<Note> notes = noteRepository.listNotes(groupId);
        for (Note note : notes) {
            noteVOs.add(this.toNoteVO(note));
        }
        return noteVOs;
    }

    @Override
    public NoteVO getNote(Long noteId) {
        return this.toNoteVO(noteRepository.getNote(noteId));
    }

    @Override
    public Long insertNote(NoteVO noteVO) {

        String path = noteVO.getPath();
        String title = noteVO.getTitle();
        String mdContent = noteVO.getMdContent();
        String htmlContent = noteVO.getHtmlContent();
        Long groupId = noteVO.getGroupId();

        if (StringUtils.isEmpty(path) ||
                StringUtils.isEmpty(title) ||
                StringUtils.isEmpty(mdContent) ||
                StringUtils.isEmpty(htmlContent) ||
                StringUtils.isEmpty(groupId)) {
            throw new ServiceRuntimeException(RuntimeExceptionMessage.PARAM_NULL);
        }

        Note note = new Note() {{
            setPath(path);
            setTitle(title);
            setMdContent(mdContent);
            setHtmlContent(htmlContent);
            setGroupId(groupId);
        }};

        noteRepository.insertNote(note);

        return note.getId();
    }

    @Override
    public void updateNote(NoteVO noteVO, Long noteId) {

        String path = noteVO.getPath();
        String title = noteVO.getTitle();
        String mdContent = noteVO.getMdContent();
        String htmlContent = noteVO.getHtmlContent();
        Long groupId = noteVO.getGroupId();

        if (StringUtils.isEmpty(path) ||
                StringUtils.isEmpty(title) ||
                StringUtils.isEmpty(mdContent) ||
                StringUtils.isEmpty(htmlContent) ||
                StringUtils.isEmpty(noteId) ||
                StringUtils.isEmpty(groupId)) {
            throw new ServiceRuntimeException(RuntimeExceptionMessage.PARAM_NULL);
        }

        noteRepository.updateNote(new Note() {{
            setPath(path);
            setTitle(title);
            setMdContent(mdContent);
            setHtmlContent(htmlContent);
            setGroupId(groupId);
        }}, noteId);
    }

    @Override
    public void deleteNote(Long noteId) {
        noteRepository.deleteNote(noteId);
    }

    @Override
    public List<PvOfTimeVO> countPvOfTime(Long noteId, LocalDateTime startTime, LocalDateTime endTime) {

        List<PvOfTimeVO> pvOfTimeVOs = noteRepository.countPvOfTime(noteId, startTime, endTime);
        return this.fillWithZero(pvOfTimeVOs, startTime, endTime);
    }

    @Override
    public List<PvOfIdVO> countPvOfId(LocalDateTime samplingTime) {
        return noteRepository.countPvOfId(samplingTime);
    }

    @Override
    public List<GroupVO> listGroups() {
        List<GroupVO> groupVOs = new ArrayList<>();
        List<Group> groups = noteRepository.listGroups();

        for (Group group : groups) {
            groupVOs.add(new GroupVO() {{
                setId(group.getId());
                setName(group.getName());
            }});
        }
        return groupVOs;
    }

    @Override
    public GroupVO getGroup(Long groupId) {
        Group group = noteRepository.getGroup(groupId);
        return new GroupVO() {{
            setId(group.getId());
            setName(group.getName());
        }};
    }

    @Override
    public Long insertGroup(GroupVO groupVO) {

        String groupName = groupVO.getName();

        if (StringUtils.isEmpty(groupName)) {
            throw new ServiceRuntimeException(RuntimeExceptionMessage.PARAM_NULL);
        }

        Group group = new Group() {{
            setName(groupName);
        }};
        noteRepository.insertGroup(group);
        return group.getId();
    }

    @Override
    public void updateGroup(GroupVO groupVO, Long groupId) {

        String groupName = groupVO.getName();

        if (StringUtils.isEmpty(groupName)) {
            throw new ServiceRuntimeException(RuntimeExceptionMessage.PARAM_NULL);
        }

        noteRepository.updateGroup(new Group() {{
            setName(groupName);
        }}, groupId);
    }

    @Override
    public void deleteGroup(Long groupId) {
        noteRepository.setNoteGroupNull(groupId);
        noteRepository.deleteGroup(groupId);
    }

    private NoteVO toNoteVO(Note note) {

        if (note == null) {
            return null;
        }

        return new NoteVO() {{
            setId(note.getId());
            setPath(note.getPath());
            setTitle(note.getTitle());
            setMdContent(note.getMdContent());
            setHtmlContent(note.getHtmlContent());
            setPv(note.getPv());
            setCreatedTs(TimeUtil.beautify(note.getCreatedTs()));
            setModifiedTs(TimeUtil.beautify(note.getModifiedTs()));
            setGroupId(note.getGroupId());
        }};
    }

    private List<PvOfTimeVO> fillWithZero(List<PvOfTimeVO> pvOfTimeVOs, LocalDateTime startTime, LocalDateTime endTime) {

        Map<LocalDateTime, PvOfTimeVO> notePvOfTimeVOMap = new HashMap<>(32);
        for (PvOfTimeVO pvOfTimeVO : pvOfTimeVOs) {
            notePvOfTimeVOMap.put(pvOfTimeVO.getSamplingTime(), pvOfTimeVO);
        }

        List<PvOfTimeVO> resultVOs = new ArrayList<>();
        LocalDateTime cursorTime = startTime;
        while (!cursorTime.isAfter(endTime)) {
            PvOfTimeVO pvOfTimeVO = notePvOfTimeVOMap.get(cursorTime);
            if (pvOfTimeVO == null) {
                PvOfTimeVO vo = new PvOfTimeVO();
                vo.setSamplingTime(cursorTime);
                vo.setPv(0L);
                resultVOs.add(vo);
            } else {
                resultVOs.add(pvOfTimeVO);
            }
            cursorTime = cursorTime.plusDays(1L);
        }
        return resultVOs;
    }
}
