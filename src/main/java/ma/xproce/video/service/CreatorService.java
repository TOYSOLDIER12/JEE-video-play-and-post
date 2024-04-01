package ma.xproce.video.service;

import ma.xproce.video.dao.entity.Creator;
import ma.xproce.video.dao.repository.CreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreatorService implements CreatorManager {
    @Autowired
    CreatorRepository creatorRepository;
    @Override
    public Creator addCreator(Creator creator) {
        if(creator.getUsername().isEmpty() || creator.getMail().isEmpty()) {
            System.out.println("no name noor mail wtf");
            return null;
        }
        return creatorRepository.save(creator);
    }

    @Override
    public Creator updateCreator(Creator creator) {
        Optional<Creator> existingCreator = creatorRepository.findById(creator.getId());

        if(existingCreator.isEmpty()) {
            System.out.println("There ain't no bloody Creator with this bleedin' name: " + creator.getUsername());
            return null;
        }

        Creator oldCreator = existingCreator.get();
        oldCreator.setMail(creator.getMail());
        oldCreator.setUsername(creator.getUsername());
        oldCreator.setVideos(creator.getVideos());

        return creatorRepository.save(oldCreator);
    }

    @Override
    public boolean deleteCeator(Creator creator) {
        Optional<Creator> existingCreator = creatorRepository.findById(creator.getId());
        if (existingCreator.isEmpty()) {
            System.out.println("No fookin' video found with that pissin' ID: " + creator.getId());
            return false;
        }

        creatorRepository.delete(existingCreator.get());

        return !creatorRepository.existsById(creator.getId());
    }

    @Override
    public List<Creator> getAllCreators() {

        return creatorRepository.findAll();
    }

    @Override
    public Creator getById(long id) {
        if(creatorRepository.getById(id) == null) {
            System.out.println("no creator was found matching that id" + id);
            return null;
        }
        return creatorRepository.getById(id);
    }

    @Override
    public boolean checkLogin(String username, String password) {
        Optional<Creator> existingCreator = creatorRepository.findByUsername(username);
        if (existingCreator.isPresent()) {
            Creator creator = existingCreator.get();
            if (creator.getPassword().equals(password)) {
                // Password matches, login successful
                return true;
            } else {
                System.out.println("Password not matchy matchy");
                return false;
            }
        } else {
            System.out.println("who are you mr " +username+" ?");
            return false;
        }
    }

    @Override
    public Optional<Creator> findByUsername(String username) {
        Optional<Creator> existingCreator = creatorRepository.findByUsername(username);
        if(existingCreator.isPresent()){
            return existingCreator;
        }
        return null;
    }
}
