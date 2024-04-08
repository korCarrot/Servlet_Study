package com.green.jdbcex.service;

import com.green.jdbcex.dao.MemberDAO;
import com.green.jdbcex.domain.MemberVO;
import com.green.jdbcex.dto.MemberDTO;
import com.green.jdbcex.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {

    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService() {

        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    //ID,PW로 로그인
   public MemberDTO login(String mid, String mpw) throws Exception {
       MemberVO vo = null;

           vo =dao.getWithPassword(mid, mpw);

       MemberDTO memberDTO=modelMapper.map(vo, MemberDTO.class);
       return memberDTO;
    }


    //UUID 데이터 추가
    public void updateUuid(String id, String uuid) throws Exception{
        dao.updateUuid(id, uuid);
    }


    //UUID로 memberVO조회
    public MemberDTO getByUUID(String uuid) throws Exception{
        MemberVO vo = dao.selectUuid(uuid);
        MemberDTO memberDTO=modelMapper.map(vo, MemberDTO.class);
        return memberDTO;
    }

}
