package com.study.springboot.dao;

import java.util.List;

import com.study.springboot.dto.SimpleBbsDto;

public interface ISimpleBbsDao {

    public List<SimpleBbsDto> listDao();
    public SimpleBbsDto viewDao(String id);
    public int writeDao(String writer, String title, String content);
    public int updateDao(String writer, String title, String content,String id);
    public int deleteDao(String id);
}
