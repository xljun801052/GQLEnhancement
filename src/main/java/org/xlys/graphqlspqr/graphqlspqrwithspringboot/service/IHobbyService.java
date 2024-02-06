package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service;

import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Hobby;

import java.util.List;

public interface IHobbyService {

    Hobby getHobbyById(Integer id);

    List<Hobby> getAllHobbys();

    Hobby addHobby(Hobby Hobby);

    Hobby updateHobby(Hobby Hobby);

    boolean deleteHobby(Hobby Hobby);
}
