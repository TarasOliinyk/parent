package com.lits.api.service;

import com.lits.api.dto.ProfileDTO;

import java.util.List;

public interface ProfileService {

    ProfileDTO createProfile(ProfileDTO profile);

    List<ProfileDTO> findAll();

    ProfileDTO findById(int id);

    ProfileDTO updateProfile(ProfileDTO profile);

    boolean deleteProfile(int id);
}
