package com.salesianos.dam.gestiondepeliculas.service;

import com.salesianos.dam.gestiondepeliculas.dto.director.EditDirectorDto;
import com.salesianos.dam.gestiondepeliculas.model.Director;
import com.salesianos.dam.gestiondepeliculas.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    public Director addDirector(EditDirectorDto editDirectorDto) {

        Director d = editDirectorDto.to();

        return directorRepository.save(d);
    }

    public List<Director> getAllDirectores() {

        List<Director> result = directorRepository.findAll();

        return result;
    }

    public Director getDirectorById(Long id) {

        return directorRepository.findById(id).orElse(null);

    }

}
