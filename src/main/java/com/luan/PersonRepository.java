package com.luan;

import com.luan.model.Person;
import com.luan.repository.BaseRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository extends BaseRepository<Person> {
}
