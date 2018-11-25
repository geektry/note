package com.geektry.note.repository;

import com.geektry.note.entity.Group;
import com.geektry.note.entity.Note;
import com.geektry.note.entity.AccessRecord;
import com.geektry.note.vo.PvOfIdVO;
import com.geektry.note.vo.PvOfTimeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Chaohang Fu
 */
@Mapper
@Repository
public interface NoteRepository {

    /**
     * 根据path获取note
     * @param path 路径
     * @return 笔记
     */
    Note getNoteByPath(@Param("path") String path);

    /**
     * 插入accessRecord
     * @param accessRecord 笔记访问日志
     */
    void insertAccessRecord(AccessRecord accessRecord);

    /**
     * 增加pv
     * @param noteId 笔记id
     */
    void increasePv(@Param("noteId") Long noteId);

    /**
     * 获取notes
     * @param groupId 分组id
     * @return 笔记
     */
    List<Note> listNotes(@Param("groupId") Long groupId);

    /**
     * 获取note
     * @param noteId 笔记id
     * @return 笔记
     */
    Note getNote(@Param("noteId") Long noteId);

    /**
     * 插入note
     * @param note 笔记
     */
    void insertNote(Note note);

    /**
     * 更新note
     * @param note 笔记
     * @param noteId 笔记id
     */
    void updateNote(@Param("note") Note note,
                    @Param("noteId") Long noteId);

    /**
     * 删除note
     * @param noteId 笔记id
     */
    void deleteNote(@Param("noteId") Long noteId);

    /**
     * 统计pv of time
     * @param noteId 笔记id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据
     */
    List<PvOfTimeVO> countPvOfTime(@Param("noteId") Long noteId,
                                   @Param("startTime") LocalDateTime startTime,
                                   @Param("endTime") LocalDateTime endTime);

    /**
     * 统计pv of id
     * @param samplingTime 采样时间
     * @return 统计数据
     */
    List<PvOfIdVO> countPvOfId(@Param("samplingTime") LocalDateTime samplingTime);

    /**
     * 获取groups
     * @return 分组
     */
    List<Group> listGroups();

    /**
     * 获取group
     * @param groupId 分组id
     * @return 分组
     */
    Group getGroup(@Param("groupId") Long groupId);

    /**
     * 插入group
     * @param group 分组
     */
    void insertGroup(Group group);

    /**
     * 更新group
     * @param group 分组
     * @param groupId 分组id
     */
    void updateGroup(@Param("group") Group group,
                     @Param("groupId") Long groupId);

    /**
     * 清除本分组下的note分组信息
     * @param groupId 分组id
     */
    void setNoteGroupNull(@Param("groupId") Long groupId);

    /**
     * 删除group
     * @param groupId 分组id
     */
    void deleteGroup(@Param("groupId") Long groupId);
}
