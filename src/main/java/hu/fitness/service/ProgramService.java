package hu.fitness.service;

import hu.fitness.converter.ProgramConverter;
import hu.fitness.domain.Program;
import hu.fitness.domain.Trainer;
import hu.fitness.dto.ProgramRead;
import hu.fitness.dto.ProgramSave;
import hu.fitness.exception.ProgramNotFoundException;
import hu.fitness.exception.TrainerNotFoundException;
import hu.fitness.repository.ProgramRepository;
import hu.fitness.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {

    @Autowired
    ProgramRepository programRepository;
    @Autowired
    private TrainerRepository trainerRepository;

    public ProgramRead readProgram(int id) {
        if (!programRepository.existsById(id) ) {
            throw new ProgramNotFoundException();
        }
        Program program = programRepository.getReferenceById(id);
        return ProgramConverter.convertModelToRead(program);
    }

    public List<ProgramRead> listPrograms() {
        List<ProgramRead> programReads;
        List<Program> programs = programRepository.findAll();
        programReads = ProgramConverter.convertModelToReadList(programs);
        return programReads;
    }

    public ProgramRead createProgram(ProgramSave programSave) {
        if (!trainerRepository.existsById(programSave.getTrainerId())) {
            throw new TrainerNotFoundException();
        }
        Trainer trainer = trainerRepository.getReferenceById(programSave.getTrainerId());
        Program program = ProgramConverter.convertSaveToModel(programSave, trainer);
        Program savedProgram = programRepository.save(program);
        return ProgramConverter.convertModelToRead(savedProgram);
    }

    public ProgramRead deleteProgram(int id) {
        if (!programRepository.existsById(id)) {
            throw new ProgramNotFoundException();
        }
        ProgramRead programRead = ProgramConverter.convertModelToRead(programRepository.getReferenceById(id));
        programRepository.deleteById(id);
        return programRead;
    }
}
