package com.green.nowon.service;

import javax.validation.Valid;

import com.green.nowon.domain.dto.UserSaveDTO;

public interface SignService {

	void userSave(@Valid UserSaveDTO dto);

}
