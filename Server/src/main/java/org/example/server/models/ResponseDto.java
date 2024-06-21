package org.example.server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto implements Serializable {
    private boolean worked =false ;
    private Object result = null;
    private String message;
}
