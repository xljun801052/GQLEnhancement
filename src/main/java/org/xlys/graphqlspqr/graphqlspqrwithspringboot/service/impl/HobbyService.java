package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Hobby;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.repository.HobbyRepository;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.IHobbyService;

import java.util.List;

@Service
public class HobbyService implements IHobbyService {

    @Autowired
    private HobbyRepository hobbyRepository;

    @Override
    public Hobby getHobbyById(Integer id) {
        return hobbyRepository.findById(id).orElse(new Hobby());
    }

    @Override
    public List<Hobby> getAllHobbys() {
        return hobbyRepository.findAll();
    }

    @Override
    public Hobby addHobby(Hobby Hobby) {
        return hobbyRepository.saveAndFlush(Hobby);
    }

    @Override
    public Hobby updateHobby(Hobby Hobby) {
        return hobbyRepository.saveAndFlush(Hobby);
    }

    @Override
    public boolean deleteHobby(Hobby Hobby) {
        try {
            hobbyRepository.delete(Hobby);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
