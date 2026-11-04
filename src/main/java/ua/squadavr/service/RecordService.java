package ua.squadavr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.squadavr.dao.RecordDao;
import ua.squadavr.entity.Record;
import ua.squadavr.entity.RecordStatus;
import ua.squadavr.entity.dto.RecordsContainerDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private final RecordDao recordDao;
    private int id = 3;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public RecordsContainerDto findAllRecords(String filterMode) {
        List<Record> records = recordDao.findAllRecords();
        int numberOfDoneRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();

        if (filterMode == null || filterMode.isBlank()){
            return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);
        }

        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(Enum::name)
                .toList();
        String filterModeInUpperCase = filterMode.toUpperCase();
        if (allowedFilterModes.contains(filterModeInUpperCase)){
            List<Record> filteredRecords = records.stream().filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeInUpperCase)).toList();
            return new RecordsContainerDto(filteredRecords, numberOfDoneRecords, numberOfActiveRecords);
        } else {
            return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);
        }
    }


    public void saveRecord(String title) {
        if (title != null && !title.isBlank()) {
            recordDao.saveRecord(new Record(title, id));
            id++;
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        recordDao.updateRecordStatus(id, newStatus);
    }

    public void deleteRecord(int id) {
        recordDao.deleteRecord(id);
    }
}
