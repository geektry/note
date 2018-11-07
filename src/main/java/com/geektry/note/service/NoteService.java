package com.geektry.note.service;

import com.geektry.note.entity.AccessRecord;
import com.geektry.note.vo.GroupVO;
import com.geektry.note.vo.PvOfIdVO;
import com.geektry.note.vo.PvOfTimeVO;
import com.geektry.note.vo.NoteVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Chaohang Fu
 */
public interface NoteService {

    /**
     * 根据path获取note
     * @param path 路径
     * @return 笔记
     */
    NoteVO getNoteByPath(String path);

    /**
     * 插入accessRecord
     * @param accessRecord 笔记访问日志
     */
    void insertAccessRecord(AccessRecord accessRecord);

    /**
     * 获取notes
     * @param groupId 分组id
     * @return 笔记
     */
    List<NoteVO> listNotes(Long groupId);

    /**
     * 获取note
     * @param noteId 笔记id
     * @return 笔记
     */
    NoteVO getNote(Long noteId);

    /**
     * 插入note
     * @param noteVO 笔记
     * @return 笔记id
     */
    Long insertNote(NoteVO noteVO);

    /**
     * 更新note
     * @param noteVO 笔记
     * @param noteId 笔记id
     */
    void updateNote(NoteVO noteVO, Long noteId);

    /**
     * 删除note
     * @param noteId 笔记id
     */
    void deleteNote(Long noteId);

    /**
     * 统计pv of time
     * @param noteId 笔记id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据
     */
    List<PvOfTimeVO> countPvOfTime(Long noteId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计pv of id
     * @param samplingTime 采样时间
     * @return 统计数据
     */
    List<PvOfIdVO> countPvOfId(LocalDateTime samplingTime);

    /**
     * 获取groups
     * @return 分组
     */
    List<GroupVO> listGroups();

    /**
     * 获取group
     * @param groupId 分组id
     * @return 分组
     */
    GroupVO getGroup(Long groupId);

    /**
     * 插入group
     * @param groupVO 分组
     * @return 分组id
     */
    Long insertGroup(GroupVO groupVO);

    /**
     * 更新group
     * @param groupVO 分组
     * @param groupId 分组id
     */
    void updateGroup(GroupVO groupVO, Long groupId);

    /**
     * 删除group
     * @param groupId 分组id
     */
    void deleteGroup(Long groupId);
}
