package ru.intabia.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.intabia.dao.RentDAO;
import ru.intabia.dto.RentDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentDAO rentDAO;

    public List<RentDTO> showRentTable(long libraryId) {
        return rentDAO.showAllRents(libraryId)
                .stream()
                .map(rent -> new RentDTO(
                        rent.getId(),
                        rent.getReaderId(),
                        rent.getBookId(),
                        rent.getLibraryId(),
                        rent.isActive()
                )).toList();
    }
}
