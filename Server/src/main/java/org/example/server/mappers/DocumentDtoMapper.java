package org.example.server.mappers;

import org.example.server.Documentation.Document;
import org.example.server.Documentation.DocumentDto;
import org.example.server.Dtos.ExamDto;
import org.example.server.models.Exam;

import java.util.function.Function;

public class DocumentDtoMapper implements Function<Document, DocumentDto> {

    @Override
    public DocumentDto apply(Document document) {
        return new DocumentDto(
                document.getId(),
                document.getName(),
                document.getDescription(),
                document.getCategory(),
                document.getFilePath());
    }
}
