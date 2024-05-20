package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;

import ma.xproce.video.dao.repository.CreatorRepository;
import ma.xproce.video.service.dtos.CreatorDTO;
import ma.xproce.video.service.dtos.CreatorDTOADD;
import ma.xproce.video.service.mapper.CreatorMapper;
import ma.xproce.video.service.mapper.HolyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CreatorService implements CreatorManager {
    @Autowired
    CreatorRepository creatorRepository;
    @Autowired
    CreatorMapper creatorMapper;
    @Autowired
    HolyMapper holyMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CreatorDTO addCreator(CreatorDTOADD creatorDTOADDD) {
        if(creatorDTOADDD.getUsername().isEmpty() || creatorDTOADDD.getMail().isEmpty()) {
            System.out.println("no name nor mail wtf");
            return null;
        }
        Creator creator = creatorMapper.CreatorDTOADDToCreator(creatorDTOADDD);
        creatorRepository.save(creator);
        CreatorDTO creatorDTO = creatorMapper.CreatorToCreatorDTO(creator);
        return creatorDTO;
    }

    @Override
    public CreatorDTO updateCreator(CreatorDTOADD creatorDTOADD) {

        Optional<Creator> existingCreator = creatorRepository.findById(creatorDTOADD.getId());
        if(existingCreator.isEmpty()) {
            System.out.println(creatorDTOADD.getId());
            System.out.println("There ain't no bloody Creator with this bleedin' name: " + creatorDTOADD.getUsername());
            return null;
        }
        Creator toUpdate = creatorMapper.CreatorDTOADDToCreator(creatorDTOADD);
        Creator creator = existingCreator.get();
        creator.setMail(toUpdate.getMail());
        creator.setUsername(toUpdate.getUsername());
        creator.setVideos(toUpdate.getVideos());

        creatorRepository.save(creator);
        CreatorDTO creatorDTO = creatorMapper.CreatorToCreatorDTO(creator);
        return creatorDTO;
    }

    @Override
    public boolean deleteCeator(long id) {

        Optional<Creator> existingCreator = creatorRepository.findById(id);
        if (existingCreator.isEmpty()) {
            System.out.println("No fookin' video found with that pissin' ID: " + id);
            return false;
        }

        creatorRepository.delete(existingCreator.get());

        return !creatorRepository.existsById(id);
    }

    @Override
    public List<CreatorDTO> getAllCreators() {
        List<Creator> creators = creatorRepository.findAll();
        List<CreatorDTO> creatorDTOS = new ArrayList<>();
        for (Creator creator : creators) {
            CreatorDTO creatorDTO = holyMapper.ToCreatorDTO(creator);
            creatorDTOS.add(creatorDTO);
        }
        return creatorDTOS;
    }

    @Override
    public CreatorDTO getById(long id) {
        if(!creatorRepository.existsById(id)) {
            System.out.println("no creator was found matching that id" + id);
            return null;
        }
        Creator creator = creatorRepository.findById(id).get();
        CreatorDTO creatorDTO = holyMapper.ToCreatorDTO(creator);
        return creatorDTO;
    }

    @Override
    public boolean checkLogin(String username, String password) {


        Optional<Creator> existingCreator = creatorRepository.findByUsername(username);
        if (existingCreator.isEmpty()) {
            System.out.println("who are you mr " +username+" ?");
            return false;}

        Creator creator = existingCreator.get();


        if (passwordEncoder.matches(password, creator.getPassword())) {
                // Password matches, login successful
            return true;
            } else {
                System.out.println("Password not matchy matchy");
                return false;
            }
        }



    @Override
    public CreatorDTO findByUsername(String username) {
        Optional<Creator> existingCreator = creatorRepository.findByUsername(username);
        if(existingCreator.isPresent()){
            Creator creator = existingCreator.get();
            CreatorDTO creatorDTO = holyMapper.ToCreatorDTO(creator);
            return creatorDTO;
        }
        return null;
    }
    @Override
    public boolean checkPrivilege(Authentication authentication , String username){



        return true;
    }

}
