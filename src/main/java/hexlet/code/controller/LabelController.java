package hexlet.code.controller;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
@RequiredArgsConstructor
public class LabelController {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @GetMapping
    public ResponseEntity<List<LabelDTO>> index(@RequestParam(defaultValue = "0") int start,
                                                @RequestParam(defaultValue = "2147483647") int end,
                                                @RequestParam(defaultValue = "id") String sort,
                                                @RequestParam(defaultValue = "ASC") String order) {
        int page = start / (end - start);
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable pageable = PageRequest.of(page, end - start, Sort.by(direction, sort));

        List<LabelDTO> result =  labelRepository.findAll(pageable).stream()
                .map(labelMapper::map)
                .toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);

    }

    @GetMapping(path = "/{id}")
    public LabelDTO show(@PathVariable long id) {
        return labelMapper.map(labelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Label with id %d not found", id))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO create(@RequestBody @Valid LabelCreateDTO labelCreateDTO)
            throws DataIntegrityViolationException {
        Label label = labelMapper.map(labelCreateDTO);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    @PutMapping(path = "/{id}")
    public LabelDTO update(@PathVariable long id, @RequestBody @Valid LabelUpdateDTO labelUpdateDTO)
            throws DataIntegrityViolationException {
        Label label = labelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Label with id %d not found", id)));
        labelMapper.update(labelUpdateDTO, label);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        labelRepository.deleteById(id);
    }
}
