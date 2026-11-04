package ua.squadavr.dao;

import org.springframework.stereotype.Repository;
import ua.squadavr.entity.Record;
import ua.squadavr.entity.RecordStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RecordDao {
    private final List<Record> records = new ArrayList<>(
            Arrays.asList(
                    new Record(0,"Learn to code", RecordStatus.ACTIVE),
                    new Record(1,"Go to the gym", RecordStatus.DONE),
                    new Record(2,"Buy a flowers", RecordStatus.DONE)
            )
    );

    public List<Record> findAllRecords(){
        return new ArrayList<>(records);
    }

//    public List<Record> findActiveRecords(){
//        List<Record> result = records.stream()
//                .filter(record -> record.getStatus() == RecordStatus.ACTIVE).toList();
//        return new ArrayList<>(result);
//    }
//    public List<Record> findDoneRecords(){
//        List<Record> result = records.stream()
//                .filter(record -> record.getStatus() == RecordStatus.DONE).toList();
//        return new ArrayList<>(result);
//    }


    public void saveRecord(Record record){
        records.add(record);
    }

    public void updateRecordStatus(int id, RecordStatus newStatus){
        for (Record item : records){
            if (item.getId() == id){
                item.setStatus(newStatus);
                break;
            }
        }
    }

    public void deleteRecord(int id){
        records.removeIf(item -> item.getId() == id);
    }
}
