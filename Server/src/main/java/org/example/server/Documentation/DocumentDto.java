package org.example.server.Documentation;

public record DocumentDto(Long id,
                          String name,
                          String description,
                          Category category,
                          String filePath) {


}
