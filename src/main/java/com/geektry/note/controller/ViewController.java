package com.geektry.note.controller;

import com.geektry.note.entity.AccessRecord;
import com.geektry.note.service.NoteService;
import com.geektry.note.vo.NoteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Chaohang Fu
 */
@Controller
public class ViewController {

    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    private NoteService noteService;

    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("redirect:/p/index");
    }

    @GetMapping("/p/{path}")
    public ModelAndView viewNote(@PathVariable("path") String path,
                                 @RequestHeader(name = "X-Real-IP", defaultValue = "127.0.0.1") String clientIp,
                                 @RequestHeader(name = "X-User-Agent", defaultValue = "user-agent") String userAgent,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView();
        NoteVO noteVO = noteService.getNoteByPath(path);
        if (noteVO == null) {
            modelAndView.setViewName("error/404");
            return modelAndView;
        }
        modelAndView.setViewName("note");
        modelAndView.addObject("note", noteVO);

        Long noteId = noteVO.getId();
        boolean isNoteLatelyViewedByUser = this.isNoteLatelyViewedByUser(request.getCookies(), noteId);
        if (isNoteLatelyViewedByUser) {
            return modelAndView;
        }

        noteService.insertAccessRecord(new AccessRecord() {{
            setNoteId(noteId);
            setClientIp(clientIp);
            setUserAgent(userAgent);
        }});
        response.addCookie(new Cookie(noteId.toString(), "expired-in-2-hours") {{
            setMaxAge(2 * 60 * 60);
        }});
        return modelAndView;
    }

    private boolean isNoteLatelyViewedByUser(Cookie[] cookies, Long noteId) {

        if (cookies == null) {
            return false;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(noteId.toString())) {
                return true;
            }
        }
        return false;
    }
}
