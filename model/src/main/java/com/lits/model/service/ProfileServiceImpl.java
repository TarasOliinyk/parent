package com.lits.model.service;

import com.lits.api.dto.ProfileDTO;
import com.lits.api.service.ProfileService;
import com.lits.model.dao.ProfileDao;
import com.lits.model.entity.Profile;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileServiceImpl implements ProfileService {
    private ProfileDao repo;

    public ProfileServiceImpl(ProfileDao repo) {
        this.repo = repo;
    }

    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        Profile profile = dtoToProfileWithoutId(profileDTO);
        return profileToDTO(repo.save(profile));
    }

    @Override
    public List<ProfileDTO> findAll() {
        return repo.findAll().stream().map(this::profileToDTO).collect(Collectors.toList());
    }

    @Override
    public ProfileDTO findById(int id) {
        return profileToDTO(repo.findById(id));
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) {
        Profile profile = dtoToProfileWithoutId(profileDTO);
        profile.setId(profileDTO.getId());
        return profileToDTO(repo.update(profile));
    }

    @Override
    public boolean deleteProfile(int id) {
        return repo.deleteProfile(id);
    }

    private ProfileDTO profileToDTO(Profile profile) {
        ProfileDTO p = new ProfileDTO();
        p.setAge(profile.getAge());
        p.setId(profile.getId());
        p.setName(profile.getName());
        p.setLastName(profile.getLastName());
        return p;
    }

    private Profile dtoToProfileWithoutId(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setName(profileDTO.getName());
        profile.setLastName(profileDTO.getLastName());
        profile.setAge(profileDTO.getAge());
        return profile;
    }
}
