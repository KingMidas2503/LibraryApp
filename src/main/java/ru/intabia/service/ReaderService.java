package ru.intabia.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.intabia.dao.ReaderDAO;
import ru.intabia.dto.ReaderDTO;
import ru.intabia.models.Reader;


@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderDAO readerDAO;

    public void saveNewReader(ReaderDTO readerDTO) {
        Reader reader = new Reader(readerDTO.getName());
        readerDAO.saveNewReader(reader);
    }

    public ReaderDTO getReaderById(long readerId) {
        Reader reader = readerDAO.getReaderById(readerId);
        if (reader != null) {
            return new ReaderDTO(readerId, reader.getName());
        }
        return null;
    }
}
