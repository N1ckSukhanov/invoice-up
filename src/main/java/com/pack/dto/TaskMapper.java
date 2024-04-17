package com.pack.dto;

import com.pack.entity.Task;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TaskMapper {
    public static Task toModel(TaskDto dto) throws IOException {
            return new Task(dto.getPlanPayDate(), dto.getSumCur(), dto.getTaxCur(),
                    dto.getCreditNoteSum(), dto.getCreditNoteInvoice(),
                    dto.getCreditNoteInvoiceForPay());

    }

}
